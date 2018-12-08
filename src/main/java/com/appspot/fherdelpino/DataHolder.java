package com.appspot.fherdelpino;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class DataHolder {
	
	private static final Logger log = Logger.getLogger(DataHolder.class.getName());
	
	private static DataHolder INSTANCE = new DataHolder();
	
	private String CLIENT_ID;
	private String CLIENT_SECRET;
	
	private DataHolder() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity credentials = new Entity("SpotifyProperties", "credentials");
		try {
			credentials = datastore.get(credentials.getKey());
		} catch (EntityNotFoundException e) {
			log.log(Level.SEVERE, e.toString());
		}
		
		CLIENT_ID = (String) credentials.getProperty("client_id");
		CLIENT_SECRET = (String) credentials.getProperty("client_secret");
	}
	
	public static DataHolder getInstance() {
		return INSTANCE;
	}
	
	public String getClientId() {
		return CLIENT_ID;
	}
	
	public String getClientSecret() {
		return CLIENT_SECRET;
	}
	

}
