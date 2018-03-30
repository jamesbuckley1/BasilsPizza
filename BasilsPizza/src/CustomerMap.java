import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.fabric.xmlrpc.base.Data;





// Talk about the Bing maps API used for the static image and the process I used.

public class CustomerMap {

	String houseNumber;
	String address;
	String city;
	String polyline;
	String distanceKm;
	String distanceMiles;
	String duration;
	Thread directionsThread;
	Thread mapImageThread;

	private boolean directionsDataDownloaded = false;



	public CustomerMap() {

	}
	// Add customer constructor
	public CustomerMap(Customer c) {
		this.houseNumber = c.getHouseNumber();
		this.address = c.getAddress().replace(" ", "+");
		this.city = c.getCity().replace(" ", "+");

	}
	// Constructor for customer info dialog 
	public CustomerMap(String houseNumber, String address, String city) {
		this.houseNumber = houseNumber;
		this.address = address.replace(" ", "+");
		this.city = city;
	}



	public void getDirectionsData() {

		//directionsThread = new Thread() {
		//public void run() {

		try {
			System.out.println("Downloading directions data...");
			URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Bournemouth+University&destination=" + houseNumber + "+" + address + "+" + city + "&key=AIzaSyBn2qYJcHoNCgNQZv1mcycnUo06sJDZPBs");
			System.out.println("DIRECTIONS URL");
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();

			if (responseCode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responseCode);
			} else {


				Scanner scan = new Scanner(url.openStream());
				StringBuilder jsonIn = new StringBuilder();
				while (scan.hasNextLine()) {
					jsonIn.append(scan.nextLine());
					//System.out.println("");

				}
				scan.close();

				System.out.println(jsonIn.toString());

				JSONParser parser = new JSONParser();
				JSONObject objRoot = (JSONObject) parser.parse(jsonIn.toString());
				JSONArray routesArray = (JSONArray) objRoot.get("routes");


				for (int i = 0; i < routesArray.size(); i ++) {



					JSONObject objRoutes = (JSONObject) routesArray.get(i);

					JSONArray legsArray = (JSONArray) objRoutes.get("legs");
					for (int j = 0; j < legsArray.size(); j ++) {
						JSONObject objLegs = (JSONObject) legsArray.get(j);
						JSONObject objDistance = (JSONObject) objLegs.get("distance");
						JSONObject objDuration = (JSONObject) objLegs.get("duration");
						distanceKm = (String) objDistance.get("text");
						duration = (String) objDuration.get("text");
					}


					JSONObject objPoints = (JSONObject) objRoutes.get("overview_polyline");
					polyline = (String) objPoints.get("points");
				}

				distanceMiles = convertKmToMiles(distanceKm);
				System.out.println("POLYLINE " + polyline);
				System.out.println("DISTANCE " + distanceKm);
				System.out.println("DURATION" + duration);
				System.out.println("DURATION MILES " + convertKmToMiles(distanceKm));
				directionsDataDownloaded = true;
			}


		}catch (Exception e) {
			e.printStackTrace();
		}


	}



	//};

	//directionsThread.start();

	//return directionsDataDownloaded;




	private String convertKmToMiles(String distanceKm) {
		String processedDistanceKm = distanceKm.replace(" km", "");
		Double miles = Double.parseDouble(processedDistanceKm) * 0.621;
		processedDistanceKm = miles.toString() + " miles";
		return processedDistanceKm;
	}


	public void getStaticMapImage() {


		//JPanel panelMap = new JPanel();


		/*
					try {
						directionsThread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		 */
		try {

			String mapImgUrl = "https://maps.googleapis.com/maps/api/staticmap?size=300x300&path=enc:";
			String formattedAddress = address.replace("+", " "); // Remove + character from address
			String imageFileName = houseNumber + " " + formattedAddress + " " + city + ".jpg";
			String key = "&key=AIzaSyBn2qYJcHoNCgNQZv1mcycnUo06sJDZPBs";

			StringBuilder sb = new StringBuilder();
			sb.append(mapImgUrl);
			sb.append(polyline);
			sb.append(key);

			boolean fileExists = new File("Customer Maps/", imageFileName).exists();
			if (fileExists) {
				System.out.println("Image file already exists.");
			} else {
				System.out.println("Downloading image...");
				URL url = new URL(mapImgUrl + polyline + key);
				BufferedImage img = ImageIO.read(url);
				File file = new File("Customer Maps/" + imageFileName);
				ImageIO.write(img, "jpg", file);
			}



			System.out.println("URL");
			System.out.println(mapImgUrl);
			System.out.println("STRINGBUILDER");
			System.out.println(sb);

			//latch.countDown();

		} catch (Exception e) {
			e.printStackTrace();
		}


	}







	public String getDistance() {
		return distanceMiles;
	}

	public String getDuration() {
		return duration;
	}


}
