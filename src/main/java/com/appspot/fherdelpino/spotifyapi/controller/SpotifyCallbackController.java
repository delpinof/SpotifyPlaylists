package com.appspot.fherdelpino.spotifyapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.fherdelpino.DataHolder;
import com.appspot.fherdelpino.SpotifyAuthorization;
import com.appspot.fherdelpino.Utils;
import com.appspot.fherdelpino.bean.TrackBean;
import com.appspot.fherdelpino.bean.UserDetailsBean;
import com.appspot.fherdelpino.service.SpotifyService;

@WebServlet(name = "SpotifyCallback", urlPatterns = { "/callback" })
public class SpotifyCallbackController extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SpotifyCallbackController.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String code = request.getParameter("code");

		String client_id = DataHolder.getInstance().getClientId();
		String client_secret = DataHolder.getInstance().getClientSecret();
		
		String redirect_uri = Utils.buildCallback(request);
		
		String token = new SpotifyAuthorization(client_id, client_secret).getAuthorizationCodeToken(code, redirect_uri);

		SpotifyService service = new SpotifyService(token);
		UserDetailsBean userDetails = service.getCurrentUserDetails();
		
		response.getWriter().print("<html><body>");
		response.getWriter().print(String.format("<img src=%s />", userDetails.getImageURL()));	
		
		List<TrackBean> tracks = service.getUserTracks();
		
		response.getWriter().print("<ol>");
		for(TrackBean track : tracks) {
			response.getWriter().print("<li>");
			response.getWriter().print(String.format("%s - %s", track.getArtist(), track.getName()));
			response.getWriter().print("</li>");
		}
		response.getWriter().print("</ol>");

		response.getWriter().print("</body></html>");
	}
	

}
