import java.text.DecimalFormat;

public class Stock {

	private String item;
	private String price;
	private String quantity;

	public Stock(String item, String price, String quantity) {
		this.item = item;
		this.price= price;
		this.quantity = quantity;
	}
	
	public Stock(String item, String pricePounds, String pricePence, String quantity) {
		this.item = item.toUpperCase();
		this.price = pricePounds + "." + pricePenceCheckIfEmpty(pricePence);
		this.quantity = quantity;
	}
	
	private static String pricePenceCheckIfEmpty(String pricePence) {
		if (pricePence.equals("")) {
			pricePence = "00";
		}
		return pricePence;
	}

	public boolean validateItem() {
		String pattern = "[\\w\\s]+";
		
		if (!item.matches(pattern)) {
			System.out.println("Item does not match pattern");
			return false;
		}

		return true;
	}

	public boolean validatePrice() {
		
		String pattern = "\\d{1,2}\\.\\d{1,2}";
		
		if (!price.matches(pattern)) {
			System.out.println("Price does not match pattern");
			return false;
		}
		
		return true;

	}

	public boolean validateQuantity() {
		String pattern = "\\d{1,3}";

		if (!quantity.matches(pattern)) {
			System.out.println("Quantity does not match pattern");
			return false;
		}

		return true;
	}
	
	public void addStockToDatabase() throws Exception {
		Database.insertStock(item, price, quantity);
	}

	

	/* COME BACK TO THIS IT MIGHT BE FOR EDIT
	public void splitPrice() {
		String[] price = price.split("\\.").toString();
		pricePoundsAfterSplit = price[0];
		pricePenceAfterSplit = price[1];
	}
	 */

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPrice() {
		return price;
	}

	/* NOT WORKING
	public String getFormattedPrice() { // Needed for table to display 1.00 format instead of 1.0
		String pattern = "###.00";
		DecimalFormat df = new DecimalFormat(pattern);
		String formattedPrice = df.format(getPrice());

		return formattedPrice;
	}
	*/

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item: " + this.getItem() + ", Price: " + 
				this.getPrice() + ", Quantity: " + this.getQuantity();
	}
}
