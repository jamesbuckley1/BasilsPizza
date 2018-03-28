import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;





// Talk about the Bing maps API used for the static image and the process I used.

public class CustomerMap {

	String houseNumber;
	String address;
	String city;
	String polyline;
	String distanceKm;
	String duration;
	int zoomLevel = 13; //?
	

	public CustomerMap(int  zoomLevel) {

	}

	public CustomerMap(String houseNumber, String address, String city) {
		this.houseNumber = houseNumber;
		this.address = address.replace(" ", "+");
		this.city = city.replace(" ", "+");

		getDirections();

	}

	public void setZoom() {
		//zoomLevel
	}
	


	public void getDirections() {
		try {
			
			URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Bournemouth+University&destination=" + houseNumber + "+" + address + "+" + city + "&key=AIzaSyBn2qYJcHoNCgNQZv1mcycnUo06sJDZPBs");
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
				
				System.out.println("POLYLINE " + polyline);
				System.out.println("DISTANCE " + distanceKm);
				System.out.println("DURATION" + duration);
				System.out.println("DURATION MILES " + convertKmToMiles(distanceKm));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			

	}
	
	public String convertKmToMiles(String distanceKm) {
		String processedDistanceKm = distanceKm.replace(" km", "");
		Double miles = Double.parseDouble(processedDistanceKm) * 0.621;
		processedDistanceKm = miles.toString() + " miles";
		return processedDistanceKm;
	}

	/*
	public JPanel getImage() {
		
		String mapImgUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + houseNumber + "+" + address + "," + city + "&zoom=" + zoomLevel + "&size=400x260&maptype=roadmap&markers=color:red|" + houseNumber + "+" + address + "+" + city + "&key=AIzaSyDrJF68y_uKuwryAurtDLbHvb4ZonqpDmM&format=jpeg";
		String imageFileName = "CustomerLocationImg.jpg";
		JPanel panelMap = new JPanel();
		try {
			URL url = new URL(mapImgUrl);
			System.out.println("housenum: " + houseNumber + " address: " + address + " city: " + city);
			System.out.println("IMGURL" + mapImgUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(imageFileName);
			byte[] b = new byte[2048];
			int length;

			while((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();

			JLabel mapLabel = new JLabel();

			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("CustomerLocationImg.jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			mapLabel.setSize(new Dimension(400, 240));


			Image resize = img.getScaledInstance(mapLabel.getWidth(), mapLabel.getHeight(),
					Image.SCALE_SMOOTH);

			ImageIcon imageIcon = new ImageIcon(resize);

			/*
			ImageIcon imageIcon = new ImageIcon((new ImageIcon("CustomerLocationImg.jpg"))
					.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
			 


			panelMap.add(new JLabel(imageIcon));



		} catch (Exception e) {
			e.printStackTrace();
		}

		return panelMap;
	

	}
*/


	
}
