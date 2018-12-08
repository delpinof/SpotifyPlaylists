package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.fherdelpino.DataHolder;
import com.appspot.fherdelpino.SpotifyAuthorization;
import com.appspot.fherdelpino.Utils;
import com.appspot.fherdelpino.spotifyapi.endpoint.SpotifyEndpointHitter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@WebServlet(name = "SpotifyCallback", urlPatterns = { "/callback" })
public class SpotifyCallbackController extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SpotifyCallbackController.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String code = request.getParameter("code");

		String client_id = DataHolder.getInstance().getClientId();
		String client_secret = DataHolder.getInstance().getClientSecret();
		
		String redirect_uri = Utils.buildCallback(request);
		
		String token = new SpotifyAuthorization(client_id, client_secret).getAuthorizationCodeToken(code, redirect_uri);

		SpotifyEndpointHitter hit = new SpotifyEndpointHitter(token);
		
		JsonObject json = hit.getCurrentUserProfile();
		JsonObject jsonResponse = hit.getUserSavedTracks();
		
		JsonElement image1 = json.get("images").getAsJsonArray().get(0);
		String imageURL = image1.getAsJsonObject().get("url").toString();
		
		response.getWriter().print("<html><body>");
		response.getWriter().print(String.format("<img src=%s />", imageURL));	
		
		JsonElement track = null;		
		response.getWriter().print("<ul>");
		for(JsonElement item : jsonResponse.get("items").getAsJsonArray()) {
			response.getWriter().print("<li>");
			track = item.getAsJsonObject().get("track");
			response.getWriter().print(track.getAsJsonObject().get("name").toString());
			response.getWriter().print("</li>");
		}
		response.getWriter().print("</ul>");

		
		
		response.getWriter().print("</body></html>");
	}

}
