package basilspizza;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	
	private int orderId; // Used only when selecting from database.
	private String dateTime; // Used only when selecting from database.
	private String tableName;
	private int menuItemId;
	private int quantity;
	private String menuItemName;
	double menuItemPrice;
	
	// Add new order constructor
	public Order(String tableName) {
		this.tableName = tableName;
	}
	
	// Add new order item constructor.
	public Order(String tableName, int menuItemId, int quantity) {
		this.tableName = tableName;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
		
	}
	
	// Select order from database constructor.
	public Order(int orderId, String tableName, String dateTime) {
		this.orderId = orderId;
		this.tableName = tableName;
		this.dateTime = dateTime;
	}
	
	// Select order item from database constructor.
	public Order(int orderId, int menuItemId, String menuItemName, double menuItemPrice) {
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.menuItemPrice = menuItemPrice;
	}
	
	public String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date dateTime = new Date();
		String currentDateTime = dateFormat.format(dateTime);
		
		return currentDateTime;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	
	
	public int getMenuItemId() {
		return menuItemId;
	}
	
	public String getMenuItemName() {
		return menuItemName;
	}
	
	public double getMenuItemPrice() {
		return menuItemPrice;
	}
	
	
	public void databaseInsertTableOrderItem() {
		Database.insertTableOrderItem(tableName, menuItemId, quantity);
	}
	


}
