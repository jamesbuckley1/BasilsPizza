import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class GUI {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    //private static Database db;
    private static DefaultTableModel model;
    private static JTable tableStock;
    
    
    public GUI() {
    		frame = new JFrame();
    		tabbedPane = new JTabbedPane();
    		//db = new Database();
    		newOrderPanel();
    		ordersPanel();
        tabbedPane("New Order", newOrderPanel());
        tabbedPane("Orders", ordersPanel());
        tabbedPane("Tables", tablesPanel());
        tabbedPane("Stock", stockPanel());
        tabbedPane("Staff", staffPanel());
        createAndShowGUI();
    }
    
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
    
    private JPanel stockPanel(){
    		JPanel panelStockMain = new JPanel();
    		panelStockMain.setLayout(new BorderLayout());
    		
    		JPanel panelStockTable = new JPanel();
    		panelStockTable.setLayout(new BorderLayout());
    		
    		JPanel panelStockButtons = new JPanel();
    		
    		
    		model = new DefaultTableModel(new String[]{"Item", "Price", "Quantity"}, 0);
	
    		tableStock = new JTable(model) {
    			
    			//Disables editing of table
    			public boolean isCellEditable(int row, int columns) {
    				return false;
    			}
    			
    			
    			// Makes every other row a different colour for readability
    			public Component prepareRenderer(TableCellRenderer r, int row, int columns) {
    				Component c = super.prepareRenderer(r, row, columns);
    				
    				
    				if (row % 2 == 0) {
    					c.setBackground(Color.WHITE);
    				}
    				else {
    					c.setBackground(new Color(234, 234, 234));
    				}
    				
    				if (isRowSelected(row)) {
    					c.setBackground(new Color(24, 134, 254));
    				}
    				
    				return c;
    			}
    		};
    		
    		tableStock.setFont(new Font("", 0, 14));
    		tableStock.setRowHeight(tableStock.getRowHeight() + 10);
    		
    		
    		
    		// Stock table mouse listener
    		tableStock.addMouseListener(new java.awt.event.MouseAdapter() {
    			@Override
    			public void mouseClicked(java.awt.event.MouseEvent event) {
    				int row = tableStock.getSelectedRow();
    				int col = tableStock.getSelectedColumn();
    				System.out.println("Row = " + row);
    				System.out.println("Col = " + col);
    				
    				
    				
    			}
    		});
    		
    		// Loop through result set ArrayList and adds to new array which can be used by TableModel
    		
    		
    		populateStockTable();	
    		
    		//tableStock.setPreferredScrollableViewportSize(new Dimension(450, 350)); // 450, 63 - original size
    		tableStock.setFillsViewportHeight(true);
    		
    		JScrollPane jsp = new JScrollPane(tableStock);
    		panelStockTable.add(jsp, BorderLayout.CENTER);
    		
    		// Buttons
    		JButton addBtn = new JButton();
    		addBtn.setText("Add");
    		addBtn.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent event) {
    				
    			AddStock addStock = new AddStock(frame);
    				
    			
    				
    			}
    		});
    		
    		JButton editBtn = new JButton();
    		editBtn.setText("Edit");
    		
        JButton deleteBtn = new JButton();
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent event) {
        			
        		}
        });
        
        panelStockButtons.add(addBtn);
        panelStockButtons.add(editBtn);
        panelStockButtons.add(deleteBtn);
        
    		
    		
    		panelStockMain.add(panelStockTable, BorderLayout.CENTER);
    		panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);
    		
    		return panelStockMain;
    }
    /*
    public static void resetStockTable() {
    		//model = new DefaultTableModel();
    		int rows = model.getRowCount();
    		for (int i = rows - 1; i >= 0; i --) {
    			model.removeRow(i);
    		}
    }
    */
    public static void populateStockTable() {
    	int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			model.removeRow(i);
		}
    		
    		Database.selectStock();
    		
		for (int i = 0; i < Database.getStockArray().size(); i++) {
			
			
			//System.out.println(Database.getStockArray().get(i).getItem());
			String item = Database.getStockArray().get(i).getItem();
			double price = Database.getStockArray().get(i).getPrice();
			int quantity = Database.getStockArray().get(i).getQuantity();
			
			Object[] data = {item, price, quantity};
			
			model.addRow(data);
			}
	}
    
    private JPanel staffPanel() {
    		JPanel panelStaff = new JPanel();
    		
    		return panelStaff;
    }
    
    public void tabbedPane(String title, JPanel panel) {
    		tabbedPane.addTab(title, panel);
        frame.add(tabbedPane);
    }
    
    private void createAndShowGUI() {
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Basil's Pizza Ordering System");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

}