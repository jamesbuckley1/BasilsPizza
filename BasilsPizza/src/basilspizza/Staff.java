package basilspizza;

import java.sql.SQLException;
import java.util.ArrayList;

public class Staff {
	
	private String employeeNumber;
	private String firstName;
	private String lastName;
	private String jobTitle;
	
	public Staff(String employeeNumber, String firstName, String lastName, String jobTitle) {
		this.employeeNumber = employeeNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
	}
	
	public void addToDatabase() throws SQLException {
		Database.insertStaff(employeeNumber, firstName, lastName, jobTitle);
	}
	
	public String getEmployeeNumber() {
		return employeeNumber;
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

}
