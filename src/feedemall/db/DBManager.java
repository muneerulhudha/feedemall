package feedemall.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DBManager {

	private static DBManager instance = null;
	
	MongoClient mongoClient;
	MongoDatabase database;

	protected DBManager() {
		MongoClientURI connectionString = new MongoClientURI("mongodb://admin:admin@ds011311.mlab.com:11311/feedemall");
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase("feedemall");
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}
	
}
