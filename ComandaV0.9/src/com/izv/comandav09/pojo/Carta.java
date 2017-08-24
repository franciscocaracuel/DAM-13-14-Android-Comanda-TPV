package com.izv.comandav09.pojo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Carta implements Serializable{

	private static final long serialVersionUID = 1578475777325256148L;
	private long id;
    private String nombre;
    private double precio;

    public Carta() {
        this(0,"", 0);
    }

    public Carta(long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public Carta(String[] registro) {
        set(registro, 0);
    }
    
    public Carta(String[] registro, int inicial){
        set(registro, inicial);
    }

    public Carta(JSONObject json) throws JSONException{
    	this(json.getLong("id"), json.getString("nombre"), json.getDouble("precio"));
    }
    
    public final void set(String[] registro, int inicial){
        if(registro!=null){
            this.id = Long.parseLong(registro[0+inicial]);
            this.nombre = registro[1+inicial];
            this.precio = Double.parseDouble(registro[2+inicial]);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Carta{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + '}';
    }
    
}