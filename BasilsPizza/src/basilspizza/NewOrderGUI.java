package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class NewOrderGUI {

	private JPanel panelNewOrdersMain;
	
	private JList tablesList;
	private DefaultListModel<String> tablesListModel;
	
	private JList customerList;
	private DefaultListModel customerListModel;

	private DefaultTableModel pizzaMenuTableModel;
	private JTable pizzaMenuTable;
	
	private DefaultTableModel orderTableModel;
	private JTable orderTable;

	public NewOrderGUI() {
		initGUI();
	}

	public void initGUI() {
		panelNewOrdersMain = new JPanel(new BorderLayout());
		JPanel panelNewOrdersMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelNewOrdersLeftSideGrid = new JPanel(new GridLayout(2, 1)); // Panel for select customer, select menu items, order summary table.
		JPanel panelNewOrdersRightSideGrid = new JPanel(new GridLayout(2, 1)); // Panel for all orders table.

		panelNewOrdersLeftSideGrid.add(selectOrderType());
		panelNewOrdersLeftSideGrid.add(selectMenu());
		//panelNewOrdersLeftSideGrid.add(orderDetails());

		panelNewOrdersRightSideGrid.add(orderTable());
		panelNewOrdersRightSideGrid.add(orderDetails());
		

		panelNewOrdersMainGrid.add(panelNewOrdersLeftSideGrid);
		panelNewOrdersMainGrid.add(panelNewOrdersRightSideGrid);

		panelNewOrdersMain.add(panelNewOrdersMainGrid, BorderLayout.CENTER);

	}

	public JPanel selectOrderType() {
		JPanel panelSelectOrderTypeMain = new JPanel(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Seated", seatedOrderPane());
		tabbedPane.add("Collection", collectionOrderPane());
		tabbedPane.add("Delivery", deliveryOrderPane());
		
		TitledBorder border = new TitledBorder("Order Type:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		tabbedPane.setBorder(border);

		panelSelectOrderTypeMain.add(tabbedPane);

		return panelSelectOrderTypeMain;

	}


	public JPanel seatedOrderPane() {


		JPanel panelSeatedOrderMain = new JPanel(new BorderLayout());
		JPanel panelSeatedOrder = new JPanel(new GridBagLayout());


		JButton buttonAddToOrder = new JButton("Add to Order");

		
		
		tablesListModel = new DefaultListModel();
		tablesList = new JList(tablesListModel);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//setSpecialRequirements(getSelectedTable());
			}
		};
		tablesList.addMouseListener(mouseListener);
		
		populateTables();
		
		JScrollPane jsp = new JScrollPane(tablesList);


		panelSeatedOrderMain.add(jsp, BorderLayout.CENTER);
		panelSeatedOrderMain.add(seatedOrderPaneNorthControls(), BorderLayout.NORTH);
		panelSeatedOrderMain.add(seatedOrderPaneSouthControls(), BorderLayout.SOUTH);

		TitledBorder border = new TitledBorder("Tables:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelSeatedOrderMain.setBorder(border);


		return panelSeatedOrderMain;
	}

	public JPanel seatedOrderPaneNorthControls() {
		JPanel panelSeatedOrderNorthControls = new JPanel(new GridBagLayout());

		JTextField textFieldTableSearch = new JTextField(20);

		JButton buttonTableSearchClear = new JButton("Clear Search");

		GridBagConstraints gbc = new GridBagConstraints();


		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelSeatedOrderNorthControls.add(new JLabel(), gbc);


		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelSeatedOrderNorthControls.add(textFieldTableSearch, gbc);


		gbc.gridx++;
		panelSeatedOrderNorthControls.add(buttonTableSearchClear, gbc);

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelSeatedOrderNorthControls.add(new JLabel(), gbc);

		return panelSeatedOrderNorthControls;
	}

	public JPanel seatedOrderPaneSouthControls() {
		JPanel panelSeatedOrderSouthControls = new JPanel(new GridBagLayout());

		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("TABLE REFRESH");
				populateTables();
			}
		});
		
		
		JButton buttonAddToOrder = new JButton("Add to Order");

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelSeatedOrderSouthControls.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelSeatedOrderSouthControls.add(buttonRefresh, gbc);
		
		gbc.gridx++;
		panelSeatedOrderSouthControls.add(buttonAddToOrder, gbc);


		return panelSeatedOrderSouthControls;
	}

	public void populateTables() {
		
		
		tablesListModel.removeAllElements();

		//DefaultListModel model = new DefaultListModel();
		//tablesList.setModel(model);
		
		Database.selectTables();

		for (int i = 0; i < Database.getTablesArray().size(); i++) {

			String tableId = Database.getTablesArray().get(i).getTableId();
			String assignedStaff = Database.getTablesArray().get(i).getAssignedStaff();
			String specialRequirements = Database.getTablesArray().get(i).getSpecialRequirements();
			String orderId = Database.getTablesArray().get(i).getOrderId();
		
			//Object[] data = {tableId, assignedStaff, specialRequirements, orderId};

			tablesListModel.addElement(tableId);
			
			
		}
		
		
		
	}
	
	public static void dunno() {
		
		//String test = "test";
		//tablesListModel.addElement(test);
		
		//tablesListModel.removeAllElements();
		
	}

	public JPanel collectionOrderPane() {
		JPanel panelCollectionOrdersMain = new JPanel(new BorderLayout());
		JPanel panelCollectionOrders = new JPanel();

		JTextField textFieldCustomerFirstName = new JTextField(20);


		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelCollectionOrders.add(new JLabel("Customer First Name: "), gbc);

		gbc.gridx++;
		panelCollectionOrders.add(textFieldCustomerFirstName, gbc);


		panelCollectionOrdersMain.add(panelCollectionOrders, BorderLayout.CENTER);
		panelCollectionOrdersMain.add(collectionOrderPaneSouthControls(), BorderLayout.SOUTH);
		return panelCollectionOrdersMain;

	}

	public JPanel collectionOrderPaneSouthControls() {
		JPanel panelWalkInButtons = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");

		GridBagConstraints gbc = new GridBagConstraints();

		// SPACING
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelWalkInButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelWalkInButtons.add(buttonAddToOrder, gbc);

		return panelWalkInButtons;
	}

	public JPanel deliveryOrderPane() {
		JPanel panelDeliveryOrderMain = new JPanel(new BorderLayout());
		JPanel panelDeliveryOrderPane = new JPanel(new GridBagLayout());
		JTextField textFieldCustomerSearch = new JTextField(20);
		JButton buttonCustomerSearchClear = new JButton("Clear Search");
		JButton buttonNewCustomer = new JButton("New Customer");

		JTextArea textAreaCustomerSearch = new JTextArea(12, 10);
		textAreaCustomerSearch.setLineWrap(true);

		JScrollPane jsp = new JScrollPane(textAreaCustomerSearch);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridx = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelDeliveryOrderPane.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelDeliveryOrderPane.add(textFieldCustomerSearch, gbc);



		gbc.gridx++;
		panelDeliveryOrderPane.add(buttonCustomerSearchClear, gbc);

		gbc.gridx++;
		panelDeliveryOrderPane.add(buttonNewCustomer, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		//gbc.ipady = 100;
		panelDeliveryOrderPane.add(jsp, gbc);


		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelDeliveryOrderPane.add(new JLabel(), gbc);

		panelDeliveryOrderMain.add(panelDeliveryOrderPane, BorderLayout.CENTER);
		panelDeliveryOrderMain.add(deliveryOrderPaneSouthControls(), BorderLayout.SOUTH);

		TitledBorder border = new TitledBorder("Customers:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelDeliveryOrderMain.setBorder(border);

		return panelDeliveryOrderMain;
	}

	public JPanel deliveryOrderPaneSouthControls() {
		JPanel panelDeliveryOrderSouthControls = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");

		GridBagConstraints gbc = new GridBagConstraints();

		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelDeliveryOrderSouthControls.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelDeliveryOrderSouthControls.add(buttonAddToOrder, gbc);

		return panelDeliveryOrderSouthControls;
	}





	public JPanel selectMenu() {
		JPanel panelSelectMenu = new JPanel(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Pizza", pizzaMenu());
		tabbedPane.add("Sides", sidesMenu());
		tabbedPane.add("Drinks", drinksMenu());
		tabbedPane.add("Desserts", dessertsMenu());
		
		TitledBorder border = new TitledBorder("Menu:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		tabbedPane.setBorder(border);

		panelSelectMenu.add(tabbedPane, BorderLayout.CENTER);



		return panelSelectMenu;
	}



	public JPanel pizzaMenu() {
		JPanel panelPizzaMenu = new JPanel(new BorderLayout());
		JPanel panelPizzaMenuButtons = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");



		//JTable pizzaMenuTable = new JTable();
		//DefaultTableModel pizzaMenuTableModel = new DefaultTableModel();
		pizzaMenuTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price"
		}, 0);

		JTable pizzaMenuTable = new JTable(pizzaMenuTableModel ) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Rows alternate in colour for readability.
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

		pizzaMenuTable.setFont(new Font("", 0, 14));
		pizzaMenuTable.setRowHeight(pizzaMenuTable.getRowHeight() + 10);
		pizzaMenuTable.setAutoCreateRowSorter(true);
		pizzaMenuTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


		populatePizzaMenuTable();



		JScrollPane jsp = new JScrollPane(pizzaMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pizzaMenuTable.setFillsViewportHeight(true);


		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelPizzaMenuButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelPizzaMenuButtons.add(buttonAddToOrder, gbc);

		/*
		TitledBorder border = new TitledBorder("Menu:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelPizzaMenu.setBorder(border);
		*/
		
		panelPizzaMenu.add(jsp, BorderLayout.CENTER);



		panelPizzaMenu.add(panelPizzaMenuButtons, BorderLayout.SOUTH);


		return panelPizzaMenu;
	}

	public void populatePizzaMenuTable() {
		int rows = pizzaMenuTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			pizzaMenuTableModel.removeRow(i);
		}

		Database.selectMenuItems();

		for (int i = 0; i < Database.getMenuItemArray().size(); i++) {

			String itemName = Database.getMenuItemArray().get(i).getItemName();
			double itemPrice = Database.getMenuItemArray().get(i).getItemPrice();



			Object[] data = {itemName, itemPrice
			};

			pizzaMenuTableModel.addRow(data);
			//pizzaMenuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//pizzaMenuTable.getSelectionModel().setSelectionInterval(0, 0);
			//pizzaMenuTable.setColumnSelectionInterval(0, 0);
			//pizzaMenuTable.requestFocusInWindow();
		}
	}



	public JPanel sidesMenu() {
		JPanel panelSidesMenu = new JPanel();


		return panelSidesMenu;
	}

	public JPanel drinksMenu() {
		JPanel panelDrinksMenu = new JPanel();


		return panelDrinksMenu;
	}

	public JPanel dessertsMenu() {
		JPanel panelDessertMenu = new JPanel();


		return panelDessertMenu;
	}
	
	public JPanel orderTable() {
		JPanel panelOrderTableMain = new JPanel(new BorderLayout());
		
		orderTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price", "Quantity"
				
		}, 0);

		orderTable = new JTable(orderTableModel ) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Rows alternate in colour for readability.
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

		orderTable.setFont(new Font("", 0, 14));
		orderTable.setRowHeight(orderTable.getRowHeight() + 10);
		orderTable.setAutoCreateRowSorter(true);
		orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		orderTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = orderTable.getSelectedRow();
					String houseNumber = orderTable.getModel().getValueAt(row, 2).toString();
					String address = orderTable.getModel().getValueAt(row, 3).toString();
					String city = orderTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		//populateCustomersTable();

		JScrollPane jsp = new JScrollPane(orderTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		orderTable.setFillsViewportHeight(true);
		
		panelOrderTableMain.add(jsp);

		TitledBorder border = new TitledBorder("Order:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelOrderTableMain.setBorder(border);
		
		return panelOrderTableMain;
	}
	
	public JPanel orderDetails() {
		JPanel panelOrderSummaryMain = new JPanel(new BorderLayout());
		JPanel panelOrderSummary = new JPanel(new GridBagLayout());
		
		JTextField textFieldOrderType = new JTextField(20);
		JTextField textFieldTableName = new JTextField(20);
		JTextField textFieldCustomerName = new JTextField(20);
		JTextField textFieldCustomerHouseNumber = new JTextField(20);
		JTextField textFieldCustomerAddress = new JTextField(20);
		JTextField textFieldCustomerCity = new JTextField(20);
		JTextField textFieldCustomerPostcode = new JTextField(20);
		JTextField textFieldCustomerPhoneNumber = new JTextField(20);
		JTextField textFieldTotalPrice = new JTextField(20);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(10, 20, 0, 0);
		panelOrderSummary.add(new JLabel("Order Type: "), gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(0, 20, 0, 0);
		panelOrderSummary.add(new JLabel("Table Name: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Customer Name: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Customer House Number: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Customer Address: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Customer City: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Customer Postcode: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Customer Phone Number: "), gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(new JLabel("Total Price: "), gbc);
		
		// TEXT FIELDS
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.anchor = GridBagConstraints.LINE_START;
		panelOrderSummary.add(textFieldOrderType, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(0, 0, 0 , 0);
		panelOrderSummary.add(textFieldTableName, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldCustomerName, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldCustomerHouseNumber, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldCustomerAddress, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldCustomerCity, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldCustomerPostcode, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldCustomerPhoneNumber, gbc);
		
		gbc.gridy++;
		panelOrderSummary.add(textFieldTotalPrice, gbc);
		
		
		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelOrderSummary.add(new JLabel(), gbc);
		
		
		
		TitledBorder border = new TitledBorder("Order Details:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelOrderSummaryMain.setBorder(border);
		
		panelOrderSummaryMain.add(panelOrderSummary, BorderLayout.CENTER);
		panelOrderSummaryMain.add(orderDetailsSouthControls(), BorderLayout.SOUTH);
		
		return panelOrderSummaryMain;
		
	}
	
	public JPanel orderDetailsSouthControls() {
		JPanel panelOrderSummarySouth = new JPanel(new GridBagLayout());
		
		JButton buttonPlaceOrder = new JButton("Place Order");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelOrderSummarySouth.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelOrderSummarySouth.add(buttonPlaceOrder, gbc);
		
		
		return panelOrderSummarySouth;
		
	}



	public JPanel getOrdersPanel() {
		return panelNewOrdersMain;
	}



}
