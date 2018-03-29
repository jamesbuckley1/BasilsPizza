import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomerInfoDialogGUI {
	
	private String firstName;
	private String lastName;
	private String houseNumber;
	private String address;
	private String city;
	private String postcode;
	private String phoneNumber;
	
	public CustomerInfoDialogGUI(ArrayList<String> selectedCellValues) {
		this.firstName = selectedCellValues.get(0);
		this.lastName = selectedCellValues.get(1);
		this.houseNumber = selectedCellValues.get(2);
		this.address = selectedCellValues.get(3);
		this.city = selectedCellValues.get(4);
		this.postcode = selectedCellValues.get(5);
		this.phoneNumber = selectedCellValues.get(6);
		
		initDialog();
	}
	
	private void initDialog() {
		JDialog dialog = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		JPanel panelMainGrid = new JPanel(new GridLayout(1,2));
		
		
		panelMainGrid.add(generateMap());
		panelMainGrid.add(customerInfo());
		
		
		panelMain.add(panelMainGrid);
		
		dialog.add(panelMain);
		dialog.setSize(300, 180);
		dialog.setModal(true); // Always on top
		//dialog.setLocationRelativeTo(frame); // Open dialog in middle of frame
		dialog.setVisible(true);
	}
	
	private JPanel generateMap() {
		JPanel panelMap = new JPanel(new BorderLayout());
		
		//panelMap.add(new JLabel("TEST"), BorderLayout.CENTER);
		
		CustomerMap cm = new CustomerMap(houseNumber, address, city);
		panelMap.add(cm.getStaticMapImage(), BorderLayout.CENTER);
		
		return panelMap;
		
	}
	
	private JPanel customerInfo() {
		JPanel panelInfo = new JPanel(new BorderLayout());
		
		panelInfo.add(new JLabel("TEST"), BorderLayout.CENTER);
		
		return panelInfo;
	}

}
