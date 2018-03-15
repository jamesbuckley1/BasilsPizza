import java.sql.*;
import java.util.ArrayList;


public class Database {
	
	private static Connection conn = null;
	//private static Statement stmt = null;
	
	private static ArrayList<Stock> stockArray;
	
	private final static String createStockTableSql = "CREATE TABLE IF NOT EXISTS stock (item TEXT PRIMARY KEY NOT NULL, price REAL NOT NULL, quantity INT NOT NULL);";
	private final static String dropStockTableSql = "DROP TABLE stock;";
	private final static String selectStockSql = "SELECT * FROM stock;";
	private final static String insertStockSql = "INSERT INTO stock (item, price, quantity) VALUES (?, ?, ?);";
	private final static String updateStockSql = "UPDATE stock SET item = ?, price = ?, quantity = ? WHERE item = ?";
	private final static String deleteStockSql = "DELETE FROM stock WHERE item = ?";
	
	//String sql = "DELETE FROM STOCK WHERE ITEM = '" + stockItem + "';";
	
	
	public static void openDB() {
		try {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:stock.db");
		conn.setAutoCommit(false);
		//stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Opened database successfully.");
	}
	
	public static void closeDB() {
		try {
			//stmt.close(); // Does conn.close close this already?
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Closed database successfully.");
	}
	public static void initialise() {
		
		//stockArray = new ArrayList<Stock>();
		
		try {
			openDB();
			
			PreparedStatement createStockTable = conn.prepareStatement(createStockTableSql);
			createStockTable.executeUpdate();
			createStockTable.close();
			
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
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				
				Stock st = new Stock(item, price, quantity);
				stockArray.add(st);
				
			
			}
			/*
			for (Stock stock : stockArray) {
				System.out.println(stock);
			}
			*/
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
	
	public static void insertStock(String item, double price, int quantity ) throws Exception{
		
		
		
		//try {
			openDB();
			
			System.out.println("Inserting stock: " + item + " , " + price + " , " + quantity);
			
			PreparedStatement insert = conn.prepareStatement(insertStockSql);
				
			insert.setString(1, item);
			insert.setDouble(2, price);
			insert.setInt(3, quantity);
			
			//stmt = conn.createStatement();
			
			insert.executeUpdate();
			insert.close();
			
			closeDB();
			
			
			
		//} catch (Exception e) {
		//	System.err.println(e.getClass().getName() + ": " + e.getMessage());
		//	System.exit(0);
		//}
		System.out.println("INSERT stock successful.");
	}
	

	
	public static ArrayList<Stock> getStockArray() {
		return stockArray;
	}
	
	public static void deleteStock(String stockItem) {
		
		
		try {
			openDB();
			
			PreparedStatement deleteStock = conn.prepareStatement(deleteStockSql);
			deleteStock.setString(1, stockItem);
			
			deleteStock.executeUpdate();
			
			deleteStock.close();
			
			closeDB();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Deleted successfully.");
	}
	
	public static void updateStock(String currentItem, String newItem, double newPrice, int newQuantity) {
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
	
}