import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) throws Exception {
		//Database db = new Database();
		//db.addStock("test2", 12.56, 19);
		Database.initialise();
		//Database.selectStock();
		//Database.dropStockTable();
		Database.insertStock("test1", 0.00, 0);
		Database.insertStock("test2", 0.00, 0);
		Database.insertStock("test3", 0.00, 0);
		Database.insertStock("test4", 0.00, 0);
		Database.insertStock("test5", 0.00, 0);
		Database.insertStock("test6", 0.00, 0);
		Database.insertStock("test7", 0.00, 0);
		
		//Database.dropStockTable();
		//db.select();
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GUI gui = new GUI();
            }
        });
		
		
		

	}

}
