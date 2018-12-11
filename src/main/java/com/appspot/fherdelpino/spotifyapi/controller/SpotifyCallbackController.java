package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
		
		JsonObject userProfile = hit.getCurrentUserProfile();
		
		
		JsonElement image1 = userProfile.get("images").getAsJsonArray().get(0);
		String imageURL = image1.getAsJsonObject().get("url").toString();
		
		response.getWriter().print("<html><body>");
		response.getWriter().print(String.format("<img src=%s />", imageURL));	
		
		
		Map<String,String> tracks = getTracks(token);
		
		response.getWriter().print("<ol>");
		for(String track : tracks.keySet()) {
			response.getWriter().print("<li>");
			response.getWriter().print(String.format("%s - %s", tracks.get(track), track));
			response.getWriter().print("</li>");
		}
		response.getWriter().print("</ol>");

		response.getWriter().print("</body></html>");
	}
	
	private Map<String,String> getTracks(String token) {
		
		Map<String,String> result = new HashMap<>();
		SpotifyEndpointHitter hit = new SpotifyEndpointHitter(token);
		
		String nextTracksURL = "tracks", trackName,artist;
		int factor = 50, cont = 0;
		JsonObject userTracks, track;

		
		while (!nextTracksURL.equals("null")) {
			userTracks = hit.getUserSavedTracks(factor*cont,factor);
			
			for(JsonElement item : userTracks.get("items").getAsJsonArray()) {
				track = item.getAsJsonObject().get("track").getAsJsonObject();
				trackName = track.get("name").toString();
				artist = track.get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").toString();
				log.info(String.format("%s - %s",artist,trackName));
				result.put(trackName, artist);
			}
			
			cont++;
			nextTracksURL = userTracks.get("next").toString();
		}
		
		return result;
	}

}
