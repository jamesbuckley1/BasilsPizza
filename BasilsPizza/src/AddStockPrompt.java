import javax.swing.*;
public class AddStockPrompt { 
	String item;
	double price;
	int quantity;
	
	JPanel panelPrompt;
	JTextField itemField;
	JTextField priceField;
	JTextField quantityField;
			
	public AddStockPrompt() {
		itemField = new JTextField(15);
		priceField = new JTextField(5);
		quantityField = new JTextField(5);
		panelPrompt = new JPanel();
		
		panelPrompt.add(new JLabel("Item:"));
		panelPrompt.add(itemField);
		panelPrompt.add(new JLabel("Price:"));
		panelPrompt.add(priceField);
		panelPrompt.add(new JLabel("Quantity:"));
		panelPrompt.add(quantityField);
		
		addStockItemPrompt();
	}
	
	private void addStockItemPrompt() {
		int result = JOptionPane.showConfirmDialog(null, panelPrompt,
				"Add a New Stock Item", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				boolean check = true;
					while (check == true) {
						for (int i = 0; i < itemField.getText().length(); i ++) {
							System.out.println(itemField.getText().charAt(i));
							if (Character.isDigit(itemField.getText().charAt(i)) || 
									Character.isLetter(itemField.getText().charAt(i))) {
								
								check = false;
							} else {
								System.out.println("Invalid item entry");
							}
							
						}
					}
					
				
			item = itemField.getText();
			price = Double.parseDouble(priceField.getText());
			quantity = Integer.parseInt(quantityField.getText());
			//System.out.println("Quantity: " + quantityField.getText());
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}
	
	public String getItem() {
		return item;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	
}
