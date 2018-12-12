package com.appspot.fherdelpino.service;

import java.util.ArrayList;
import java.util.List;

import com.appspot.fherdelpino.bean.TrackBean;
import com.appspot.fherdelpino.bean.UserDetailsBean;
import com.appspot.fherdelpino.spotifyapi.endpoint.SpotifyEndpointHitter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SpotifyService {

	private SpotifyEndpointHitter HIT;

	public SpotifyService(String token) {

		HIT = new SpotifyEndpointHitter(token);
	}

	public UserDetailsBean getCurrentUserDetails() {

		JsonObject userProfile = HIT.getCurrentUserProfile();

		JsonElement image1 = userProfile.get("images").getAsJsonArray().get(0);
		String imageURL = image1.getAsJsonObject().get("url").toString();

		String userId = userProfile.get("id").toString();
		return new UserDetailsBean(userId, imageURL);

	}

	public List<TrackBean> getUserTracks() {

		List<TrackBean> tracks = new ArrayList<>();
		String nextTracksURL = "";
		int factor = 50, cont = 0;
		JsonObject userTracks;

		while (!nextTracksURL.equals("null")) {
			userTracks = HIT.getUserSavedTracks(factor * cont, factor);

			for (JsonElement item : userTracks.get("items").getAsJsonArray()) {
				tracks.add(getTrackData(item));
			}

			cont++;
			nextTracksURL = userTracks.get("next").toString();
		}
		return tracks;
	}

	private TrackBean getTrackData(JsonElement item) {

		JsonObject track = item.getAsJsonObject().get("track").getAsJsonObject();
		String id = track.get("id").toString();
		String trackname = track.get("name").toString();
		String artist = track.get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").toString();
		return new TrackBean(id, trackname, artist);
	}

	public String createPlaylist(String userId, String playlistName) {
		JsonObject response = HIT.createPlaylist(userId, playlistName);
		
		return response.toString();		
		
	}

}
