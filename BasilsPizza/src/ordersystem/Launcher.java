package ordersystem;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) throws Exception {
		
		Database.initialise();
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new GUI();
            }
        });
		
		
		

	}

}
