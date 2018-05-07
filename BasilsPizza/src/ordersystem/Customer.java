package ordersystem;


public class Customer {
	
	private int customerId;
	private String firstName, lastName, houseNumber, address, city, postcode, phoneNumber;
	
	// Add new customer constructor
	public Customer(String firstName, String lastName, String houseNumber, String address, String city, String postcode, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.houseNumber = houseNumber;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
		this.phoneNumber = phoneNumber;
	}
	
	// Select from database constructor
	public Customer(int customerId, String firstName, String lastName, String houseNumber, String address, String city, String postcode, String phoneNumber) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.houseNumber = houseNumber;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
		this.phoneNumber = phoneNumber;
	}
	
	public boolean validateFirstName() {
		
		String pattern = "\\w{1,20}";
		if (!firstName.matches(pattern)) {
			return false;
		}
		
		return true;
	}
	
	public boolean validateLastName() {
		String pattern = "\\w{1,20}";
		if (!lastName.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public boolean validateHouseNumber() {
		String pattern = "\\d{1,5}";
		if (!houseNumber.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public boolean validateAddress() {
		String pattern = "[\\w\\s]+";
		if (!address.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public boolean validateCity() {
		String pattern = "\\w{1,20}";
		if (!city.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public boolean validatePostcode() {
		String pattern = "\\w{6,8}";
		if (!postcode.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public boolean validatePhoneNumber() {
		String pattern = "\\d{11}";
		if (!phoneNumber.matches(pattern)) {
			return false;
		}
		return true;
	}
	
	public void addCustomerToDatabase() throws Exception {
		Database.insertCustomer(firstName, lastName, houseNumber, address, city, postcode, phoneNumber);
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public String getCustomerFirstName() {
		return firstName;
	}

	public void setCustomerFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCustomerLastName() {
		return lastName;
	}

	public void setCustomerLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerHouseNumber() {
		return houseNumber;
	}

	public void setCustomerHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCustomerAddress() {
		return address;
	}

	public void setCustomerAddress(String address) {
		this.address = address;
	}

	public String getCustomerCity() {
		return city;
	}

	public void setCustomerCity(String city) {
		this.city = city;
	}

	public String getCustomerPostcode() {
		return postcode;
	}

	public void setCustomerPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCustomerPhoneNumber() {
		return phoneNumber;
	}

	public void setCustomerPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}
