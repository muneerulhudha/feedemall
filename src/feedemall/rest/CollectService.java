package feedemall.rest;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.bson.Document;
import org.json.JSONException;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import feedemall.db.DBManager;
import feedemall.util.Util;

@Path("collect")
public class CollectService {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String putProfile(@FormParam("lat") String lat, @FormParam("long") String lng) throws ClientProtocolException, IOException, JSONException {
		
		DBManager manager = DBManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("donations");
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		int flag = 0;
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		String zipcode = Util.getZip(lat, lng);
		
		MongoCursor<Document> donations = collection.find(eq("zipcode", zipcode)).iterator();
		
		while(donations.hasNext()) {
			flag = 1;
			Document match = donations.next();
			
			String time = match.get("currentTime").toString();
			
			String currDate = format1.format(cal.getTime());
			
			String restname = match.get("restName").toString();
			String description = match.get("description").toString();
			String timeleft = currDate;
			String lat1 = match.get("lat").toString();
			String long1 = match.get("long").toString();
						
			result.append("{ \"restname\" : \"").append(restname).append("\", ")
				.append("\"description\" : \"").append(description).append("\", ")
				.append("\"timeleft\" : \"").append(timeleft).append("\", ")
				.append("\"lat\" : \"").append(lat1).append("\", ")
				.append("\"lng\" : \"").append(long1).append("\"},");

		}
		if(flag == 1)
			result = result.replace(result.length() - 1, result.length(), "");
		
		result.append("]");
		
		return result.toString();
		
	}
	
}
