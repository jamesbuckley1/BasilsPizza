import java.sql.*;
import java.util.ArrayList;


public class Database {

	private static Connection conn = null;

	private static ArrayList<Stock> stockArray;
	private static ArrayList<Customer> customersArray;
	
	// STOCK SQL STRINGS
	private final static String createStockTableSql = "CREATE TABLE IF NOT EXISTS stock (item TEXT PRIMARY KEY NOT NULL, price DOUBLE NOT NULL, quantity INT NOT NULL);";
	private final static String selectStockSql = "SELECT * FROM stock ORDER BY item ASC;";
	private final static String insertStockSql = "INSERT INTO stock (item, price, quantity) VALUES (?, ?, ?);";
	private final static String updateStockSql = "UPDATE stock SET item = ?, price = ?, quantity = ? WHERE item = ?";
	private final static String deleteStockSql = "DELETE FROM stock WHERE item = ?";
	private final static String dropStockTableSql = "DROP TABLE stock;";

	// CUSTOMERS SQL STRINGS
	private final static String createCustomersTableSql = "CREATE TABLE IF NOT EXISTS customers (first_name TEXT NOT NULL, last_name TEXT NOT NULL, house_number TEXT NOT NULL, address TEXT NOT NULL, city TEXT NOT NULL, postcode TEXT NOT NULL, phone_number TEXT NOT NULL, PRIMARY KEY(first_name, last_name, postcode));";
	private final static String selectCustomersSql = "SELECT * FROM customers ORDER BY last_name ASC;";
	private final static String insertCustomersSql = "INSERT INTO customers (first_name, last_name, house_number, address, city, postcode, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private final static String dropCustomersTableSql = "DROP TABLE customers;";

	private static void openDB() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:stock.db");
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Opened database successfully.");
	}

	public static void closeDB() {
		try {
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Closed database successfully.");
	}

	public static void initialise() {
		try {
			openDB();
			PreparedStatement stmt = conn.prepareStatement(createStockTableSql);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(createCustomersTableSql);
			stmt.executeUpdate();

			stmt.close();
			closeDB();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("SQLite database initialisation complete.");
	}

	public static void selectStock() {
		try {
			stockArray = new ArrayList<Stock>();
			openDB();

			PreparedStatement selectStock = conn.prepareStatement(selectStockSql);
			ResultSet rs = selectStock.executeQuery();

			while (rs.next()) {
				String item = rs.getString("item");
				String price = rs.getString("price");
				String quantity = rs.getString("quantity");

				Stock s = new Stock(item, price, quantity);
				stockArray.add(s);
			}

			rs.close();
			selectStock.close();
			closeDB();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("SELECT stock successful.");
	}

	public static void insertStock(String item, Double price, int quantity ) throws Exception{

		openDB();
		System.out.println("Inserting stock: " + item + " , " + price + " , " + quantity);

		PreparedStatement insert = conn.prepareStatement(insertStockSql);

		insert.setString(1, item);
		insert.setDouble(2, price);
		insert.setInt(3, quantity);

		insert.executeUpdate();
		insert.close();

		closeDB();

		System.out.println("INSERT stock successful.");
	}

	public static void deleteStock(String stockItem) {
		try {
			openDB();

			PreparedStatement delete = conn.prepareStatement(deleteStockSql);
			delete.setString(1, stockItem);
			delete.executeUpdate();
			delete.close();

			closeDB();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Delete successful.");
	}

	public static void updateStock(String currentItem, String newItem, Double newPrice, int newQuantity) {
		try {
			openDB();

			PreparedStatement update = conn.prepareStatement(updateStockSql);

			update.setString(1, newItem);
			update.setDouble(2, newPrice);
			update.setInt(3, newQuantity);
			update.setString(4, currentItem);
			update.executeUpdate();

			update.close();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("UPDATED " + currentItem + " successfully.");
	}

	public static ArrayList<Stock> getStockArray() {
		return stockArray;
	}

	///////////////////////////CUSTOMERS

	public static void selectCustomers() {
		try {
			customersArray = new ArrayList<Customer>();
			openDB();

			PreparedStatement selectCustomers = conn.prepareStatement(selectCustomersSql);
			ResultSet rs = selectCustomers.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String houseNumber = rs.getString("house_number");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String postcode = rs.getString("postcode");
				String phoneNumber = rs.getString("phone_number");

				Customer cust = new Customer(firstName, lastName, houseNumber, address, city, postcode, phoneNumber);
				customersArray.add(cust);
			}

			rs.close();
			selectCustomers.close();
			closeDB();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("SELECT customers successful.");
	}

	public static void insertCustomer(String firstName, String lastName, String houseNumber, String address, String city, String postcode, String phoneNumber) throws Exception {
		openDB();
		//System.out.println("Inserting customer: " + item + " , " + price + " , " + quantity);

		PreparedStatement insert = conn.prepareStatement(insertCustomersSql);

		insert.setString(1, firstName);
		insert.setString(2, lastName);
		insert.setString(3, houseNumber);
		insert.setString(4, address);
		insert.setString(5, city);
		insert.setString(6, postcode);
		insert.setString(7, phoneNumber);

		insert.executeUpdate();
		insert.close();

		closeDB();

		System.out.println("INSERT customer successful.");
	}





	public static ArrayList<Customer> getCustomersArray() {
		return customersArray;
	}

	// DEBUG
	public static void dropStockTable() { 
		try {
			openDB();

			PreparedStatement dropStockTable = conn.prepareStatement(dropStockTableSql);
			dropStockTable.executeUpdate();
			dropStockTable.close();

			closeDB();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("STOCK table dropped successfully");
	}

	public static void dropCustomersTable() {
		try {
			openDB();

			PreparedStatement dropCustomersTable = conn.prepareStatement(dropCustomersTableSql);
			dropCustomersTable.executeUpdate();
			dropCustomersTable.close();

			closeDB();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("CUSTOMERS table dropped successfully");
	}

}