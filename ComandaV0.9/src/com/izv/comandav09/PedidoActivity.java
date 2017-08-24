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
import com.izv.comandav09.adaptadores.AdaptadorCarta;
import com.izv.comandav09.adaptadores.AdaptadorDetallePedido;
import com.izv.comandav09.db4o.ManagerCarta;
import com.izv.comandav09.pojo.Carta;
import com.izv.comandav09.pojo.DetallePedido;
import com.izv.comandav09.pojo.Pedido;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PedidoActivity extends Activity {
	
	//Elementos gráficos
	private TextView tvMesaMesa, tvMesaPedido, tvMesaHora;
	private ListView lvDetallePedido;
		
	//Mis variables
	private String ip, destinationPedido;
	private Pedido pedido;
	private long idMesa=-1;	
	private String nombreMesa;
	private AdaptadorDetallePedido adp;
	
	private ArrayList<Carta> listaCarta;
	private ArrayList<DetallePedido> listaDetallePedido;
	
	private ManagerCarta managerCarta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido);
		
		//Se recoge el id que nos llega por el intent
		Bundle b=getIntent().getExtras();
		if(b!=null){
			idMesa=b.getLong("id");
			nombreMesa=b.getString("nombre");
		}
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_mesa, menu);
		return true;
	}
	
	@Override
	protected void onResume(){		
		super.onResume();
	
		inicio();
		
	}
	
	@Override
	public void onPause(){		
		super.onPause();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		
			case R.id.action_cerrarPedido:
				cerrarPedido();
				return true;
		
			case R.id.action_insertarCarta:
				insertarCarta();
				return true;
				
		}
		
		return false;
		
	}
	
	private void inicio(){
		
		ip=MainActivity.getIp();
		destinationPedido="controlador?op=vistadetallepedidojson&id="+idMesa;
		
		managerCarta=new ManagerCarta(this);
		
		listaCarta=new ArrayList<Carta>();
		listaDetallePedido=new ArrayList<DetallePedido>();
		
		//Se asignan los textview y los valores
		tvMesaMesa=(TextView)findViewById(R.id.tvMesaMesa);
		tvMesaPedido=(TextView)findViewById(R.id.tvMesaPedido);	
		tvMesaHora=(TextView)findViewById(R.id.tvMesaHora);		
		lvDetallePedido=(ListView)findViewById(R.id.lvDetallePedido);
				
		new HiloDetallePedido().execute(new String[] { ip+destinationPedido });
				
	}
	
	public void insertarPedido(){
		
		String destinationPedidoCrear="controlador?op=opinsertarpedido&idmesa="+idMesa+"&"+
				"cerrado="+0;
				
		new HiloGet().execute(new String[] { ip+destinationPedidoCrear });
		
	}
	
	public void cerrarPedido(){
		
		// Se crea un dialogo para confirmar el borrado
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle(R.string.titulo_confirmacion_cerrar);
		// ad.setMessage(""); Esto añadiria un mensaje debajo del titulo

		// Si se pulsa en ok se borra
		ad.setPositiveButton(R.string.dialogo_mensaje_si,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
						Log.v("comanda", pedido.getId()+"");
						
						String destinationPedidoCerrar="controlador?op=cerrarpedidojson&id="+pedido.getId();
						
						new HiloGet().execute(new String[] { ip+destinationPedidoCerrar });
						
						insertarPedido();
						
					}
				});

		// Si se pulsa en cancelar nada
		ad.setNegativeButton(R.string.dialogo_mensaje_cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// Se muestra el dialogo
		ad.show();
				
	}
	
	public void reabrirPedido(){
		
		// Se crea un dialogo para confirmar el borrado
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle(R.string.titulo_confirmacion_reabrir);
		//Esto añadiria un mensaje debajo del titulo
		ad.setMessage(R.string.mensaje_reabrir); 

		// Si se pulsa en ok se borra
		ad.setPositiveButton(R.string.dialogo_mensaje_si,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						
						
					}
				});

		// Si se pulsa en cancelar nada
		ad.setNegativeButton(R.string.dialogo_mensaje_cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// Se muestra el dialogo
		ad.show();
								
	}
	
	public void showDetallePedidos(){
		
		adp=new AdaptadorDetallePedido(this, listaDetallePedido);
		lvDetallePedido.setAdapter(adp);
			
	}
	
	public void sumarCantidad(DetallePedido dp){
		
		String destinationPedidoSumar="controlador?op=opeditardetallepedido&id="+dp.getId()+"&"+
				"idpedido="+dp.getIdpedido()+"&"+
				"idcarta="+dp.getIdcarta()+"&"+
				"cantidad="+(dp.getCantidad()+1)+"&"+
				"precio="+dp.getPrecio()+"&"+
				"pk="+dp.getId();
				
		new HiloGet().execute(new String[] { ip+destinationPedidoSumar });
		
	}
	
	public void restarCantidad(final DetallePedido dp){
		
		if(dp.getCantidad()-1>=1){
			
			String destinationPedidoRestar="controlador?op=opeditardetallepedido&id="+dp.getId()+"&"+
					"idpedido="+dp.getIdpedido()+"&"+
					"idcarta="+dp.getIdcarta()+"&"+
					"cantidad="+(dp.getCantidad()-1)+"&"+
					"precio="+dp.getPrecio()+"&"+
					"pk="+dp.getId();
					
			new HiloGet().execute(new String[] { ip+destinationPedidoRestar });
			
		} else if(dp.getCantidad()-1==0){
			
			// Se crea un dialogo para confirmar el borrado
			AlertDialog.Builder ad = new AlertDialog.Builder(this);
			ad.setTitle(R.string.titulo_confirmacion_borrar_detalle);
			//Esto añadiria un mensaje debajo del titulo
			ad.setMessage(R.string.mensaje_borrar_detalle); 

			// Si se pulsa en ok se borra
			ad.setPositiveButton(R.string.dialogo_mensaje_si,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							String destinationDetallePedidoBorrar="controlador?" +
									"op=opborrardetallepedido&id="+dp.getId()+"&idpedido="+dp.getIdpedido();
							
							new HiloGet().execute(new String[] { ip+destinationDetallePedidoBorrar });
							
						}
					});

			// Si se pulsa en cancelar nada
			ad.setNegativeButton(R.string.dialogo_mensaje_cancelar,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			// Se muestra el dialogo
			ad.show();
			
		}
		
	}
	
	public void insertarCarta(){
		
		listaCarta=new ArrayList<Carta>(managerCarta.getAll());
		
		final Dialog d=new Dialog(this);
		d.setContentView(R.layout.dialog_cartas);
		d.setTitle(getResources().getString(R.string.titulo_dialog_cartas));
		d.setCanceledOnTouchOutside(true);
		
		final ListView lvCartas=(ListView)d.findViewById(R.id.lvCartas);
		
		lvCartas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				
				String destinationPedidoAñadir="controlador?op=opinsertardetallepedido&idpedido="+pedido.getId()+"&"+
						"carta="+listaCarta.get(pos).getId()+"&"+
						"cantidad="+1;
						
				new HiloGet().execute(new String[] { ip+destinationPedidoAñadir });
								
				d.cancel();
				
			}
		});
				
		final AdaptadorCarta ag=new AdaptadorCarta(this, listaCarta);
		registerForContextMenu(lvCartas);
		lvCartas.setAdapter(ag);
		
		d.show();
		
	}
	
	//Hilo que recoge el JSON del servidor
		private class HiloDetallePedido extends AsyncTask<String, Integer, String> {

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
				
				listaDetallePedido=new ArrayList<DetallePedido>();
				
				// El tokener "convierte" un string en un objeto JSON
				JSONTokener tokener = new JSONTokener(result);
				
				Log.v("comanda", tokener.toString());
				
				try {

					//Se obtiene el padre del json
					JSONObject raiz = new JSONObject(tokener);
					
					//Se convierte en un array (porque nos viene como array)
					JSONArray lista = raiz.getJSONArray("detallespedidocartas");
					
					//Se recorre el json
					for (int i = 0; i < lista.length(); i++) {
						JSONObject fila = lista.getJSONObject(i);
						listaDetallePedido.add(new DetallePedido((JSONObject)fila.get("detallePedido")));
						listaCarta.add(new Carta((JSONObject)fila.get("carta")));
					}	
										
					pedido=new Pedido(raiz.getJSONObject("pedido"));
					
				} catch (JSONException e) {
					//Si da un error el JSON significa que no hay pedidos para esa mesa, por tanto se creara uno nuevo
					insertarPedido();
				}
								
				if(pedido!=null){
					
					tvMesaPedido.setText(pedido.getId()+"");
					
					tvMesaMesa.setText(nombreMesa);
					
					tvMesaHora.setText(pedido.getFechaHora());
									
					showDetallePedidos();
					
				}
				
			}

		}

	// Hilo que recoge el JSON del servidor
	private class HiloGet extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			try {

				URL url = new URL(params[0]);
				
				url.openStream();

			} catch (MalformedURLException e) {
				Log.v("comanda", "Error MALFORMED --> "+e.getCause());
				return e.toString();
			} catch (IOException e) {
				Log.v("comanda", "IOEXCEPTION --> "+e.toString());
				return e.toString();
			}
			
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			new HiloDetallePedido().execute(new String[] { ip+destinationPedido });

		}

	}

}
