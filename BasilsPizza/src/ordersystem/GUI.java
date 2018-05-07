package ordersystem;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    
    public GUI() {
    		
    				frame = new JFrame();
    				
    	    		tabbedPane = new JTabbedPane();
    	    		
    	    		/*
    	    		tabbedPane.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							System.out.println("TAB CHANGED");
							
							if (tabbedPane.getSelectedIndex() == 0) {
								System.out.println("NEW ORDER TAB SELECTED");
								NewOrderGUI n = new NewOrderGUI();
								n.populateTables();
							}
						}
    	    		});
    	    		
    	    		*/
    	    		
    	    		//newOrderPanel();
    	    		//ordersPanel();
    	        tabbedPane("New Order", new NewOrderGUI().getOrdersPanel());
    	        tabbedPane("Orders", new OrdersGUI().getOrdersPanel());
    	        tabbedPane("Tables", new TablesGUI().getTablesPanel());
    	        tabbedPane("Stock", new StockGUI().getStockPanel());
    	        tabbedPane("Customers", new CustomerGUI().getCustomerPanel());
    	        tabbedPane("Menu", new MenuGUI().getMenuPanel());
    	        tabbedPane("Staff", new StaffGUI().getStaffPanel());
    	        
    	        //tabbedPane("Menu", new MenuGUI().getMenuPanel());
    	        createAndShowGUI();
    			
    		
    }
    
    /*
    private JPanel newOrderPanel() {
    		JPanel panelNewOrder = new JPanel(new BorderLayout());
		JLabel lbl1 = new JLabel("New Orders.");
		panelNewOrder.add(lbl1, BorderLayout.NORTH);
		
		return panelNewOrder;
		
    }
    
    private JPanel ordersPanel() {
    		JPanel panelOrders = new JPanel();
    		JLabel lbl1 = new JLabel("Orders.");
    		panelOrders.add(lbl1);
    		
    		return panelOrders;
    }
    
    private JPanel tablesPanel() {
    		JPanel panelTables = new JPanel();
    		
    		return panelTables;
    }
    
    
    
    
    
    
    
   
    private JPanel staffPanel() {
    		JPanel panelStaff = new JPanel();
    		
    		return panelStaff;
    }
    
    
    */
    
    
    
    
    
    
    public void tabbedPane(String title, JPanel panel) {
    		tabbedPane.addTab(title, panel);
        frame.add(tabbedPane);
    }
    
    private void createAndShowGUI() {
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Open Source Ordering System");
        //frame.setSize(900, 700);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

}