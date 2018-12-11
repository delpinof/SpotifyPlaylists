package com.appspot.fherdelpino.bean;

public class UserDetailsBean {

	private String userId;
	private String imageURL;

	public UserDetailsBean(String userId, String imageURL) {
		super();
		this.userId = userId;
		this.imageURL = imageURL;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}
