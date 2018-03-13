import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) throws SQLException {
		Database db = new Database();
		//db.addStock("test2", 12.56, 19);
		
		
		
		
		
		//db.dropTable();
		//db.select();
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GUI gui = new GUI();
            }
        });
		
		
		

	}

}
