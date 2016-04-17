package feedemall.rest;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import feedemall.db.DBManager;
import feedemall.model.Profile;
import feedemall.util.Util;

@Path("profile")
public class UserService {

	@GET
	@Path("/{username}")
	public String getProfile(@PathParam("username") String username) {
		
		DBManager manager = DBManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		
		Document doc = collection.find(eq("username", username)).first();
		
		Profile profile = new Profile(doc.getString("username").toString(), doc.get("password").toString(), doc.get("name").toString(), doc.get("email").toString(), doc.get("address").toString(), doc.get("phoneno").toString());
		
		return Util.toJson(profile);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String putProfile(@FormParam("username") String username, @FormParam("email") String email, @FormParam("name") String name, @FormParam("address") String address, @FormParam("phoneno") String phoneNumber, @FormParam("zipcode") String zipcode) {
		
		DBManager manager = DBManager.getInstance();
		MongoCollection<Document> collection = manager.getDatabase().getCollection("users");
		String result="";
		
		Document existingDoc = collection.find(eq("username", username)).first();
		
		Document doc = new Document("username", username)
					.append("password", existingDoc.get("password"))
					.append("email", email)
					.append("name", name)
					.append("zipcode", zipcode)
	                .append("address", address)
	                .append("phoneno", phoneNumber);
		
		collection.updateOne(eq("username", username), new Document("$set", doc));
		
		result = "{\"success\": true}";	
		return result;
		
	}
	
}
