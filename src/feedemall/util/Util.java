package feedemall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;


public class Util {

	static Gson gson = new Gson();
	
	public static String toJson(Object o) {
		return gson.toJson(o);
	}
	
	public static Class<?> fromJson(String json, Class<?> toClass) {
		return (Class<?>) gson.fromJson(json, toClass);
	}
	
	@SuppressWarnings("deprecation")
	public static void sendSMS(String phone, String message) throws ClientProtocolException, IOException{
		
		StringBuilder uri = new StringBuilder();
		uri.append("https://api.tropo.com/1.0/sessions?action=create&token=7a594d446b416c447a6443447758544e73434f6b586d4e4a7652506d61444b736549715855786e5641674e63&phoneNumber=");
		uri.append(phone);
		uri.append("&msg=");
		uri.append(URLEncoder.encode(message));
			
		String url = uri.toString();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = client.execute(request);
		
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + 
                       response.getStatusLine().getStatusCode());
		
		//System.out.println(uri);
		
		//Unirest.get(uri.toString());
		
	}
	
	public static String getFairEstimate(String startLat, String startLong, String endLat, String endLong) throws ClientProtocolException, IOException, JSONException{
		
		String endResponse = "";
		StringBuilder uri = new StringBuilder();
		uri.append("https://api.uber.com/v1/estimates/price?server_token=3z1RatO5tPNN8TvJ28qV6Im0VSW4ehh4VG3FRYjA&start_latitude=");
		uri.append(startLat);
		uri.append("&start_longitude=");
		uri.append(startLong);
		uri.append("&end_latitude=");
		uri.append(endLat);
		uri.append("&end_longitude=");
		uri.append(endLong);
		
		String url = uri.toString();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
	
//		System.out.println(result.toString());
		
		JSONObject obj = new JSONObject(result.toString());
		JSONArray arr = obj.getJSONArray("prices");
		
		for (int i = 0; i < arr.length(); i++)
		{
			String type = arr.getJSONObject(i).getString("display_name");
			String estimate = arr.getJSONObject(i).getString("estimate");
			if(type.equals("uberX")){
				endResponse = estimate;
			}
		}
		
		
		return endResponse;
	}

	public static String getLat(String address) throws ClientProtocolException, IOException, JSONException {
		// TODO Auto-generated method stub
		
		StringBuilder uri = new StringBuilder();
		uri.append("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyCxLDQyi8iKvpbUdbKetIeGASXu6iUBLPA&address=");
		uri.append(URLEncoder.encode(address));

		String url = uri.toString();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
	
//		System.out.println(result.toString());
		
		JSONObject obj = new JSONObject(result.toString());
		JSONArray arr = obj.getJSONArray("results");
		
		JSONObject obj1 = arr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
		String lat = obj1.getString("lat");
		System.out.println("Latitude : " + lat);
		return lat;
	}

	public static String getLong(String address) throws JSONException, ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		
		StringBuilder uri = new StringBuilder();
		uri.append("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyCxLDQyi8iKvpbUdbKetIeGASXu6iUBLPA&address=");
		uri.append(URLEncoder.encode(address));

		String url = uri.toString();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
	
//		System.out.println(result.toString());
		
		JSONObject obj = new JSONObject(result.toString());
		JSONArray arr = obj.getJSONArray("results");
		
		JSONObject obj1 = arr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
		String long1 = obj1.getString("lng");
		System.out.println("Longitude : " + long1);
		return long1;
	}
	
}
