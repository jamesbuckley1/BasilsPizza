package basilspizza;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Customer {
	
	private String firstName;
	private String lastName;
	private String houseNumber;
	private String address;
	private String city;
	private String postcode;
	private String phoneNumber;
	
	public Customer(String firstName, String lastName, String houseNumber, String address, String city, String postcode, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.houseNumber = houseNumber;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
		this.phoneNumber = phoneNumber;
	}
	
	public boolean validateFirstName() {
		System.out.println("WORD TO BE VALIDATED: " + firstName);
		
		String pattern = "\\w{1,20}";
		if (!firstName.matches(pattern)) {
			return false;
		}
		
		return true;
		//addCustomer();
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
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}
