import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GUI {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    //private static Database db;
    private static DefaultTableModel model;
    private static JTable stockTable;
    
    
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
	
    		stockTable = new JTable(model) {
    			
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
    		
    		stockTable.setFont(new Font("", 0, 14));
    		stockTable.setRowHeight(stockTable.getRowHeight() + 10);
    		
    		
    		
    		// Stock table mouse listener
    		stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent event) {
    				if (event.getClickCount() == 2) {
    					editItem(frame);
    				}
    			
    				
    				
    			}
    		});
    		
    		// Loop through result set ArrayList and adds to new array which can be used by TableModel
    		
    		
    		populateStockTable();	
    		
    		//stockTable.setPreferredScrollableViewportSize(new Dimension(450, 350)); // 450, 63 - original size
    		stockTable.setFillsViewportHeight(true);
    		
    		JScrollPane jsp = new JScrollPane(stockTable);
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
    		editBtn.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent event) {
    				editItem(frame);
    				
    			}
    		});
    		
    		
        JButton deleteBtn = new JButton();
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent event) {
        			try {
        			int row = stockTable.getSelectedRow();
        			String cellData = stockTable.getModel().getValueAt(row, 0).toString();
        			System.out.println(cellData);
        			Database.deleteStock(cellData);
        			populateStockTable();
        			} catch (Exception e) {
        				JOptionPane.showMessageDialog(frame, "Please select an item to delete.",
        						"Error", JOptionPane.ERROR_MESSAGE);
        						
        			}
        			
        			
        		}
        });
        
        panelStockButtons.add(addBtn);
        panelStockButtons.add(editBtn);
        panelStockButtons.add(deleteBtn);
        
    		
    		
    		panelStockMain.add(panelStockTable, BorderLayout.CENTER);
    		panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);
    		
    		return panelStockMain;
    }
    
    public static void editItem(JFrame frame) {
    	try {
			int row = stockTable.getSelectedRow();
			String cellDataItem = stockTable.getModel().getValueAt(row, 0).toString();
			String cellDataPrice = stockTable.getModel().getValueAt(row, 1).toString();
			String cellDataQuantity = stockTable.getModel().getValueAt(row, 2).toString();
			AddStock edit = new AddStock(frame, cellDataItem, cellDataPrice, cellDataQuantity);
			} catch (Exception e){
				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
    }
    
    public static void populateStockTable() {
    	int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			model.removeRow(i);
		}
    		
    		Database.selectStock();
    		
		for (int i = 0; i < Database.getStockArray().size(); i++) {
			
			
			//System.out.println(Database.getStockArray().get(i).getItem());
			String item = Database.getStockArray().get(i).getItem();
			String price = Database.getStockArray().get(i).getFormattedPrice(); 
			int quantity = Database.getStockArray().get(i).getQuantity();
			
			
			Object[] data = {item, price, quantity};
			//Arrays.sort(data);
				
			
			model.addRow(data);
			
			/*
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(stockTable.getModel());
			stockTable.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			*/
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