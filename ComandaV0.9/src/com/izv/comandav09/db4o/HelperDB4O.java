package com.izv.comandav09.db4o;

import android.content.Context;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

public class HelperDB4O {
	
	private static ObjectContainer connection=null;
	private Context context;
	
	public HelperDB4O(Context c){
		context=c;
	}
	
	
	//Abre la conexion a la bd
	public ObjectContainer db(){
		
		if(connection==null || connection.ext().isClosed()){
			connection=Db4oEmbedded.openFile(dbConfig(), context.getExternalFilesDir(null)+"/bd.db4o");
		}
		
		return connection;
		
	}
	
	
	private EmbeddedConfiguration dbConfig(){
		
		EmbeddedConfiguration configuration= Db4oEmbedded.newConfiguration();
		
		return configuration;
		
	}
	
	public void commit(){
		connection.commit();
	}
	
	public void close(){
		
		if(connection!=null){
			connection.close();
		}
		
	}
	
}
