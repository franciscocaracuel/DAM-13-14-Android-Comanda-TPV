package com.izv.comandav09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.izv.comandav08.R;
import com.izv.comandav09.adaptadores.AdaptadorMesa;
import com.izv.comandav09.db4o.ManagerCarta;
import com.izv.comandav09.db4o.ManagerFecha;
import com.izv.comandav09.db4o.ManagerMesa;
import com.izv.comandav09.pojo.Carta;
import com.izv.comandav09.pojo.Mesa;

import MiFecha.MiFecha;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static String ip = "http://192.168.43.212:8084/aadcomanda/";
	public String destination="controlador?op=vistamesajson";
	//public static String ip="http://192.168.208.133:8084/aadcomanda/controlador?op=vistamesajson";
	private AdaptadorMesa am;
	private ArrayList<Mesa> listaMesas;
	private ArrayList<Carta> listaCartas;
	private GridView gv;
	
	private ManagerMesa managerMesa;
	private ManagerCarta managerCarta;
	private ManagerFecha managerFecha;
	private MiFecha miFecha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inicio();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			//Si se pulsa en boton de actualizar se lanzara el hilo para actualizarlas
			new HiloMesas().execute(new String[] { ip+destination });
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	public void inicio() {
		
		//Se inicializa el gestor de mesa, gestor carta y el gestor de fecha. 
		//Ya se ha conectado a la bd y si no existia la ha creado
		managerMesa=new ManagerMesa(this);
		managerCarta=new ManagerCarta(this);
		managerFecha=new ManagerFecha(this);
		
		//Utilizo miFecha para saber si debo actualizar la bd o no
		miFecha=new MiFecha();
		miFecha.setTag("mesa");

		gv = (GridView) findViewById(R.id.gvMesas);
		
		//En un caso normal se le asignaria el escuchador asi pero como esto muy exquisito, no hace nada.
		//La clase adaptador tiene un escuchador que hace lo mismo que haria este
		/*gv.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> padre, View v, final int posicion, long id) {
		    	Log.v("ONITEMCLICK", "OK");
		    }
		    
		});*/
		
		//Se lanza verMesas que lo que hace es cargar las mesas de la bd y comprobar
		//si tiene que actualizar la bd o no
		verMesas();
			 
	}

	// Se le pasa la ip y la ruta donde esta el json que devuelve todas las
	// mesas y las pone en el gridview
	public void verMesas() {
		
		//Se guarda en el arraylist la lista con todas las mesas
		listaMesas=new ArrayList<Mesa>(managerMesa.getAll());
		
		//y tambien las cartas
		listaCartas=new ArrayList<Carta>(managerCarta.getAll());
		
		MiFecha lastFechaMesa=null;	
		
		//Dif tendra la diferencia de dias desde que se actualizo hasta hoy.
		//Se inicializa en -1 por si no entra en el siguiente if que no lance un nuevo hilo
		int dif=-1;
				
		if(managerFecha.getFechas("mesa").size()>0){
			
			//Se guarda en lastFechaMesa la fecha que tenga el tag mesa
			lastFechaMesa=new MiFecha(managerFecha.getFechas("mesa").get(0).getMillisString());
									
			dif=miFecha.getDiferenciaEntreDias(lastFechaMesa);
			
			if(dif>0){
				Toast.makeText(MainActivity.this,
						getResources().getString(R.string.mesas_actualizadas_dif_1)+" "+dif+" "+
								getResources().getString(R.string.mesas_actualizadas_dif_2),
						Toast.LENGTH_SHORT).show();
			}
									
		}
		
		//Si no habia fecha de mesas(es la primera vez que se inicia o se ha borrado la bd),
		//o la diferencia es mayor de un dia,
		//o no hay ninguna mesa en la bd,
		//o no hay cartas
		//ejecutara el hilo que conecta con el jsp
		if(lastFechaMesa==null || dif>0 || listaMesas.size()==0 || listaCartas.size()==0){	
			
			new HiloMesas().execute(new String[] { ip+destination });
			
		//Si no carga las mesas en el gridview
		} else {			
			
			showMesas();	
			
		}
			
	}
	
	public void showMesas(){
		//Una vez que se han actualizado se muestran en el gridview
		am = new AdaptadorMesa(MainActivity.this, listaMesas);
		gv.setAdapter(am);
	}
	
	public static String getIp(){
		return ip;
	}

	//Hilo que recoge el JSON del servidor
	private class HiloMesas extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			String texto = "";

			try {

				URL url = new URL(params[0]);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));

				String linea;

				while ((linea = in.readLine()) != null) {

					texto = texto + linea;

				}

				in.close();

			} catch (MalformedURLException e) {				
				return e.toString();
			} catch (IOException e) {
				return e.toString();
			}

			return texto;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			//Cada vez que se actualice hay que borrar todas las mesas de la bd para no duplicarlas
			managerMesa.deleteAll();
			
			//Se borra la fecha de la mesa
			managerFecha.delete("mesa");

			// El tokener "convierte" un string en un objeto JSON
			JSONTokener tokener = new JSONTokener(result);

			try {

				//Se obtiene el padre del json
				JSONObject raiz = new JSONObject(tokener);

				//Se convierte en un array el objeto mesas (porque nos viene como array)
				JSONArray lista = raiz.getJSONArray("mesas");
				
				//Se recorre el json y se guarda en la bd las mesas que se han obtenido
				for (int i = 0; i < lista.length(); i++) {
					JSONObject fila = lista.getJSONObject(i);
					Mesa m = new Mesa(fila);
					managerMesa.add(m);
				}				
				
				managerFecha.add(miFecha);

				managerMesa.commit();
				managerFecha.commit();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Se guarda en el arraylist la lista con todas las mesas
			//Se vuelve a instanciar para evitar duplicidad
			listaMesas=new ArrayList<Mesa>(managerMesa.getAll());
			
			//Se cargan las mesas en el gridview
			showMesas();
						
			//Mostramos un Toast diciendo si se ha actualizado o ha habido algun error
			if(listaMesas.size()==0){
				Toast.makeText(MainActivity.this,
						getResources().getString(R.string.error_conexion),
						Toast.LENGTH_SHORT).show();
			} else{
				Toast.makeText(MainActivity.this,
						getResources().getString(R.string.mesas_actualizadas),
						Toast.LENGTH_SHORT).show();
			}
			
			//Cuando termine este hilo se lanzara otro para actualizar las cartas
			new HiloCartas().execute(new String[] { ip+"controlador?op=vistacartajson" });
			
		}

	}
	
	//Hilo que recoge el JSON del servidor
		private class HiloCartas extends AsyncTask<String, Integer, String> {

			@Override
			protected String doInBackground(String... params) {

				String texto = "";

				try {

					URL url = new URL(params[0]);
					BufferedReader in = new BufferedReader(new InputStreamReader(
							url.openStream()));

					String linea;

					while ((linea = in.readLine()) != null) {

						texto = texto + linea;

					}

					in.close();

				} catch (MalformedURLException e) {				
					return e.toString();
				} catch (IOException e) {
					return e.toString();
				}

				return texto;

			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				//Cada vez que se actualice hay que borrar todas las cartas de la bd para no duplicarlas
				managerCarta.deleteAll();

				// El tokener "convierte" un string en un objeto JSON
				JSONTokener tokener = new JSONTokener(result);

				try {

					//Se obtiene el padre del json
					JSONObject raiz = new JSONObject(tokener);

					//Se convierte en un array el objeto cartas (porque nos viene como array)
					JSONArray lista = raiz.getJSONArray("cartas");
					
					//Se recorre el json y se guarda en la bd las cartas que se han obtenido
					for (int i = 0; i < lista.length(); i++) {
						JSONObject fila = lista.getJSONObject(i);
						Carta c = new Carta(fila);
						managerCarta.add(c);
					}				
					
					managerCarta.commit();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Se guarda en el arraylist la lista con todas las cartas
				//Se vuelve a instanciar para evitar duplicidad
				listaCartas=new ArrayList<Carta>(managerCarta.getAll());
											
				//Mostramos un Toast diciendo si se ha actualizado o ha habido algun error
				if(listaMesas.size()==0){
					Toast.makeText(MainActivity.this,
							getResources().getString(R.string.error_conexion),
							Toast.LENGTH_SHORT).show();
				} else{
					Toast.makeText(MainActivity.this,
							getResources().getString(R.string.cartas_actualizadas),
							Toast.LENGTH_SHORT).show();
				}
				
			}

		}

}
