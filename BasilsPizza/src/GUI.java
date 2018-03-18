import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
    
    private static DefaultTableModel stockTableModel;
    private static DefaultTableModel customersTableModel;
    
    private static JTable stockTable;
    private static JTable customersTable;
    
    private static GridBagConstraints gbc;
    
    
    public GUI() {
    		frame = new JFrame();
    		tabbedPane = new JTabbedPane();
    		gbc = new GridBagConstraints();
    		newOrderPanel();
    		ordersPanel();
        tabbedPane("New Order", newOrderPanel());
        tabbedPane("Orders", ordersPanel());
        tabbedPane("Tables", tablesPanel());
        tabbedPane("Stock", stockPanel());
        tabbedPane("Customers", customersPanel());
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
    		
    		JPanel panelStockGrid = new JPanel(new GridLayout(1, 2));
    		
    		JPanel panelStockTable = new JPanel(new BorderLayout());
    		JPanel panelStockForm = new JPanel(new GridBagLayout());
    		
    		
    		
    		JPanel panelStockButtons = new JPanel();
    		
    		
    		stockTableModel = new DefaultTableModel(new String[]{"Item", "Price", "Quantity"}, 0);
	
    		stockTable = new JTable(stockTableModel) {
    			
    			//Disables editing of table
    			public boolean isCellEditable(int row, int columns) {
    				return false;
    			}
    			
    			
    			// Makes every other row a different colour for readability
    			public Component prepareRenderer(TableCellRenderer r, int row, int columns) {
    				Component c = super.prepareRenderer(r, row, columns);
    				
    				
    					
    				//String cellData = stockTable.getModel().getValueAt(row, 0).toString();
    				
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
    		
    		//stockTable font and cell height
    		stockTable.setFont(new Font("", 0, 14));
    		stockTable.setRowHeight(stockTable.getRowHeight() + 10);
    		stockTable.setAutoCreateRowSorter(true);
    		
    		
    		
    		// Stock table mouse listener
    		stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent event) {
    				if (event.getClickCount() == 2) {
    					editItem(frame); // OPEN EDIT ITEM DIALOG ON DOUBLE CLICK
    				}
    			
    				
    				
    			}
    		});
    		
    		// Loop through result set ArrayList and adds to new array which can be used by TableModel
    		
    		
    		populateStockTable();	
    		
    		//stockTable.setPreferredScrollableViewportSize(new Dimension(450, 350)); // 450, 63 - original size
    		stockTable.setFillsViewportHeight(true);
    		
    		JScrollPane jsp = new JScrollPane(stockTable);
    		
    		
    		
    	
    		
    		
    		
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
        
    		panelStockTable.add(jsp, BorderLayout.CENTER);
    		panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);
    		
    		
    		panelStockMain.add(panelStockTable, BorderLayout.CENTER);
    		//panelStockMain.add(panelStockTable, BorderLayout.CENTER);
    		//panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);
    		
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
    	int rows = stockTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			stockTableModel.removeRow(i);
		}
    		
    		Database.selectStock();
    		
		for (int i = 0; i < Database.getStockArray().size(); i++) {
			
			
			//System.out.println(Database.getStockArray().get(i).getItem());
			String item = Database.getStockArray().get(i).getItem();
			String price = Database.getStockArray().get(i).getFormattedPrice(); 
			int quantity = Database.getStockArray().get(i).getQuantity();
			
			
			Object[] data = {item, price, quantity};
			//Arrays.sort(data);
				
			
			stockTableModel.addRow(data);
			
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
    
    private JPanel customersPanel() {
		JPanel panelCustomersMain = new JPanel(new BorderLayout());
		JPanel panelCustomersMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelCustomersFormMapGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelCustomersTable = new JPanel(new BorderLayout());
		JPanel panelCustomersTableButtons = new JPanel(new GridBagLayout()); //
		JPanel panelCustomersFormBorder = new JPanel(new BorderLayout());
		JPanel panelCustomersForm = new JPanel(new GridBagLayout());
		JPanel panelCustomersFormButtons = new JPanel(new FlowLayout());
		JPanel panelCustomersMap = new JPanel(new BorderLayout());
		JPanel panelCustomersMapButtons = new JPanel(new GridBagLayout());
		JPanel panelCustomersMapZoomButtons = new JPanel(new GridBagLayout());
		
		customersTableModel = new DefaultTableModel(new String[] {
				"First Name", "Last Name", "House Number", "Address",
				"City", "Postcode", "Phone Number"
		}, 0);
		
		customersTable = new JTable(customersTableModel ) {
			
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			
			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);
				
				if (row % 2 == 0) {
					c.setBackground(Color.WHITE);
				} else {
					c.setBackground(new Color(234, 234, 234));
				}
				
				if (isRowSelected(row)) {
					c.setBackground(new Color(24, 134, 254));
				}
				
				return c;
			}
		};
		
		customersTable.setFont(new Font("", 0, 14));
		customersTable.setRowHeight(customersTable.getRowHeight() + 10);
		customersTable.setAutoCreateRowSorter(true);
		
		customersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editCustomer(frame?)
				}
			}
		});
		
		populateCustomersTable();
		
		customersTable.setFillsViewportHeight(true);
		
		JScrollPane jsp = new JScrollPane(customersTable);
		
		JButton addCustomerBtn = new JButton();
		addCustomerBtn.setText("Add"); //Is this line needed?
		addCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//AddCustomer addCustomer = new AddCustomer(frame?);
			}
		});
		
		JButton editCustomerBtn = new JButton();
		editCustomerBtn.setText("Edit");
		editCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//editCustomer(frame?);
			}
		});
		
		JButton deleteCustomerBtn = new JButton();
		deleteCustomerBtn.setText("Delete");
		deleteCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//do stuff
			}
		});
		
		JButton mapExpandBtn = new JButton();
		mapExpandBtn.setText("Expand");
		mapExpandBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//do stuff
			}
		});
		
		JButton mapZoomInBtn = new JButton();
		mapZoomInBtn.setText("+");
		mapZoomInBtn.setPreferredSize(new Dimension(40,40));
		mapZoomInBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//do shit
			}
		});
		
		JButton mapZoomOutBtn = new JButton("-");
		mapZoomOutBtn.setPreferredSize(new Dimension(40,40));
		mapZoomOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//do shit
			}
		});
		
		//ADD THE BUTTONS!!!
		
		// TABLE BUTTONS
		

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersTableButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersTableButtons.add(editCustomerBtn, gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		panelCustomersTableButtons.add(deleteCustomerBtn, gbc);
		
		
		
		// CUSTOMER FORM
		
		JLabel lblCustomerFormTitle = new JLabel("Add new customer: ");
		JLabel lblCustomerFirstName = new JLabel("First name: ");
		JLabel lblCustomerLastName = new JLabel("Last name: ");
		JLabel lblCustomerHouseNumber = new JLabel("House number: ");
		JLabel lblCustomerAddress = new JLabel("Address: ");
		JLabel lblCustomerCity = new JLabel("City: ");
		JLabel lblCustomerPostcode = new JLabel("Postcode: ");
		JLabel lblCustomerPhoneNumber = new JLabel("Phone number: ");
		
		JTextField txtCustomerFirstName = new JTextField(20);
		JTextField txtCustomerLastName = new JTextField(20);
		JTextField txtCustomerHouseNumber = new JTextField(5);
		JTextField txtCustomerAddress = new JTextField(20);
		JTextField txtCustomerCity = new JTextField(20);
		JTextField txtCustomerPostcode = new JTextField(10);
		JTextField txtCustomerPhoneNumber = new JTextField(15);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5,10,0,0); //TOP, LEFT 
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(lblCustomerFormTitle, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(20,10,0,0); //TOP, LEFT 
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(lblCustomerFirstName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(5,10,0,0); //TOP, LEFT 
		panelCustomersForm.add(lblCustomerLastName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		panelCustomersForm.add(lblCustomerHouseNumber, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		panelCustomersForm.add(lblCustomerAddress, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		panelCustomersForm.add(lblCustomerCity, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		panelCustomersForm.add(lblCustomerPostcode, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		panelCustomersForm.add(lblCustomerPhoneNumber, gbc);
		
		// TEXT FIELDS
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(20,10,0,0); //TOP, LEFT 
		panelCustomersForm.add(txtCustomerFirstName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy++;
		gbc.insets = new Insets(5,10,0,0); //TOP, LEFT 
		panelCustomersForm.add(txtCustomerLastName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(txtCustomerHouseNumber, gbc);
		
		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(txtCustomerAddress, gbc);
		
		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(txtCustomerCity, gbc);
		
		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(txtCustomerPostcode, gbc);
		
		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(txtCustomerPhoneNumber, gbc);
		
		
		
		// FORM BUTTON - ADD
		
		gbc.gridx = 1;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(addCustomerBtn, gbc);

		
		//TRICK
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersForm.add(new JLabel(), gbc);
		
		
		// MAP BUTTONS
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersMapButtons.add(new JLabel(), gbc);
		
		
		
		
		gbc.gridx++;
		gbc.gridy = 0;
		panelCustomersMapButtons.add(mapExpandBtn, gbc);
		
		
		// MAP ZOOM BUTTONS
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersMapZoomButtons.add(new JLabel(), gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelCustomersMapZoomButtons.add(mapZoomInBtn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		panelCustomersMapZoomButtons.add(mapZoomOutBtn, gbc);
		
		
		
		//panelCustomersTableButtons.add(editCustomerBtn);
		//panelCustomersTableButtons.add(deleteCustomerBtn);
		//panelCustomersFormButtons.add(addCustomerBtn); 
		//panelCustomersMapButtons.add(mapExpandBtn);
		
		
		
		panelCustomersTable.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelCustomersTable.add(jsp, BorderLayout.CENTER);
		panelCustomersTable.add(panelCustomersTableButtons, BorderLayout.SOUTH);
		
		panelCustomersMainGrid.add(panelCustomersTable);
		panelCustomersMainGrid.add(panelCustomersFormMapGrid);
		
		panelCustomersFormBorder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panelCustomersFormBorder.add(panelCustomersForm, BorderLayout.CENTER);
		panelCustomersFormBorder.add(panelCustomersFormButtons, BorderLayout.SOUTH);
		
		//panelCustomersFormMapGrid.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//panelCustomersFormMapGrid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelCustomersFormMapGrid.add(panelCustomersFormBorder);
		
		
		
		CustomerMap home = new CustomerMap("163", "Shelbourne Road", "Bournemouth");
		
		
		panelCustomersMap.add(home.getImage(), BorderLayout.CENTER);
		panelCustomersMap.add(panelCustomersMapZoomButtons, BorderLayout.EAST);
		panelCustomersMap.add(panelCustomersMapButtons, BorderLayout.SOUTH);
		panelCustomersFormMapGrid.add(panelCustomersMap);
		
		panelCustomersMain.add(panelCustomersMainGrid);
		
		
		
		//panelCustomersMain.add(, BorderLayout.NORTH);
		
	
		return panelCustomersMain;
	
    }
    
    public static void populateCustomersTable() {
    	int rows = customersTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			customersTableModel.removeRow(i);
		}
    		
    		Database.selectCustomers();
    		
		for (int i = 0; i < Database.getCustomersArray().size(); i++) {
			
			
			String firstName = Database.getCustomersArray().get(i).getFirstName();
			String lastName = Database.getCustomersArray().get(i).getLastName(); 
			String houseNumber = Database.getCustomersArray().get(i).getHouseNumber();
			String address = Database.getCustomersArray().get(i).getAddress();
			String city = Database.getCustomersArray().get(i).getCity();
			String postcode = Database.getCustomersArray().get(i).getPostcode();
			String phoneNumber = Database.getCustomersArray().get(i).getPhoneNumber();
			
			
			Object[] data = {firstName, lastName, houseNumber, address, city,
					postcode, phoneNumber
					};
			
				
			
			customersTableModel.addRow(data);
			
			
			}
	}
    
    public void tabbedPane(String title, JPanel panel) {
    		tabbedPane.addTab(title, panel);
        frame.add(tabbedPane);
    }
    
    private void createAndShowGUI() {
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Basil's Pizza Ordering System");
        //frame.setSize(900, 700);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

}