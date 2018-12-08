package com.appspot.fherdelpino;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	
	public static String buildCallback(HttpServletRequest request) {
		String server = request.getServerName();
		int port = request.getServerPort();
		String redirect_uri; 
		
		if (server.contains("localhost"))
			 redirect_uri = String.format("http://%s:%d/callback", server,port);
		else
			redirect_uri = String.format("http://%s/callback", server);
		
		return redirect_uri;
	}

}
