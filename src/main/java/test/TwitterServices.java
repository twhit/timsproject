package test;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import test.DB;

@Path("/twitter")
public class TwitterServices {
	String consumerKey = "yKH6Fyx0yaSc0awlxralMfggQ";
	String consumerSecret = "KVvvpbmgZaJ1wpTll3AaNvrou3W8FQgZdo7rq2F1rkEnIXg2Am";

	@GET
	@Path("/request")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAuthentication(@Context HttpServletResponse response,
			@Context HttpServletRequest request, @QueryParam("user") String user) {
		Twitter twitter = new TwitterFactory().getInstance();
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}

		try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			System.out.println("In here");
			request.getSession().setAttribute("requestToken", requestToken);
			request.getSession().setAttribute("username", user);
			response.sendRedirect(requestToken.getAuthorizationURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@GET
	@Path("/success")
	@Produces(MediaType.APPLICATION_JSON)
	public String success(@QueryParam("oauth_token") String otoken,
			@QueryParam("oauth_verifier") String oauth_verifier,
			@Context HttpServletRequest request) {
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		RequestToken requestToken = null;
		String user = null;
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out
					.println("The OAuthConsumer has likely already been set, ignore");
		}
		try {
			requestToken = (RequestToken) request.getSession().getAttribute(
					"requestToken");
			if (requestToken == null)
				throw new Exception();
		} catch (Exception e1) {
			return "Could not find valid Request Token";
		}
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken,
					oauth_verifier);
		} catch (TwitterException e1) {
			return "Could not find valid token";
		}
		try {
			DB db = new DB();
			user = (String) request.getSession().getAttribute("username");
			db.saveOAuthToken(accessToken.getToken(), user, "twitter",
					accessToken.getTokenSecret());
		} catch (Exception e) {
			System.out.println("Could not store access token to DB");
		}
		
		try {
			tweetStatus = twitter.updateStatus("Test From Heroku"
					+ System.currentTimeMillis());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted:"
					+ tweetStatus.getText();
		else
			return "BOO! didn't work";
	}

	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	public String status(@QueryParam("user") String user) {
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}
		try {
			DB db = new DB();
			accessToken = db.getOAuthToken(user, "twitter");
			twitter.setOAuthAccessToken(accessToken);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			tweetStatus = twitter.updateStatus("Status Update from Heroku"
					+ System.currentTimeMillis());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted:"
					+ tweetStatus.getText();
		else
			return "BOO! didn't work";
	}
	
	@GET
	@Path("/allstatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String allstatus() {
		Twitter twitter = new TwitterFactory().getInstance();
		Status tweetStatus = null;
		AccessToken accessToken = null;
		
		try {
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
		} catch (Exception e) {
			System.out.println("The OAuthConsumer has likely already been set");
		}
		try {
			DB db = new DB();
			ArrayList<String> users = db.getUserList();
			for (int i = 0; i < users.size(); i++){
				System.out.println("Here: " + users.get(i));
				accessToken = db.getOAuthToken(users.get(i), "twitter");
				twitter.setOAuthAccessToken(accessToken);
				try {
					tweetStatus = twitter.updateStatus("Status Update from Heroku"
						+ System.currentTimeMillis());
					} catch (TwitterException e) {
						e.printStackTrace();
					}
				}	
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		if (tweetStatus != null)
			return "Check your Twitter, your tweet has been posted:"
					+ tweetStatus.getText();
		else
			return "BOO! didn't work";
	}
}
