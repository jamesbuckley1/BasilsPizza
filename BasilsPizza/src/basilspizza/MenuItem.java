package basilspizza;

public class MenuItem {
	
	private String itemName;
	private String itemType;
	private double itemPrice;
	
	public MenuItem(String itemName, String itemType, double itemPrice) {
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemPrice = itemPrice;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public double getItemPrice() {
		return itemPrice;
	}

}
