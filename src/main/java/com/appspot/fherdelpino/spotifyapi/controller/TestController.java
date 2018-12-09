package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

@WebServlet(name = "Test", urlPatterns = { "/test" })
public class TestController extends HttpServlet {

	private static final Logger log = Logger.getLogger(TestController.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity credentials = new Entity("SpotifyProperties", "credentials");
		try {
			credentials = datastore.get(credentials.getKey());
		} catch (EntityNotFoundException e) {
			log.log(Level.SEVERE, e.toString());
		}
		
		String client_id = (String) credentials.getProperty("client_id");
		String client_secret = (String) credentials.getProperty("client_secret");
		
		if (client_id == null || client_id.isEmpty()) {
			createSpotifyCredentials( req,  resp);
			resp.sendRedirect("/test");
		}
			
		resp.getWriter().print(String.format("client_id: %s\n", client_id));
		resp.getWriter().print(String.format("client_secret: %s\n", client_secret));
			
	}
	
	private void createSpotifyCredentials(HttpServletRequest req, HttpServletResponse resp) {
		Entity spotifyPropertiesPut = new Entity("SpotifyProperties", "credentials");
		spotifyPropertiesPut.setProperty("client_id", "");
		spotifyPropertiesPut.setProperty("client_secret", "");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(spotifyPropertiesPut);

		Entity spotifyPropertiesGet = null;

		try {
			spotifyPropertiesGet = datastore.get(spotifyPropertiesPut.getKey());
		} catch (EntityNotFoundException e) {
			log.log(Level.SEVERE, e.toString());
		}

		String client_id = (String) spotifyPropertiesGet.getProperty("client_id");
		String client_secret = (String) spotifyPropertiesGet.getProperty("client_secret");

		log.info(client_id);
		log.info(client_secret);
	}

	private void gcs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		Storage storage = StorageOptions.getDefaultInstance().getService();
//
//		log.info(storage.toString());
//		resp.getWriter().println("Buckets:");
//
//		Page<Bucket> buckets = storage.list();
//		for (Bucket bucket : buckets.iterateAll()) {
//			resp.getWriter().println(bucket.toString());
//		}
	}

}
