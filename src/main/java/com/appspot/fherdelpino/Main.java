package com.appspot.fherdelpino;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

	public static void main(String args) throws IOException {
		new Main().run();
		
	}
	
	public void run() throws IOException {
		InputStream is = getClass().getResourceAsStream("application.properties");
		Properties prop = null;
		if (is != null) {
			prop.load(is);
		}
		System.out.println(prop);
		
	}
}
