import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CustomerInfoDialogGUI {

	private Thread mapInfoThread;
	private Thread t1;

	private String firstName;
	private String lastName;
	private String houseNumber;
	private String address;
	private String city;
	private String postcode;
	private String phoneNumber;
	private String distance;
	private String duration;

	public CustomerInfoDialogGUI(ArrayList<String> selectedCellValues) {
		this.firstName = selectedCellValues.get(0);
		this.lastName = selectedCellValues.get(1);
		this.houseNumber = selectedCellValues.get(2);
		this.address = selectedCellValues.get(3);
		this.city = selectedCellValues.get(4);
		this.postcode = selectedCellValues.get(5);
		this.phoneNumber = selectedCellValues.get(6);

		getMapInfo();
		

		initDialog();
		customerInfo();
	}

	private void initDialog() {
		JDialog dialog = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		JPanel panelMainGrid = new JPanel(new GridLayout(1,2));


		panelMainGrid.add(showMap());
		panelMainGrid.add(customerInfo());


		panelMain.add(panelMainGrid);

		dialog.add(panelMain);
		dialog.setSize(300, 180);
		dialog.setModal(true); // Always on top
		//dialog.setLocationRelativeTo(frame); // Open dialog in middle of frame
		dialog.setVisible(true);
	}

	private JPanel showMap() {
		JPanel panelMap = new JPanel(new BorderLayout());

		BufferedImage img = null;

		try {
			img = ImageIO.read(new File("Customer Maps/" + houseNumber + " " + address + " " + city + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon icon = new ImageIcon(img);


		panelMap.add(new JLabel(icon));

		//panelMap.add(new JLabel("TEST"), BorderLayout.CENTER);

		//CustomerMap cm = new CustomerMap(houseNumber, address, city);
		//panelMap.add(cm.getStaticMapImage(), BorderLayout.CENTER);

		return panelMap;

	}

	private JPanel customerInfo() {
		JPanel panelInfo = new JPanel(new BorderLayout());

		t1 = new Thread() {
			public void run() {
				try {
					mapInfoThread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("DISSSSSSATTATANCE: " + distance);
			}
		};
		t1.start();

		panelInfo.add(new JLabel("TEST"), BorderLayout.CENTER);
		panelInfo.add(new JLabel(distance), BorderLayout.NORTH);

		return panelInfo;
	}



	private void getMapInfo() {
		CustomerMap cm = new CustomerMap(houseNumber, address, city);
		mapInfoThread = new Thread() {
			public void run() {
				
				cm.getDirectionsData();
				notify();
			}
		};
		
		
			
		distance = cm.getDistance();
		duration = cm.getDuration();
		System.out.println("THREAD 2 DONE");
	
		//t2.start();
		mapInfoThread.start();
		
		
		System.out.println("DISTANCE AFTER: " + distance);



	}
	
	

}
