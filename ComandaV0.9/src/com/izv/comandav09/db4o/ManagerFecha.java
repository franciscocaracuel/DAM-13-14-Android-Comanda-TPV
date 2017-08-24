package com.izv.comandav09.db4o;

import java.util.List;

import MiFecha.MiFecha;
import android.content.Context;

public class ManagerFecha extends HelperDB4O{
	
	private static ManagerFecha provider=null;
	
	//Se llama al constructor del padre
	public ManagerFecha(Context c){
		super(c);
	}
	
	//Se  instancia el proveedor del DB4O
	public static ManagerFecha getInstance(Context c){
		
		if(provider==null){
			provider=new ManagerFecha(c);
		}

		return provider;
		
	}
	
	//Añade una fecha
	public void add(MiFecha miFecha){
		db().store(miFecha);
	}
	
	//Borra la fecha que le pasemos
	public void delete(MiFecha miFecha){
		db().delete(miFecha);
	}
	
	//Borra la fecha que tenga el tag que se le pase
	public void delete(String tag){
		
		List<MiFecha> list=getAll();
		
		for(MiFecha miFecha: list){
			if(miFecha.getTag().equals("mesa"))
				delete(miFecha);
		}
		
	}
		
	//Borra todas las fechas
	public void deleteAll(){		
		
		List<MiFecha> list=getAll();	
		
		for(MiFecha miFecha: list){
			delete(miFecha);
		}
		
	}
		
	//Me devuelve todas las fechas
	public List<MiFecha> getAll(){
		return db().query(MiFecha.class);
	}
	
	//Devuelve una lista con todas las mesas que coincidan con el tag que le paso
	public List<MiFecha> getFechas(String tag){
		return db().queryByExample(new MiFecha(null, tag, null));
	}
	
	@Override
	public void commit(){
		db().commit();
	}
	
	//Devuelve false si hay algún elemento y true si esta vacia
	public boolean isEmpty(){
		
		List<MiFecha> list=getAll();
		
		return list.size()==0;
		
	}

}
