package feedemall.rest;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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

@Path("donate")
public class DonateService {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String putProfile(@FormParam("restName") String restName, @FormParam("address") String address, @FormParam("lat") String lat, @FormParam("long") String longitude, @FormParam("availableFor") String availableFor, @FormParam("until") String until, @FormParam("description") String description, @FormParam("phoneno") String phoneno, @FormParam("zipcode") String zipcode) throws ClientProtocolException, IOException, JSONException {
		
		DBManager manager = DBManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("donations");
		MongoCollection<Document> userColl = manager.getDatabase().getCollection("users");
		String result = "";
		String time;
		Calendar calendar = Calendar.getInstance();
		
		Date date = calendar.getTime();
		
		time = date.toString();
		
		Document newDoc = new Document("restName", restName)
	               .append("address", address)
	               .append("lat", lat)
	               .append("long", longitude)
	               .append("availableFor", availableFor)
	               .append("until", until)
	               .append("description", description)
	               .append("phoneno", phoneno)
	               .append("zipcode", zipcode)
	               .append("currentTime", time);
		
		collection.insertOne(newDoc);
		
		MongoCursor<Document> volunteers = userColl.find(eq("zipcode", zipcode)).iterator();
		
		while(volunteers.hasNext()) {
			
			Document match = volunteers.next();
			String phone = match.get("phoneno").toString();
			String add = match.get("address").toString();
			String endlat = Util.getLat(address);
			String endlong = Util.getLong(address);
			
			StringBuilder message = new StringBuilder();
			message.append("Food available for ");
			message.append(availableFor);
			message.append(" people at ");
			message.append(restName);
			message.append(". Restaurant contact number : ");
			message.append(phoneno);
			message.append(". Taking a uber would cost: ");
			message.append(Util.getFairEstimate(lat, longitude,  endlat, endlong));
			
			Util.sendSMS(phone, message.toString());
		}
		
		result = "{\"success\": true}";	
		return result;
	}
	
}
