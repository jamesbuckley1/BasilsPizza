import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	String distanceMiles;
	String duration;
	int zoomLevel = 13; //?
	Thread directionsThread;
	Thread mapImageThread;
	//CountDownLatch latch;
	

	public CustomerMap(int  zoomLevel) {

	}

	public CustomerMap(String houseNumber, String address, String city) {
		this.houseNumber = houseNumber;
		this.address = address.replace(" ", "+");
		this.city = city.replace(" ", "+");
		
		//latch = new CountDownLatch(1);

		getDirectionsData();
		//getStaticMapImage();

	}

	public void setZoom() {
		//zoomLevel
	}
	


	private void getDirectionsData() {
		directionsThread = new Thread() {
			public void run() {
				try {
					
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
						//polyline = polyline.replace("\\", "\\\\"); // This was fun figuring out
						System.out.println("POLYLINE " + polyline);
						System.out.println("DISTANCE " + distanceKm);
						System.out.println("DURATION" + duration);
						System.out.println("DURATION MILES " + convertKmToMiles(distanceKm));
					}
					
					//latch.await();
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
			

		};
		
		directionsThread.start();
		
		
			

	}
	
	private String convertKmToMiles(String distanceKm) {
		String processedDistanceKm = distanceKm.replace(" km", "");
		Double miles = Double.parseDouble(processedDistanceKm) * 0.621;
		processedDistanceKm = miles.toString() + " miles";
		return processedDistanceKm;
	}

	
	public JPanel getStaticMapImage() {
		
		
		JPanel panelMap = new JPanel();
		
			mapImageThread = new Thread() {
				public void run() {
					try {
						directionsThread.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
					String mapImgUrl = "https://maps.googleapis.com/maps/api/staticmap?size=300x300&path=enc:";
					String imageFileName = houseNumber + " " + address + ".jpg";
					String key = "&key=AIzaSyBn2qYJcHoNCgNQZv1mcycnUo06sJDZPBs";
					
					StringBuilder sb = new StringBuilder();
					sb.append(mapImgUrl);
					sb.append(polyline);
					sb.append(key);
					URL url = new URL(mapImgUrl + polyline + key);
					System.out.println("UUURRRRLL" + url.toString());
					InputStream is = url.openStream();
					OutputStream os = new FileOutputStream(imageFileName);
					
					byte[] b = new byte[2048];
					int length;
					
					while ((length = is.read(b)) != -1) {
						os.write(b, 0, length);
					}
					
					is.close();
					os.close();
					
					ImageIcon imgIcon = new ImageIcon((new ImageIcon(imageFileName))
							.getImage().getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH));
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JLabel labelMap = new JLabel();
							labelMap.setIcon(imgIcon);
							panelMap.add(labelMap);
						}
					});
					System.out.println("URL");
					System.out.println(mapImgUrl);
					System.out.println("STRINGBUILDER");
					System.out.println(sb);
					
					//latch.countDown();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}
				
				
			};
			

			mapImageThread.start();

		

		return panelMap;
	

	}



	
}
