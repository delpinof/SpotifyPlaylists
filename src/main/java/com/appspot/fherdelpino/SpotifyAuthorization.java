package com.appspot.fherdelpino;

import java.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SpotifyAuthorization {

	private String client_id;
	private String client_secret;
	private final String tokenURL = "https://accounts.spotify.com/api/token";

	public SpotifyAuthorization(String client_id, String client_secret) {
		this.client_id = client_id;
		this.client_secret = client_secret;
	}

	/**
	 * This flow is suitable for long-running applications in which the user grants
	 * permission only once. It provides an access token that can be refreshed.
	 * Since the token exchange involves sending your secret key, perform this on a
	 * secure location, like a backend service, and not from a client such as a
	 * browser or from a mobile app.
	 * 
	 * @param code
	 * @return The token
	 */
	public String getAuthorizationCodeToken(String code, String redirect_uri) {

		String tmp = client_id + ":" + client_secret;
		byte[] encodedBytes = Base64.getEncoder().encode(tmp.getBytes());
		String auth = new String(encodedBytes);

		Client c = ClientBuilder.newClient();
		WebTarget tokenTarget = c.target(tokenURL);

		Form form = new Form();
		form.param("grant_type", "authorization_code");
		form.param("code", code);
		form.param("redirect_uri", redirect_uri);

		Response r = tokenTarget.request().header("Authorization", "Basic " + auth).post(Entity.form(form));

		String bodyString = r.readEntity(String.class);

		JsonObject bodyJson = new Gson().fromJson(bodyString, JsonObject.class);

		String token = bodyJson.get("access_token").getAsString();

		return token;

	}

	/**
	 * The Client Credentials flow is used in server-to-server authentication. Only
	 * endpoints that do not access user information can be accessed. The advantage
	 * here in comparison with requests to the Web API made without an access token,
	 * is that a higher rate limit is applied.
	 * 
	 * @return The token
	 */
	public String getClientCredentialToken() {

		String tmp = client_id + ":" + client_secret;
		byte[] encodedBytes = Base64.getEncoder().encode(tmp.getBytes());
		String auth = new String(encodedBytes);

		Client c = ClientBuilder.newClient();
		WebTarget tokenTarget = c.target(tokenURL);

		Form form = new Form();
		form.param("grant_type", "client_credentials");

		Response r = tokenTarget.request().header("Authorization", "Basic " + auth).post(Entity.form(form));

		String bodyString = r.readEntity(String.class);

		JsonObject bodyJson = new Gson().fromJson(bodyString, JsonObject.class);

		String token = bodyJson.get("access_token").getAsString();

		return token;

	}
}
