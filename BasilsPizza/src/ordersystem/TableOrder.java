package ordersystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableOrder {
	
	private int orderId; // Used only when selecting from database.
	private String dateTime; // Used only when selecting from database.
	private String tableName;
	private int menuItemId;
	private int quantity;
	private String menuItemName;
	private double menuItemPrice;
	
	// Add new table order constructor
	public TableOrder(String tableName) {
		this.tableName = tableName;
	}
	
	// Add new table order item constructor.
	public TableOrder(String tableName, int menuItemId, int quantity) {
		this.tableName = tableName;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
		
	}
	
	// Select table order from database constructor.
	public TableOrder(int orderId, String tableName, String dateTime) {
		this.orderId = orderId;
		this.tableName = tableName;
		this.dateTime = dateTime;
	}
	
	// Select table order item from database constructor.
	public TableOrder(int orderId, int menuItemId, String menuItemName, int quantity, double menuItemPrice) {
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.quantity = quantity;
		this.menuItemPrice = menuItemPrice;
	}
	
	// Select active table order items constructor.
	public TableOrder(int orderId, int menuItemId, String tableId, String menuItemName, int quantity, double menuItemPrice) {
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.tableName = tableId;
		this.menuItemName = menuItemName;
		this.quantity = quantity;
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getMenuItemPrice() {
		return menuItemPrice;
	}
	
	
	public void databaseInsertTableOrderItem() {
		Database.insertTableOrderItem(tableName, menuItemId, quantity);
	}
	


}
