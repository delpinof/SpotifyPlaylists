package com.appspot.fherdelpino.spotifyapi.endpoint;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SpotifyEndpointHitter {

	private String TOKEN;
	private SpotifyEndpointFactory factory = new SpotifyEndpointFactory();

	public SpotifyEndpointHitter(String token) {
		this.TOKEN = token;
	}

	public JsonObject getCurrentUserProfile() {

		WebTarget t = factory.createCurrentUserProfileTarget();
		Response r = t.request().header("Authorization", "Bearer " + TOKEN).get();

		String bodyString = r.readEntity(String.class);

		JsonObject bodyJson = new Gson().fromJson(bodyString, JsonObject.class);

		return bodyJson;

	}

	public JsonObject getUserSavedTracks(int offset, int limit) {
		WebTarget t = factory.createUserSavedTracksTarget();
		Response r = t.queryParam("offset",offset)
				.queryParam("limit", limit)
				.request()
				.header("Authorization", "Bearer " + TOKEN)
				.get();
		String bodyString = r.readEntity(String.class);

		JsonObject bodyJson = new Gson().fromJson(bodyString, JsonObject.class);

		return bodyJson;
	}
	
	public JsonObject createPlaylist(String userId, String playlistName) {
		WebTarget t = factory.createPlaylistTarget(userId);
		Form form = new Form();
		form.param("name", playlistName);
		Response r = t.request()
				.header("Authorization", "Bearer " + TOKEN)
				.post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
		
		String bodyString = r.readEntity(String.class);

		JsonObject bodyJson = new Gson().fromJson(bodyString, JsonObject.class);
		return bodyJson;
	}

}
