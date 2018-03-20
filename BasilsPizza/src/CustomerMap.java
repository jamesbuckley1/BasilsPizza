import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
			
			mapLabel.setSize(new Dimension(400, 260));
			
			
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
	
	
	
	//JPanel customerMap = new JPanel();
	
}
