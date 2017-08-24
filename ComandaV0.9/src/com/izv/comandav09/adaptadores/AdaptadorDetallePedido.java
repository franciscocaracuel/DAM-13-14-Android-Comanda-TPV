package com.izv.comandav09.adaptadores;

import java.util.ArrayList;

import com.izv.comandav08.R;
import com.izv.comandav09.PedidoActivity;
import com.izv.comandav09.db4o.ManagerCarta;
import com.izv.comandav09.pojo.Carta;
import com.izv.comandav09.pojo.DetallePedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AdaptadorDetallePedido extends ArrayAdapter<DetallePedido> {

	private Context context;
	private ArrayList<DetallePedido> lista;
	private ManagerCarta managerCarta;

	public AdaptadorDetallePedido(Context c, ArrayList<DetallePedido> l) {
		super(c, R.layout.detalle_detalle_pedido, l);
		this.context = c;
		this.lista = l;
		managerCarta=new ManagerCarta(c);
	}

	@Override
	public View getView(final int posicion, View vista, ViewGroup padre) {

		// Dibuja las lineas
		if (vista == null) {
			LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista = i.inflate(R.layout.detalle_detalle_pedido, null);
		}
		
		final DetallePedido dp=lista.get(posicion);
		
		Carta c=new Carta(dp.getIdcarta(), null, 0);
		c=managerCarta.getCarta(c).get(0);

		TextView tvNombreDetallePedido=(TextView)vista.findViewById(R.id.tvNombreDetallePedido);
		tvNombreDetallePedido.setText(c.getNombre());

		TextView tvCantidad=(TextView)vista.findViewById(R.id.tvCantidadDetallePedido);
		tvCantidad.setText(dp.getCantidad()+"");

		Button btMas=(Button)vista.findViewById(R.id.btMasDetallePedido);
		btMas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				((PedidoActivity)context).sumarCantidad(dp);

			}
		});

		Button btMenos=(Button)vista.findViewById(R.id.btMenosDetallePedido);
		btMenos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				((PedidoActivity)context).restarCantidad(dp);

			}
		});
		
		return vista;

	}


}
