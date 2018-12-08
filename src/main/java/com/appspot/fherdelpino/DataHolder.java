package com.appspot.fherdelpino;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataHolder {
	
	private static DataHolder INSTANCE = new DataHolder();
	
	private String CLIENT_ID = "";
	private String CLIENT_SECRET = "";
	
	private DataHolder() {
		InputStream is = getClass().getResourceAsStream("resources/application.properties");
		Properties prop = null;
		if (is != null) {
			try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(prop);
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
