import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CustomerInfoDialogGUI {

	private JFrame frame;
	private JDialog dialog;


	private Thread getMapInfoThread;
	private Thread customerInfoThread;

	private CustomerMap cm;

	private String firstName;
	private String lastName;
	private String houseNumber;
	private String address;
	private String city;
	private String postcode;
	private String phoneNumber;
	private String distance;
	private String duration;

	private JTextField textFieldDistance;
	private JTextField textFieldDuration;
	private JLabel labelDeliveryZone;

	private ImageIcon mapImgIcon;


	public CustomerInfoDialogGUI(JFrame frame, ArrayList<String> selectedCellValues) {
		this.firstName = selectedCellValues.get(0);
		this.lastName = selectedCellValues.get(1);
		this.houseNumber = selectedCellValues.get(2);
		this.address = selectedCellValues.get(3);
		this.city = selectedCellValues.get(4);
		this.postcode = selectedCellValues.get(5);
		this.phoneNumber = selectedCellValues.get(6);

		this.frame = frame;
		cm = new CustomerMap(houseNumber, address, city);

		textFieldDistance = new JTextField("Downloading...", 20);
		textFieldDuration = new JTextField("Downloading...", 20);
		
		textFieldDistance.setEditable(false);
		textFieldDuration.setEditable(false);

		getMapInfo();
		//customerInfo();

		showMapInfoWhenDownloaded();
		initDialog();




	}

	private void initDialog() {


		System.out.println("RUNNING INITTHREAD");
		dialog = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		JPanel panelMainGrid = new JPanel(new GridLayout(1,2));

		mapImgIcon = new ImageIcon();

		///LABEL FOR DISPLAYING MAP



		panelMainGrid.add(setupMap());
		panelMainGrid.add(customerInfo());


		panelMain.add(panelMainGrid);

		dialog.add(panelMain);
		//dialog.setSize(300, 180);
		dialog.setModal(true); // Always on top
		dialog.pack();
		dialog.setLocationRelativeTo(frame); // Open dialog in middle of frame

		dialog.setVisible(true);




	}
	

	private void getMapInfo() {

		getMapInfoThread = new Thread() {
			public void run() {

				cm.getDirectionsData();
				distance = cm.getDistance();
				duration = cm.getDuration();
			}
		};


		getMapInfoThread.start();




	}

	private JPanel setupMap() {
		JPanel panelMap = new JPanel(new BorderLayout());
		JLabel labelMapStatus = new JLabel("Downloading map...");
		JLabel labelMap = new JLabel();
		labelMap.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		JPanel panelMapBorder = new JPanel(new BorderLayout()); // Panel just for border around map
		panelMapBorder.add(labelMap, BorderLayout.CENTER);
		panelMapBorder.setBorder(BorderFactory.createEmptyBorder(23,23,23,23));
		
		panelMap.add(panelMapBorder, BorderLayout.CENTER);
		panelMap.add(labelMapStatus, BorderLayout.SOUTH);


		String filePath = "maps/" + houseNumber + " " + address + " " + city + ".jpg";
		File file = new File(filePath);

		if (file.isFile()) {
			try {
				BufferedImage img = null;
				img = ImageIO.read(new File(filePath));
				ImageIcon icon = new ImageIcon(img);
				labelMap.setIcon(icon);
				labelMapStatus.setText("");
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			try {
				BufferedImage img = null;
				img = ImageIO.read(new File("res/map_placeholder.png"));
				ImageIcon icon = new ImageIcon(img);
				labelMap.setIcon(icon);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Thread mapThread = new Thread() {
				public void run() {
					try {
						getMapInfoThread.join();
						cm.getStaticMapImage(); // Call again in case map image has been deleted

						BufferedImage img = null;
						img = ImageIO.read(new File(filePath));
						ImageIcon icon = new ImageIcon(img);
						labelMap.setIcon(icon);
						labelMapStatus.setText("");
					} catch (Exception e) {
						e.printStackTrace();
					}


				}
			};
			mapThread.start();
		}


		return panelMap;

	}

	private JPanel customerInfo() {
		System.out.println("RUNNING CUSTOMERINFO()");
		JPanel panelInfo = new JPanel(new BorderLayout());
		JPanel panelInfoGridBag = new JPanel(new GridBagLayout());

		JTextField textFieldFirstName = new JTextField(firstName, 20);
		JTextField textFieldLastName = new JTextField(lastName, 20);
		JTextField textFieldHouseNumber = new JTextField(houseNumber, 20);
		JTextField textFieldAddress = new JTextField(address, 20);
		JTextField textFieldCity = new JTextField(city, 20);
		JTextField textFieldPostcode = new JTextField(postcode, 20);
		JTextField textFieldPhoneNumber = new JTextField(phoneNumber, 20);
		
		textFieldFirstName.setEditable(false);
		textFieldLastName.setEditable(false);
		textFieldHouseNumber.setEditable(false);
		textFieldAddress.setEditable(false);
		textFieldCity.setEditable(false);
		textFieldPostcode.setEditable(false);
		textFieldPhoneNumber.setEditable(false);
		

		labelDeliveryZone = new JLabel();



		JButton openDirectionsBtn = new JButton("Get Directions");
		openDirectionsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("DIRECTIONS BUTTON PRESSED");
				//load browser
				Thread directionsThread = new Thread() {
					public void run() {
						openDirections();
					}
				};
				directionsThread.start();

			}
		});

		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				dialog.dispose();

			}
		});



		GridBagConstraints gbc = new GridBagConstraints();

		//Blank JLabel to push others to top
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelInfoGridBag.add(new JLabel(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelInfoGridBag.add(new JLabel("First name: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("Last name: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("House number: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("Address: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("City: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("Postcode: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("Phone number: "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("Distance (miles): "), gbc);

		gbc.gridy++;
		panelInfoGridBag.add(new JLabel("Duration (current traffic): "), gbc);

		gbc.gridx++; //1
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelInfoGridBag.add(textFieldFirstName, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldLastName, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldHouseNumber, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldAddress, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldCity, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldPostcode, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldPhoneNumber, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldDistance, gbc);

		gbc.gridy++;
		panelInfoGridBag.add(textFieldDuration, gbc);

		//Blank labels for spacing
		gbc.gridx = 0;
		gbc.gridy++;
		panelInfoGridBag.add(new JLabel(), gbc);

		gbc.gridx = 0;
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		gbc.gridy++;
		panelInfoGridBag.add(new JLabel(), gbc);

		//
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		panelInfoGridBag.add(labelDeliveryZone, gbc);
		/*
		// Blank
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		//gbc.gridwidth = 1;
		panelInfoGridBag.add(new JLabel(), gbc);
		 */

		///// BUTTONS

		JPanel panelInfoButtons = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		//gbc.gridwidth = 2;
		panelInfoButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		//gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelInfoButtons.add(openDirectionsBtn, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelInfoButtons.add(okBtn, gbc);


		TitledBorder border = new TitledBorder("Customer Details:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);

		panelInfo.setBorder(border);

		panelInfoGridBag.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		panelInfo.add(panelInfoButtons, BorderLayout.SOUTH);
		panelInfo.add(panelInfoGridBag, BorderLayout.CENTER);



		return panelInfo;

	}



	private void showMapInfoWhenDownloaded() {

		Thread showMapInfoThread = new Thread() {
			public void run() {
				System.out.println("RUNNING SHOWMAPINFO!");

				int sleepCount = 0;

				while(distance == null) {
					System.out.println("DISTANCE NULL");
					sleepCount++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!(distance == null)) {
						System.out.println("DISTANCE NOT NULL" + distance);
						textFieldDistance.setText(distance);
						if (cm.outsideDeliveryZone()) {
							textFieldDistance.setForeground(Color.RED);
							labelDeliveryZone.setText("Warning: Outside delivery zone. Collection orders only.");
						}
						textFieldDuration.setText(duration);
					} else if (distance == null && sleepCount > 10) {
						System.out.println("could not get...");
						textFieldDistance.setText("Could not get data.");
						textFieldDuration.setText("Could not get data.");
						break;
					}
				}
				//labelDistance.paintImmediately(labelDuration.getVisibleRect());

				//dialog.repaint();
			}
		};
		showMapInfoThread.start();



	}

	// OPEN DIRECTIONS IN INTERNET BROWSER
	private void openDirections() {
		try {
			System.out.println("OPENDIRECTIONS CALLED");
			String formattedAddress = address.replace(" ", "+");
			Desktop.getDesktop().browse(new URL("https://www.google.co.uk/maps/dir/?api=1&origin=Bournemouth+University&destination=" + houseNumber + "+" + formattedAddress).toURI());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}








}
