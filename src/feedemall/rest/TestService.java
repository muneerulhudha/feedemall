package feedemall.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import org.json.*;

import feedemall.util.Util;

@Path("test")
public class TestService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String login() throws ClientProtocolException, IOException, JSONException {
		
		String result = Util.getFairEstimate("32.7651926", "-96.7975922", "32.9982987", "-96.7357652");
		
		return result;
		
	}
	
}
