import java.util.ArrayList;

public class AddCustomer {
	
	String firstName;
	String lastName;
	String houseNumber;
	String address;
	String city;
	String postcode;
	String phoneNumber;
	
	public AddCustomer(ArrayList textFieldValues) {
		this.firstName = textFieldValues.get(0).toString();
		this.lastName = textFieldValues.get(1).toString();
		this.houseNumber = textFieldValues.get(2).toString();
		this.address = textFieldValues.get(3).toString();
		this.city = textFieldValues.get(4).toString();
		this.postcode = textFieldValues.get(5).toString();
		this.phoneNumber = textFieldValues.get(6).toString();
	}
	
	public void validate() throws Exception {
		addCustomer();
	}
	
	public void addCustomer() throws Exception {
		Database.insertCustomer(firstName, lastName, houseNumber, address, city, postcode, phoneNumber);
	}

}
