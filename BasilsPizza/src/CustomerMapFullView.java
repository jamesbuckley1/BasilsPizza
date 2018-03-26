import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JDialog;

// Explain why using Bing and not google - Google maps in web view has font issues for MacOS that
// won't be fixed until java 9. Find source.

// Mention the bug I ran into where you couldn't close and reopen the web view without getting
// a null pointer exception, and how you fixed it by adding the updateView method and used
// static variables.

import javax.swing.JFrame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CustomerMapFullView {
	private static JFXPanel jfxPanel;
	private static JDialog dialog;
	private static WebEngine engine;
	
	private static WebView wv;
	
	private boolean init = false;

	String houseNumber = "";
	String address = "";
	String city = "";
	String url = "";

	public CustomerMapFullView(String houseNumber, String address, String city) {
		this.houseNumber = houseNumber;
		this.address = processAddress(address);
		this.city = city;

		//System.out.println(houseNumber + this.address + city);

		//url = "https://www.google.com/maps/search/?api=1&query=destination=" + houseNumber + "+" + address + "+" + city;
		url = "http://www.bing.com/maps/default.aspx?rtp=adr.Bournemouth%20University~adr." + houseNumber + "%20" + address + "," + city;
		checkInit();

	}

	private String processAddress(String address) {
		String processedAddress = address.replace(" ", "%20");

		return processedAddress;
	}

	private void initGUI() {
		try {
			
			dialog = new JDialog();
			dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			dialog.setSize(800,600);
			
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dialog.setVisible(false);
					//stop();
				}
			});
			
			jfxPanel = new JFXPanel();
			dialog.add(jfxPanel);
			
			Platform.runLater(new Runnable() {
				public void run() {
					
					wv = new WebView();
					engine = wv.getEngine();
					jfxPanel.setScene(new Scene(wv));
					engine.load(url);
				}
			});
			dialog.setVisible(true);

			/*
			Platform.runLater(new Runnable() {
				public void run() {
					System.out.println("1");
					wv = new WebView();
					WebEngine engine = wv.getEngine();
					engine.load(url);
					jfxPanel.setScene(new Scene(wv, 800,600));
					dialog = new JDialog();
					dialog.add(new JScrollPane(jfxPanel));
					
					dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					dialog.setMinimumSize(new Dimension(800,600));
					dialog.pack();
					dialog.setVisible(true);
					
				}
			});
			
			 	*/
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkInit() {
		if (init) {
			updateView();
		} else {
			initGUI();
		}
	}
	
	public void updateView() {
		Platform.runLater(new Runnable() {
			public void run() {
				//jfxPanel.setScene(new Scene(wv));
				engine.load(url);
			}
		});
		
		dialog.setVisible(true);
		
	}
	
	public void stop() {
		Platform.exit();
	}

}
