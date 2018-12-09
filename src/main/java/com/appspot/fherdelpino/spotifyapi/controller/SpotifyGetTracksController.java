package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.fherdelpino.spotifyapi.endpoint.SpotifyEndpointHitter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@WebServlet(name = "SpotifyGetTracks", urlPatterns = { "/gettracks" })
public class SpotifyGetTracksController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = String.valueOf(request.getParameter("token"));
		
		SpotifyEndpointHitter hit = new SpotifyEndpointHitter(token);
		
		JsonObject currentUserJson = hit.getCurrentUserProfile();
		JsonObject savedTracksJson = hit.getUserSavedTracks();
		
		JsonElement userImageJson = currentUserJson.get("images").getAsJsonArray().get(0);
		String imageURL = userImageJson.getAsJsonObject().get("url").toString();
		
		response.getWriter().print("<html><body>");
		response.getWriter().print(String.format("<img src=%s />", imageURL));	
		
		JsonElement track = null;		
		response.getWriter().print("<ul>");
		for(JsonElement item : savedTracksJson.get("items").getAsJsonArray()) {
			response.getWriter().print("<li>");
			track = item.getAsJsonObject().get("track");
			response.getWriter().print(track.getAsJsonObject().get("name").toString());
			response.getWriter().print("</li>");
		}
		response.getWriter().print("</ul>");

		
		
		response.getWriter().print("</body></html>");

	}
	
	
}
