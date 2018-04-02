package basilspizza;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) throws Exception {
		
		Database.initialise();
		
		//Database.dropStockTable();
		//Database.dropCustomersTable();
		//Database.insertCustomer("James", "Buckley", "88", "Longdown Lane South", "Epsom", "KT17 4JR", "07783021170");
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GUI gui = new GUI();
            }
        });
		
		
		

	}

}
