package basilspizza;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Staff {
	
	private String staffId;
	private String firstName;
	private String lastName;
	private String jobTitle;
	private String clockInTime;
	private String clockOutTime;
	private Date dateTime;
	private String currentDateTime;
	
	// Add staff constructor.
	public Staff(String staffId, String firstName, String lastName, String jobTitle) {
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
	}
	
	// Clock in constructor. - Before adding to database
	public Staff(String staffId) {
		this.staffId = staffId;
	}
	
	// Select clocked in staff constructor
	public Staff(String staffId, String firstName, String lastName, String jobTitle, String clockInTime) {
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.clockInTime = clockInTime;
	}
	
	public Staff(String staffId, String firstName, String lastName, String jobTitle, String clockInTime, String clockOutTime) {
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.clockInTime = clockInTime;
		this.clockOutTime = clockOutTime;
	}
	
	public void addNewStaffToDatabase() throws SQLException {
		Database.insertStaff(staffId, firstName, lastName, jobTitle);
	}
	
	public void clockIn() {
		// Get system time
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateTime = new Date();
		currentDateTime = dateFormat.format(dateTime);
		Database.clockInStaff(staffId, currentDateTime);
		TablesGUI.populateComboboxStaff();
	}
	
	public void updateLastClockIn() {
		try {
			Database.updateStaffLastClockIn(staffId, currentDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clockOut() {
		try {
		Database.clockOutStaff(staffId);
		TablesGUI.populateComboboxStaff();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateLastClockOut() {
		try {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateTime = new Date();
		currentDateTime = dateFormat.format(dateTime);
		Database.updateStaffClockOutTime(staffId, currentDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getStaffId() {
		return staffId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	
	public String getClockInTime() {
		return clockInTime;
	}
	
	public String getClockOutTime() {
		return clockOutTime;
	}

}
