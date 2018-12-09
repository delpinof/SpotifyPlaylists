package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.fherdelpino.DataHolder;
import com.appspot.fherdelpino.SpotifyAuthorization;
import com.appspot.fherdelpino.Utils;

@WebServlet(name = "SpotifyCallback", urlPatterns = { "/callback" })
public class SpotifyCallbackController extends HttpServlet {

	private static final Logger log = Logger.getLogger(SpotifyCallbackController.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String code = request.getParameter("code");
		if (code != null && !code.isEmpty()) {

			String client_id = DataHolder.getInstance().getClientId();
			String client_secret = DataHolder.getInstance().getClientSecret();

			String redirect_uri = Utils.buildCallback(request);

			String token = new SpotifyAuthorization(client_id, client_secret)
					.getAuthorizationCodeToken(code,redirect_uri);

			request.setAttribute("token", token);
			request.getRequestDispatcher("/gettracks").forward(request, response);
		} else {

			String error = request.getParameter("error");

			response.getWriter().println(error);
		}
	}

}
