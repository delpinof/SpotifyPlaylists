package com.appspot.fherdelpino.spotifyapi.endpoint;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class SpotifyEndpointFactory {
	
	private String spotifyAPIURL = "https://api.spotify.com";
	
	private String currentUserProfileURL = spotifyAPIURL.concat("/v1/me");	
	private String userSavedTracks = spotifyAPIURL.concat("/v1/me/tracks");	
	private String createPlaylist = spotifyAPIURL.concat("/v1/users/%s/playlists");
	
	private WebTarget createGenericTarget(String url) {
		Client c = ClientBuilder.newClient();
		return c.target(url);
	}
	
	public WebTarget createCurrentUserProfileTarget() {
		return createGenericTarget(currentUserProfileURL);
	}
	
	public WebTarget createUserSavedTracksTarget() {
		return createGenericTarget(userSavedTracks);
	}
	
	public WebTarget createPlaylistTarget(String userId) {
		return createGenericTarget(String.format(createPlaylist, userId));
	}

}
