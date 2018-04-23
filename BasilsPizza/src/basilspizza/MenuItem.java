package basilspizza;

public class MenuItem {
	
	private int itemId;
	private String itemName;
	private String itemType;
	private double itemPrice;
	
	public MenuItem(int itemId, String itemName, String itemType, double itemPrice) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemPrice = itemPrice;
	}
	
	public int getMenuItemId() {
		return itemId;
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
