package ordersystem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddMenuItemDialogGUI {
	
	private JFrame frame;
	private JTextField textFieldItemName, textFieldItemPrice;
	private JComboBox<String> comboboxItemType;
	
	public AddMenuItemDialogGUI() {
		initGUI();
	}
	
	public void initGUI() {
		JDialog dialogAddMenuItem = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		JPanel panelForm = new JPanel(new GridBagLayout());
		JPanel panelButtons = new JPanel(new GridBagLayout());
		
		textFieldItemName = new JTextField(20);
		textFieldItemPrice = new JTextField(5);
		
		comboboxItemType = new JComboBox<String>();
		comboboxItemType.addItem("Main");
		comboboxItemType.addItem("Sides");
		comboboxItemType.addItem("Drinks");
		comboboxItemType.addItem("Desserts");
		
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelForm.add(new JLabel("Item Type: "), gbc);
		
		gbc.gridy++;
		panelForm.add(new JLabel("Item Name: "), gbc);
		
		gbc.gridy++;
		
		panelForm.add(new JLabel("Item Price: "), gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		//gbc.anchor = GridBagConstraints.LINE_START;
		panelForm.add(comboboxItemType, gbc);
		
		gbc.gridy++;
		panelForm.add(textFieldItemName, gbc);
		
		gbc.gridy++;
		panelForm.add(textFieldItemPrice, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelForm.add(new JLabel(), gbc);
		
		GridBagConstraints gbcBtn = new GridBagConstraints();
		
		
		///////// BUTTONS /////////
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogAddMenuItem.dispose();
			}
		});
		
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemType = comboboxItemType.getSelectedItem().toString();
				String itemName = textFieldItemName.getText();
				double itemPrice = Double.parseDouble(textFieldItemPrice.getText());
				
				Database.insertMenuItem(itemName, itemType, itemPrice);
				

				NewOrderGUI.populateMainMenuTable();
				NewOrderGUI.populateSidesMenuTable();
				NewOrderGUI.populateDrinksMenuTable();
				NewOrderGUI.populateDessertsMenuTable();
				
				dialogAddMenuItem.dispose();
			}
		});
		
		gbcBtn.gridx = 20;
		gbcBtn.gridy = 0;
		gbcBtn.weightx = 1.0;
		gbcBtn.weighty = 1.0;
		panelButtons.add(new JLabel(), gbcBtn);
		
		gbcBtn.gridx++;
		gbcBtn.weightx = 0;
		gbcBtn.weighty = 0;
		panelButtons.add(buttonCancel, gbcBtn);
		
		gbcBtn.gridx++;
		panelButtons.add(buttonOK, gbcBtn);
		
		panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		dialogAddMenuItem.setTitle("Add Menu Item");
		
		panelMain.add(panelForm, BorderLayout.CENTER);
		panelMain.add(panelButtons, BorderLayout.SOUTH);
		
		dialogAddMenuItem.add(panelMain);
		
		dialogAddMenuItem.setModal(true); 
		dialogAddMenuItem.pack();
		dialogAddMenuItem.setLocationRelativeTo(frame);
		dialogAddMenuItem.setVisible(true);
		
	}
	
	

}
