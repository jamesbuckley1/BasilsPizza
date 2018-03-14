import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) throws Exception {
		
		Database.initialise();
		Database.deleteStock("TEST9");
		
		//Database.dropStockTable();
		
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GUI gui = new GUI();
            }
        });
		
		
		

	}

}
