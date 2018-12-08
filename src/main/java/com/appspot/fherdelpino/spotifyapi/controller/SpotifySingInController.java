package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.fherdelpino.DataHolder;
import com.appspot.fherdelpino.Utils;

@WebServlet(name = "SpotifySingIn", urlPatterns = { "/spotifylogin" })
public class SpotifySingInController extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SpotifySingInController.class.getName());
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String redirect_uri = Utils.buildCallback(request);
		
		StringBuilder sb = new StringBuilder("https://accounts.spotify.com/authorize?")
				.append("client_id=" + DataHolder.getInstance().getClientId())
				.append("&response_type=code")
				.append("&redirect_uri=".concat(redirect_uri))
				.append("&scope=user-read-private user-read-email user-library-read");
		//TODO: add state according to https://developer.spotify.com/documentation/general/guides/authorization-guide/#authorization-code-flow

		response.sendRedirect(sb.toString());

	}

}
