package com.appspot.fherdelpino.bean;

public class TrackBean {

	private String id;
	private String name;
	private String artist;

	public TrackBean(String id, String name, String artist) {
		super();
		this.id = id;
		this.name = name;
		this.artist = artist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

}
