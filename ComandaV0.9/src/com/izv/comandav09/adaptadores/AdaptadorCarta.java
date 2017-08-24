package com.izv.comandav09.adaptadores;

import java.io.File;
import java.util.ArrayList;

import com.izv.comandav08.R;
import com.izv.comandav09.pojo.Carta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorCarta extends ArrayAdapter<File>{
	
	private Context contexto;
	private ArrayList<Carta> lista;
	
	public AdaptadorCarta(Context c, ArrayList<Carta> l){
		super(c, R.layout.detalle_carta);
		this.contexto=c;
		this.lista=l;
	}
	
	@Override
	public int getCount() {
	   return lista.size();
	}

	public View getView(int posicion, View vista, ViewGroup padre){
		
		if(vista==null){
			LayoutInflater i=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista=i.inflate(R.layout.detalle_carta, null);
		}
		
		TextView tvNombre=(TextView)vista.findViewById(R.id.tvNombreCarta);
		
		tvNombre.setText(lista.get(posicion).getNombre());
		
		return vista;
		
	}

}