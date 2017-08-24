package com.izv.comandav09.db4o;

import java.util.List;

import com.izv.comandav09.pojo.Carta;

import android.content.Context;

public class ManagerCarta extends HelperDB4O{
	
	private static ManagerCarta provider=null;
	
	//Se llama al constructor del padre
	public ManagerCarta(Context c){
		super(c);
	}
	
	//Se  instancia el proveedor del DB4O
	public static ManagerCarta getInstance(Context c){
		
		if(provider==null){
			provider=new ManagerCarta(c);
		}

		return provider;
		
	}
	
	//Añade una carta
	public void add(Carta c){
		db().store(c);
	}
	
	//Borra la carta que le pasemos
	public void delete(Carta c){
		db().delete(c);
	}
	
	//Borrar todas las cartas
	public void deleteAll(){		
		
		List<Carta> list=getAll();	
		
		for(Carta c: list){
			delete(c);
		}
		
	}
	
	public List<Carta> getCarta(Carta c){
		return db().queryByExample(c);
	}
		
	//Me devuelve todas las cartas
	public List<Carta> getAll(){
		return db().query(Carta.class);
	}
	
	@Override
	public void commit(){
		db().commit();
	}
	
	//Devuelve false si hay algún elemento y true si esta vacia
	public boolean isEmpty(){
		
		List<Carta> list=getAll();
		
		return list.size()==0;
		
	}

}
