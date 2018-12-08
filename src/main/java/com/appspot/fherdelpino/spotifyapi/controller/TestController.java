package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.gax.paging.Page;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@WebServlet(name = "Test", urlPatterns = { "/test" })
public class TestController extends HttpServlet {

	private static final Logger log = Logger.getLogger(TestController.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();

		log.info(storage.toString());
		resp.getWriter().println("Buckets:");

		Page<Bucket> buckets = storage.list();
		for (Bucket bucket : buckets.iterateAll()) {
			resp.getWriter().println(bucket.toString());
		}

	}

}
