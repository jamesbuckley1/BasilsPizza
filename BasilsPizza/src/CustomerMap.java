import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// Talk about the Bing maps API used for the static image and the process I used.

public class CustomerMap {
	
	String houseNumber;
	String address;
	String city;
	int zoomLevel = 13;
	
	public CustomerMap(int  zoomLevel) {
		
	}
	
	public CustomerMap(String houseNumber, String address, String city) {
		this.houseNumber = houseNumber;
		this.address = address.replace(" ", "+");
		this.city = city.replace(" ", "+");
		
		getCoordinates();
	}
	
	public void setZoom() {
		//zoomLevel
	}
	
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
			*/
			
			
			panelMap.add(new JLabel(imageIcon));
			
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return panelMap;
		
		
	}
	

	private void getCoordinates() {
		String coordinates = "";
		String latitude = "";
		String longitude = "";
		
		try {
			URL url = new URL("http://dev.virtualearth.net/REST/v1/Locations?countryRegion=GB&locality=EPSOM&addressLine=71%20ASHLEY%20ROAD&key=AohJR4zT55zAon5RVlB_1WCX1XcTjYR06yeTKCn87jvads5MCU904rLvHoqBg9Hy");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responseCode = conn.getResponseCode();
			
			if (responseCode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responseCode);
			} else {
				
				String result = "";
				
				Scanner scan = new Scanner(url.openStream());
				while (scan.hasNext()) {
					result = scan.nextLine();
					System.out.println(result);
					
				}
				scan.close();
				
				JSONParser parser = new JSONParser();
				JSONObject jObj = (JSONObject)parser.parse(result);
				JSONArray jsonArray = (JSONArray)jObj.get("resourceSets");
				// Get resourcesSets data
				for (int i = 0; i < jsonArray.size(); i ++) {
					JSONObject jObjResourceSets = (JSONObject)jsonArray.get(i);
					JSONArray jsonResourcesArray = (JSONArray)jObjResourceSets.get("resources");
					
					for (int j = 0; j < jsonResourcesArray.size(); j ++) {
						JSONObject jObjResources = (JSONObject)jsonResourcesArray.get(j);
						JSONObject jObjPoint = (JSONObject)jObjResources.get("point");
						JSONArray jsonCoordinatesArray = (JSONArray)jObjPoint.get("coordinates");
						
						for (int k = 0; k < jsonCoordinatesArray.size(); k ++) {
							coordinates += jsonCoordinatesArray.get(k).toString() + ",";
							
						}
						
					}
				}
				
				
				System.out.println("COORDINATES BEFORE SPLIT " + coordinates);
				String[] coords = coordinates.split(",");
				latitude = coords[0];
				longitude = coords[1];
				
				//System.out.println("COORDS " + coords[0]);
				System.out.println("latitude " + latitude);
				System.out.println("logitude " + longitude);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	//JPanel customerMap = new JPanel();
	
}
