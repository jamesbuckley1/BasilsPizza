import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.*;

public class AddStock {
	
	private JTextField itemField, pricePoundsField, pricePenceField, quantityField;
	private JPanel mainPanel, formPanel, buttonPanel;
	private JDialog dialog;
	private JLabel lblItem, lblPrice, lblQuantity, lblDot;
	private JButton cancelBtn, okBtn;
	
	private boolean pricePoundsPlaceholderText = false;
	private boolean pricePencePlaceholderText = false;
	private boolean quantityPlaceholderText = false;
	
	private String inputItem = "";
	private double inputPrice;
	private int inputQuantity;
	

	public AddStock(JFrame frame) {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		formPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout());
        dialog = new JDialog();

        GroupLayout layout = new GroupLayout(formPanel);
        layout.setAutoCreateGaps(true);
        
        formPanel.setLayout(layout);

        lblItem = new JLabel(" Item:");
        lblPrice = new JLabel(" Price:");
        lblQuantity = new JLabel(" Quantity:");
        lblDot = new JLabel(".");

        itemField = new JTextField();
        
        // FOCUS LISTENER ON TEXT FIELDS TO REMOVE PLACEHOLDER TEXT ON CLICK
        pricePoundsField = new JTextField("0");
        pricePoundsField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (pricePoundsPlaceholderText == false) {
					pricePoundsPlaceholderText = true;
					pricePoundsField.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus lost");
			}
        	
        });
        pricePenceField = new JTextField("00");
        pricePenceField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (pricePencePlaceholderText == false) {
					pricePencePlaceholderText = true;
					pricePenceField.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus lost");
			}
        	
        });
        
        quantityField = new JTextField("0");
        quantityField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				
				if (quantityPlaceholderText == false) {
					quantityPlaceholderText = true;
					quantityField.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus lost");
			}
        	
        });
        
        // GROUP LAYOUT 
        layout.setHorizontalGroup(layout
        		.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(lblItem)
        				.addComponent(itemField))
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(lblPrice)
        				.addComponent(pricePoundsField)
        				.addComponent(lblDot)
        				.addComponent(pricePenceField))
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(lblQuantity)
        				.addComponent(quantityField)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                		.addComponent(lblItem)
                		.addComponent(itemField))
                	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                		.addComponent(lblPrice)
                		.addComponent(pricePoundsField)
                		.addComponent(lblDot)
                		.addComponent(pricePenceField))
                	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                		.addComponent(lblQuantity)
                		.addComponent(quantityField)));
		
        // BUTTONS
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent event) {
        			dialog.dispose();
        		}
        });
        
        okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent event) {
        			validate();
        		}
        });
        
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(okBtn);
        
        
        
    
        
        buttonPanel.add(cancelBtn);
        buttonPanel.add(okBtn);
        
        
       
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        
        dialog.add(mainPanel);
        dialog.setSize(300, 180);
        dialog.setModal(true); // Always on top
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
	
	}
	
	private void validate() {
		String item = itemField.getText();
		String pricePounds = pricePoundsField.getText();
		String pricePence = pricePenceField.getText();
		String quantity = quantityField.getText();
		
		String invalidEntryItem = "Invalid Entry - Item. Please enter letters and numbers only.\n";
		String invalidEntryItemEmpty = "Item field cannot be empty.\n";
		String invalidEntryPricePounds = "Invalid Entry - Price (pounds). Please enter numbers only.\n";
		String invalidEntryPricePoundsEmpty = "Price (pounds) cannot be empty.\n";
		String invalidEntryPricePoundsTooLong = "Price (pounds) cannot be more than 99.";
		String invalidEntryPricePence = "Invalid Entry - Price (pence). Please enter numbers only.\n";
		String invalidEntryPricePenceEmpty = "Price (pence) cannot be empty.\n";
		String invalidEntryQuantity = "Invalid Entry - Quantity. Please enter numbers only.\n";
		String invalidEntryQuantityEmpty = "Quantity field cannot be empty.\n";
		
		boolean error = false;
		
		String errorStr = "";
		
		// ITEM FIELD VALIDATION
		
		item = item.toUpperCase();
		
		for (int i = 0; i < item.length(); i ++) {
			if (!Character.isLetterOrDigit(item.charAt(i))) {
				error = true;
				errorStr += invalidEntryItem;
				break;
			}
		}
		
		if (item.isEmpty()) {
			error = true;
			errorStr += invalidEntryItemEmpty;
			System.out.println(invalidEntryItemEmpty);
		}
		
		// PRICE(POUNDS) VALIDATION
		
		// IGNORE DOT IN CASE USER ENTERS IT
		pricePence = pricePence.replace(".", "");
		
		for (int i = 0; i < pricePounds.length(); i ++) {
			if (!Character.isDigit(pricePounds.charAt(i))) {
				error = true;
				errorStr += invalidEntryPricePounds;
				break;
			}
		}
		
		if (pricePounds.isEmpty()) {
			error = true;
			errorStr += invalidEntryPricePoundsEmpty;
		}
		
		if (pricePounds.length() > 2) { // Is this needed?
			error = true;
			errorStr += invalidEntryPricePoundsTooLong;
		}
		
		// PRICE(PENCE) VALIDATION
		
		// IGNORE DOT IN CASE USER ENTERS IT
		pricePence = pricePence.replace(".", "");
		
				
		for (int i = 0; i < pricePence.length(); i ++) {
			if (!Character.isDigit(pricePence.charAt(i))) {
				error = true;
				errorStr += invalidEntryPricePence;
				break;
			}
		}
		
		if (pricePence.isEmpty()) {
			error = true;
			errorStr += invalidEntryPricePenceEmpty;
		}
		
		
		
		// QUANTITY VALIDATION
		for (int i = 0; i < quantity.length(); i ++) {
			if (!Character.isDigit(quantity.charAt(i))) {
				error = true;
				errorStr += invalidEntryQuantity;
				break;
			}
		}
		
		if (quantity.isEmpty()) {
			error = true;
			errorStr += invalidEntryQuantityEmpty;
		}
		
		if (error) {
			JOptionPane.showMessageDialog(dialog, errorStr, 
					"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			addStock(item, pricePounds, pricePence, quantity);
			dialog.dispose();
		}
	}
	
	private void addStock(String item, String pricePounds, String pricePence, String quantity) {
		inputItem = item;
		inputPrice = Double.parseDouble(pricePounds + "." + pricePence);
		inputQuantity = Integer.parseInt(quantity);
		
		//DecimalFormat df = new DecimalFormat("#.00");
		//validatedPrice = df.format(validatedPrice);
		
		//Stock s = new Stock(inputItem, inputPrice, inputQuantity);
		Database.insertStock(inputItem, inputPrice, inputQuantity);
		
	}
	
	public String getItem() {
		return inputItem;
	}
	
	public double getPrice() {
		return inputPrice;
	}
	
	public int getQuantity() {
		return inputQuantity;
	}

}
