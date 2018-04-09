package basilspizza;

public class Table {
	
	private String tableId;
	private String assignedStaff;
	private String specialRequirements;
	private String orderId;
	
	public Table(String tableId, String assignedStaff, String specialRequirements, String orderId) {
		this.tableId = tableId;
		this.assignedStaff = assignedStaff;
		this.specialRequirements = specialRequirements;
		this.orderId = orderId;
	}
	
	public String getTableId() {
		return tableId;
	}
	
	public String getAssignedStaff() {
		return assignedStaff;
	}
	
	public String getSpecialRequirements() {
		return specialRequirements;
	}
	
	public String getOrderId() {
		return orderId;
	}

}
