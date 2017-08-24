package com.izv.comandav09.pojo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fran
 */
public class DetallePedido implements Serializable{

	private static final long serialVersionUID = 7277464510224868347L;
	long id, idpedido, idcarta;
    int cantidad;
    double precio;
    
    public DetallePedido(){
        this(0, 0, 0, 0, 0);
    }

    public DetallePedido(long id, long idpedido, long idcarta, int cantidad, double precio) {
        this.id = id;
        this.idpedido = idpedido;
        this.idcarta = idcarta;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    public DetallePedido(String[] registro){
     set(registro, 0);
    }
    
    public DetallePedido(JSONObject json) throws JSONException{
    	this(json.getLong("id"), json.getLong("idpedido"), json.getLong("idcarta"),
    			json.getInt("cantidad"), json.getDouble("precio"));
    }


    public final void set(String[] registro, int inicial){
        this.id=Long.parseLong(registro[0+inicial]);
        this.idpedido=Long.parseLong(registro[1+inicial]);
        this.idcarta=Long.parseLong(registro[2+inicial]);
        this.cantidad=Integer.parseInt(registro[3+inicial]);
        this.precio=Double.parseDouble(registro[4+inicial]);
    }

    public long getId() {
        return id;
    }

    public long getIdpedido() {
        return idpedido;
    }

    public long getIdcarta() {
        return idcarta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdpedido(long idpedido) {
        this.idpedido = idpedido;
    }

    public void setIdcarta(long idcarta) {
        this.idcarta = idcarta;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "id=" + id + ", idpedido=" + idpedido + ", idcarta=" + idcarta + ", cantidad=" + cantidad + ", precio=" + precio + '}';
    }
    
}
