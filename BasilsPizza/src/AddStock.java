import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.text.NumberFormat;

import javax.swing.*;

import org.sqlite.SQLiteException;

public class AddStock {

	
	private boolean itemValidated = false; //?

	private String inputItem = "";
	private double inputPrice;
	private int inputQuantity;

	//VARIABLES FOR ITEM EDIT/UPDATE
	private boolean editFlag = false;

	private String currentItem = "";
	private String currentPrice = "";
	private String currentQuantity = "";
	private String pricePoundsAfterSplit;
	private String pricePenceAfterSplit;

	public AddStock() {

		//frame = (JFrame)SwingUtilities.getRoot(mainPanel);
		//buildGUI();



	}
	// CONSTRUCTOR FOR UPDATE/EDIT ITEM
	public AddStock(String cellDataItem, String cellDataPrice, String cellDataQuantity) {
		editFlag = true;
		currentItem = cellDataItem;
		currentPrice = cellDataPrice;
		currentQuantity = cellDataQuantity;

		//buildGUI();
		//editCheck();

	}

	

	public void splitPrice() {

		String[] price = currentPrice.split("\\.");
		pricePoundsAfterSplit = price[0];
		pricePenceAfterSplit = price[1];


	}
	/*
	public void validate() throws Exception  {
		AddStock
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

		System.out.println("VALIDATE - ITEM:" + item);

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
			itemValidated = true;
			//addStock(item, pricePounds, pricePence, quantity);
			dialog.dispose();
		}
	}
*/
	public void addStock(String item, String pricePounds, String pricePence, String quantity) throws Exception {

		if (itemValidated) {

			inputItem = item;
			inputPrice = Double.parseDouble(pricePounds + "." + pricePence);
			/*
		String pattern = "###.00";
		DecimalFormat df = new DecimalFormat(pattern);
		String formattedPrice = df.format(inputPrice);
			 */
			//System.out.println("FORMATTED PRICE: " + formattedPrice);
			//inputPrice = Double.parseDouble(formattedPrice);

			inputQuantity = Integer.parseInt(quantity);

			if (editFlag) {
				Database.updateStock(currentItem, inputItem, inputPrice, inputQuantity);
			} else {
				//
			}

		} else {
			System.out.println("Item has not been validated");
		}


	}

	/*
	public void editStock(String item, String pricePounds, String pricePence, String quantity) {
		inputItem = item;
		inputPrice = Double.parseDouble(pricePounds + "." + pricePence);
		inputQuantity = Integer.parseInt(quantity);

		Database.editStock(inputItem, inputPrice, inputQuantity)
	}
	 */

	public String getItem() {
		return inputItem;
	}

	public double getPrice() {
		return inputPrice;
	}

	public int getQuantity() {
		return inputQuantity;
	}
	
	// NEEDED?
	public boolean getEditFlag() {
		return editFlag;
	}

}
