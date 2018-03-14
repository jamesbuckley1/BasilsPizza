import java.sql.*;
import java.util.ArrayList;


public class Database {
	
	private static Connection c = null;
	private static Statement stmt = null;
	
	private static ArrayList<Stock> stockArray;
	
	private final static String createStockTableSql = "CREATE TABLE IF NOT EXISTS STOCK (ITEM TEXT PRIMARY KEY NOT NULL, PRICE REAL NOT NULL, QUANTITY INT NOT NULL);";
	private final static String dropStockTableSql = "DROP TABLE STOCK;";
	private final static String selectStockSql = "SELECT * FROM STOCK;";
			
			
			

	/*
	public Database() {
		initialise();
		
	
	}
	*/
	
	public static void openDB() {
		try {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:stock.db");
		c.setAutoCommit(false);
		stmt = c.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Opened database successfully.");
	}
	
	public static void closeDB() {
		try {
			stmt.close(); // Does c.close close this already?
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Closed database successfully.");
	}
	public static void initialise() {
		
		//stockArray = new ArrayList<Stock>();
		
		try {
			openDB();
			
			
			stmt.executeUpdate(createStockTableSql);
			
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
			
			ResultSet rs = stmt.executeQuery(selectStockSql);
			
			
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
			
			String insertSql = "INSERT INTO STOCK (ITEM,PRICE,QUANTITY) " +
					"VALUES ('" + item + "', " + price + ", " + quantity+ ");";
			
			stmt = c.createStatement();
			
			stmt.executeUpdate(insertSql);
			
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
		System.out.println("WORD DELETED = " + stockItem);
		
		try {
			openDB();
			
			stmt = c.createStatement();
			String sql = "DELETE FROM STOCK WHERE ITEM = '" + stockItem + "';";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Deleted successfully.");
	}
		
	
	
	public static void dropStockTable() {
		
		try {
			openDB();
			
			stmt = c.createStatement();
			
			stmt.executeUpdate(dropStockTableSql);
			
			
			closeDB();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("STOCK table dropped successfully");
	}
	
}