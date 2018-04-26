package basilspizza;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CustomerInfoDialogGUI {
	private JFrame frame;
	private JDialog dialog;
	private Thread getMapInfoThread;
	private CustomerMap cm;
	private String firstName, lastName, houseNumber, address, city, postcode, phoneNumber, distance, duration;
	private JTextField textFieldDistance, textFieldDuration;
	private JLabel labelDeliveryZone;

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
		textFieldDistance.setEditable(false);

		textFieldDuration = new JTextField("Downloading...", 20);
		textFieldDuration.setEditable(false);

		getMapInfo();
		showMapInfoWhenDownloaded();
		initDialog();
	}

	// Sets up dialog and main panels.
	private void initDialog() {
		dialog = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		JPanel panelMainGrid = new JPanel(new GridLayout(1,2));

		panelMainGrid.add(setupMap());
		panelMainGrid.add(customerInfo());

		panelMain.add(panelMainGrid);

		dialog.add(panelMain);
		dialog.setTitle("Customer Information");
		dialog.setModal(true); // Always on top.
		dialog.pack();
		dialog.setLocationRelativeTo(frame); // Open dialog in middle of main window.
		dialog.setVisible(true);
	}

	// Downloads map data from Google Maps API.
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

	// Loads map image onto GUI.
	private JPanel setupMap() {
		JPanel panelMap = new JPanel(new BorderLayout());
		JLabel labelMapStatus = new JLabel("Downloading map...");
		JLabel labelMap = new JLabel();
		labelMap.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		JPanel panelMapBorder = new JPanel(new BorderLayout()); // Panel for border around map.
		panelMapBorder.add(labelMap, BorderLayout.CENTER);
		panelMapBorder.setBorder(BorderFactory.createEmptyBorder(29,29,29,25));

		panelMap.add(panelMapBorder, BorderLayout.CENTER);
		panelMap.add(labelMapStatus, BorderLayout.SOUTH);


		String filePath = "maps/" + houseNumber + " " + address + " " + city + ".jpg";
		File file = new File(filePath);

		// If file already exists in project directory load it from there.
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
			/*
			 * Otherwise load the placeholder image while it downloads the map image from Google Static Maps API.
			 * The map should have been downloaded when a new customer is added however this will re-download
			 * the image in case it has been removed.
			 */
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
						getMapInfoThread.join(); // Wait for this thread to finish before starting this one.
						cm.getStaticMapImage(); 

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
	
	// Set up customer info GUI components.
	private JPanel customerInfo() {
		JPanel panelInfo = new JPanel(new BorderLayout()); // Main info panel.
		JPanel panelInfoGridBag = new JPanel(new GridBagLayout()); // Panel for the layout of info components.
		JPanel panelInfoButtons = new JPanel(new GridBagLayout()); // Panel for buttons.

		JTextField textFieldFirstName = new JTextField(firstName, 20);
		textFieldFirstName.setEditable(false);

		JTextField textFieldLastName = new JTextField(lastName, 20);
		textFieldLastName.setEditable(false);

		JTextField textFieldHouseNumber = new JTextField(houseNumber, 20);
		textFieldHouseNumber.setEditable(false);

		JTextField textFieldAddress = new JTextField(address, 20);
		textFieldAddress.setEditable(false);

		JTextField textFieldCity = new JTextField(city, 20);
		textFieldCity.setEditable(false);

		JTextField textFieldPostcode = new JTextField(postcode, 20);
		textFieldPostcode.setEditable(false);

		JTextField textFieldPhoneNumber = new JTextField(phoneNumber, 20);
		textFieldPhoneNumber.setEditable(false);

		labelDeliveryZone = new JLabel(); // Label for out of range warning.

		// Button for opening directions in web browser.
		JButton openDirectionsBtn = new JButton("Get Directions");
		openDirectionsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
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

		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelInfoGridBag.add(new JLabel(), gbc);

		// LABELS
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

		// TEXT FIELDS
		gbc.gridx++;
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

		// SPACING
		gbc.gridx = 0;
		gbc.gridy++;
		panelInfoGridBag.add(new JLabel(), gbc);

		gbc.gridx = 0;
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		gbc.gridy++;
		panelInfoGridBag.add(new JLabel(), gbc);

		// DELIVERY ZONE WARNING LABEL
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		panelInfoGridBag.add(labelDeliveryZone, gbc);

		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelInfoButtons.add(new JLabel(), gbc);

		// BUTTONS
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelInfoButtons.add(openDirectionsBtn, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelInfoButtons.add(okBtn, gbc);

		// BORDER
		TitledBorder border = new TitledBorder("Customer Details:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelInfo.setBorder(border);

		panelInfoGridBag.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		panelInfo.add(panelInfoButtons, BorderLayout.SOUTH);
		panelInfo.add(panelInfoGridBag, BorderLayout.CENTER);

		return panelInfo;
	}


	// Attempts to download map data (distance, duration) then updates GUI.
	private void showMapInfoWhenDownloaded() {
		Thread showMapInfoThread = new Thread() {
			public void run() {
				int sleepCount = 0;
				/* Checks distance data is downloaded.
				 * While distance variable is null (not downloaded), wait for 1 second and try again
				 * (maximum 10 seconds).
				 */
				while(distance == null) {
					sleepCount++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// When distance data is downloaded, check if it is outside delivery area (10 miles).
					if (!(distance == null)) {
						textFieldDistance.setText(distance);
						if (cm.outsideDeliveryZone()) {
							textFieldDistance.setForeground(Color.RED);
							labelDeliveryZone.setText("Warning: Outside delivery zone. Collection orders only.");
						}
						textFieldDuration.setText(duration);
						// If data is not downloaded notify the user.
					} else if (distance == null && sleepCount > 10) {
						System.out.println("Map data failed to download");
						JOptionPane.showMessageDialog(null, "Map data failed to download.\nPlease check customer address and/or internet connection.",
								"Error", JOptionPane.ERROR_MESSAGE);
						textFieldDistance.setText("Could not get data.");
						textFieldDuration.setText("Could not get data.");
						break;
					}
				}
			}
		};
		showMapInfoThread.start();
	}

	// Open directions in web browser.
	private void openDirections() {
		try {
			String formattedAddress = address.replace(" ", "+");
			Desktop.getDesktop().browse(new URL("https://www.google.co.uk/maps/dir/?api=1&origin=Bournemouth+University&destination=" + houseNumber + "+" + formattedAddress).toURI());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
