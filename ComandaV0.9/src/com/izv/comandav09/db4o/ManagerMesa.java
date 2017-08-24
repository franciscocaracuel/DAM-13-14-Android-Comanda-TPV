package com.izv.comandav09.db4o;

import java.util.List;

import com.izv.comandav09.pojo.Mesa;

import android.content.Context;

public class ManagerMesa extends HelperDB4O{
	
	private static ManagerMesa provider=null;
	
	//Se llama al constructor del padre
	public ManagerMesa(Context c){
		super(c);
	}
	
	//Se  instancia el proveedor del DB4O
	public static ManagerMesa getInstance(Context c){
		
		if(provider==null){
			provider=new ManagerMesa(c);
		}

		return provider;
		
	}
	
	//Añade una mesa
	public void add(Mesa m){
		db().store(m);
	}
	
	//Borra la mesa que le pasemos
	public void delete(Mesa m){
		db().delete(m);
	}
	
	//Borrar todas las mesas
	public void deleteAll(){		
		
		List<Mesa> list=getAll();	
		
		for(Mesa m: list){
			delete(m);
		}
		
	}
		
	//Me devuelve todas las mesas
	public List<Mesa> getAll(){
		return db().query(Mesa.class);
	}
	
	@Override
	public void commit(){
		db().commit();
	}
	
	//Devuelve false si hay algún elemento y true si esta vacia
	public boolean isEmpty(){
		
		List<Mesa> list=getAll();
		
		return list.size()==0;
		
	}

}
