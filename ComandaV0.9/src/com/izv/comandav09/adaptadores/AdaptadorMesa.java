package com.izv.comandav09.adaptadores;

import java.util.ArrayList;

import com.izv.comandav08.R;
import com.izv.comandav09.PedidoActivity;
import com.izv.comandav09.pojo.Mesa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AdaptadorMesa extends ArrayAdapter<Mesa>{
	
	private Context context;
	private ArrayList<Mesa> lista;
	
	private TextView tvNombre;
	
	public AdaptadorMesa(Context c, ArrayList<Mesa> l){
		super(c, R.layout.detalle_mesa, l);
		this.context=c;
		this.lista=l;
	}
	
	public View getView(final int posicion, View vista, ViewGroup padre){
		
		//Dibuja las lineas
		if(vista==null){
			LayoutInflater i=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista=i.inflate(R.layout.detalle_mesa, null);
		}
		
		final Mesa mesa=lista.get(posicion);
		
		tvNombre=(TextView)vista.findViewById(R.id.tvNombreMesa);
		tvNombre.setText(mesa.getNombre());
		
		//Cuando se pulse sobre una mesa se lanzara su pedido
		Button bt=(Button)vista.findViewById(R.id.btMesa);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PedidoActivity.class);
				intent.putExtra("id", mesa.getId());
				intent.putExtra("nombre", mesa.getNombre());
				context.startActivity(intent);
			}
		});
		
		return vista;
		
	}

}
