package ordersystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeliveryOrder {
	
	private int orderId;
	private int customerId;
	private String dateTime;
	private int menuItemId;
	private String menuItemName;
	private int quantity;
	private double menuItemPrice;
	private double totalPrice;
	
	// Add new delivery order constructor.
	public DeliveryOrder(int customerId) {
		this.customerId = customerId;
	}
	
	// Select delivery orders constructor.
	public DeliveryOrder(int orderId, int customerId, String dateTime) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.dateTime = dateTime;
	}
	
	// Insert delivery orders constructor.
	public DeliveryOrder(int customerId, String dateTime) {
		this.customerId = customerId;
		this.dateTime = dateTime;
	}
	
	// Select delivery order item constructor.
	public DeliveryOrder(int orderId, int menuItemId, String menuItemName, int quantity, double menuItemPrice) {
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.quantity = quantity;
		this.menuItemPrice = menuItemPrice;
	}
	
	// Insert delivery order order item constructor.
	public DeliveryOrder(int customerId, int menuItemId, int quantity) {
		this.customerId = customerId;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}
	
	// Select customer delivery order items constructor.
	public DeliveryOrder(int customerId, int orderId, String orderTime, double totalPrice) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.dateTime = orderTime;
		this.totalPrice = totalPrice;
	}
	
	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date dateTime = new Date();
		String currentDateTime = dateFormat.format(dateTime);
		
		return currentDateTime;
	}
	
	public int getOrderId() {
		return orderId;
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
	
	public int getCustomerId() {
		return customerId;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	
	
	public void databaseInsertDeliveryOrderItem() {
		Database.insertDeliveryOrderItem(menuItemId, quantity);
	}
	

}
