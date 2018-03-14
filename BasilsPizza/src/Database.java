import java.sql.*;
import java.util.ArrayList;


public class Database {
	
	
	private static ArrayList<Stock> stockArray;
	/*
	public Database() {
		initialise();
		
	
	}
	*/
	public static void initialise() {
		Connection c = null;
		Statement stmt = null;
		stockArray = new ArrayList<Stock>();
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:stock.db");
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS STOCK " +  // PRIMARY KEY AUTOMATICALLY INCREMENTS
							"(ITEM TEXT PRIMARY KEY NOT NULL, " + 
							"PRICE REAL NOT NULL, " +
							"QUANTITY INT NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully. Initialisation complete.");
		
		
	}
	
	public static void selectStock() {
		Connection c = null;
		Statement stmt = null;
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:stock.db");
			c.setAutoCommit(false);
			
			System.out.println("Opened database successfully. Selecting STOCK.");
			
			stmt = c.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM STOCK;");
			
			
			while (rs.next()) {
				
				String item = rs.getString("item");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				
				Stock st = new Stock(item, price, quantity);
				stockArray.add(st);
				
			
			}
			
			for (Stock stock : stockArray) {
				System.out.println(stock);
			}
			rs.close();
			stmt.close();
			c.close();
			
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("SELECT successful.");
		
		
		
		
	}
	
	public static void insertStock(String item, double price, int quantity ) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:stock.db");
			c.setAutoCommit(false);
			
			System.out.println("Opened database successfully. Inserting stock: " + item + " , " + price + " , " + quantity);
			
			stmt = c.createStatement();
			String sql = "INSERT INTO STOCK (ITEM,PRICE,QUANTITY) " +
						"VALUES ('" + item + "', " + price + ", " + quantity+ ");";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("INSERT successful.");
	}
	

	
	public static ArrayList<Stock> getStockArray() {
		return stockArray;
	}
	
	public void deleteStock(String stockItem) {
		
		Connection c = null;
		Statement stmt = null;
		
		System.out.println("Opened database successfully.");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:stock.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			String sql = "DELETE from STOCK where ITEM=" + stockItem + ";";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("STOCK table dropped successfully");
	}
		
	
	
	public void dropTable() {
		
		Connection c = null;
		Statement stmt = null;
		
		System.out.println("Opened database successfully.");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:stock.db");
			c.setAutoCommit(false);
			
			stmt = c.createStatement();
			String sql = "DROP TABLE STOCK;";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("STOCK table dropped successfully");
	}
	
}