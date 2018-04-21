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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class NewOrderGUI {

	private JPanel panelNewOrdersMain;

	private JList tablesList;
	private DefaultListModel<String> tablesListModel;

	private JList customerList;
	private DefaultListModel customerListModel;

	//private DefaultTableModel menuTableModel;
	private JTable menuTable, pizzaMenuTable, sidesMenuTable, drinksMenuTable, dessertsMenuTable;

	private DefaultTableModel pizzaMenuTableModel, sidesMenuTableModel, drinksMenuTableModel, dessertsMenuTableModel;

	private JTable orderTable;
	private DefaultTableModel orderTableModel;
	


	private JTextField textFieldOrderType, textFieldTableName, textFieldCustomerCollectionNameInput, 
						textFieldCustomerCollectionPhoneNumberInput,	textFieldCustomerName,
						textFieldCustomerHouseNumber, textFieldCustomerAddress,
						textFieldCustomerCity, textFieldCustomerPostcode,
						textFieldCustomerPhoneNumber, textFieldTotalPrice;
	

	private String orderType;
	private String tableName;
	private double totalPrice;

	private JComboBox<Integer> comboboxPizzaMenuQuantity, comboboxSidesMenuQuantity, comboboxDrinksMenuQuantity, comboboxDessertsMenuQuantity;

	private MenuGUI menuGUI;
	
	private String pizzaMenuQuantity;






	public NewOrderGUI() {
		menuGUI = new MenuGUI();
		initGUI();
	}

	public void initGUI() {


		panelNewOrdersMain = new JPanel(new BorderLayout());
		JPanel panelNewOrdersMainGrid = new JPanel(new GridLayout(1, 2));

		JPanel panelNewOrdersLeftSideGrid = new JPanel(new GridLayout(2, 1)); // Panel for select customer, select menu items, order summary table.
		JPanel panelNewOrdersRightSideGrid = new JPanel(new GridLayout(2, 1)); // Panel for all orders table.

		JPanel panelOrder = new JPanel(new BorderLayout());

		JPanel panelMenu = new JPanel(new BorderLayout());



		panelOrder.add(selectOrderType(), BorderLayout.CENTER);


		panelMenu.add(selectMenu(), BorderLayout.CENTER);

		panelNewOrdersLeftSideGrid.add(panelOrder);
		panelNewOrdersLeftSideGrid.add(panelMenu);




		panelNewOrdersRightSideGrid.add(orderTable());
		panelNewOrdersRightSideGrid.add(orderDetails());


		panelNewOrdersMainGrid.add(panelNewOrdersLeftSideGrid);
		panelNewOrdersMainGrid.add(panelNewOrdersRightSideGrid);

		panelNewOrdersMain.add(panelNewOrdersMainGrid, BorderLayout.CENTER);

		/*
		//Timer timer = new Timer();
		TimerTask myTask = new TimerTask() {

			@Override
			public void run() {

			}

		};
		//timer.sch

		 */


	}



	public JPanel selectOrderType() {
		JPanel panelSelectOrderTypeMain = new JPanel(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Table", tablesOrderPane());
		tabbedPane.add("Collection", collectionOrderPane());
		tabbedPane.add("Delivery", deliveryOrderPane());

		TitledBorder border = new TitledBorder("Order Type:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		tabbedPane.setBorder(border);

		panelSelectOrderTypeMain.add(tabbedPane);

		return panelSelectOrderTypeMain;

	}


	public JPanel tablesOrderPane() {


		JPanel panelSeatedOrderMain = new JPanel(new BorderLayout());
		JPanel panelSeatedOrder = new JPanel(new GridBagLayout());




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
		panelSeatedOrderMain.add(tablesOrderPaneNorthControls(), BorderLayout.NORTH);
		panelSeatedOrderMain.add(tablesOrderPaneSouthControls(), BorderLayout.SOUTH);

		TitledBorder border = new TitledBorder("Tables:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelSeatedOrderMain.setBorder(border);


		return panelSeatedOrderMain;
	}

	public JPanel tablesOrderPaneNorthControls() {
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

	public JPanel tablesOrderPaneSouthControls() {
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
		buttonAddToOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				orderType = "TABLE";
				tableName = getSelectedTableName();

				setOrderDetailsOrderType(orderType);
				setOrderDetailsTableName(tableName);
				grayCustomerInfoTextFields();
			}
		});

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



	public JPanel collectionOrderPane() {
		JPanel panelCollectionOrdersMain = new JPanel(new BorderLayout());
		JPanel panelCollectionOrders = new JPanel(new GridBagLayout());

		textFieldCustomerCollectionNameInput = new JTextField(15);
		textFieldCustomerCollectionPhoneNumberInput = new JTextField(15);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCollectionOrders.add(new JLabel("Customer First Name: "), gbc);
		
		gbc.gridy++;
		panelCollectionOrders.add(new JLabel("Customer Phone Number: "), gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelCollectionOrders.add(textFieldCustomerCollectionNameInput, gbc);
		
		gbc.gridy++;
		panelCollectionOrders.add(textFieldCustomerCollectionPhoneNumberInput, gbc);


		panelCollectionOrdersMain.add(panelCollectionOrders, BorderLayout.CENTER);
		panelCollectionOrdersMain.add(collectionOrderPaneSouthControls(), BorderLayout.SOUTH);
		return panelCollectionOrdersMain;

	}

	public JPanel collectionOrderPaneSouthControls() {
		JPanel panelWalkInButtons = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");
		buttonAddToOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grayTextFieldsForCollection();
				setOrderDetailsOrderType("COLLECTION");
				setOrderDetailsCustomerName(textFieldCustomerCollectionNameInput.getText());
				setOrderDetailsCustomerPhoneNumber(textFieldCustomerCollectionPhoneNumberInput.getText());
			}
		});

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
		tabbedPane.add("Pizza", pizzaMenuTable());
		tabbedPane.add("Sides", sidesMenuTable());
		tabbedPane.add("Drinks", drinksMenuTable());
		tabbedPane.add("Desserts", dessertsMenuTable());


	


		TitledBorder border = new TitledBorder("Menu:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		tabbedPane.setBorder(border);

		panelSelectMenu.add(tabbedPane, BorderLayout.CENTER);



		return panelSelectMenu;
	}


	public JPanel pizzaMenuTable() {
		JPanel panelPizzaMenuTable = new JPanel(new BorderLayout());



		pizzaMenuTableModel = menuGUI.getPizzaMenuTableModel();

		pizzaMenuTable = new JTable(pizzaMenuTableModel) {
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
		pizzaMenuTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = pizzaMenuTable.getSelectedRow();
					String houseNumber = pizzaMenuTable.getModel().getValueAt(row, 2).toString();
					String address = pizzaMenuTable.getModel().getValueAt(row, 3).toString();
					String city = pizzaMenuTable.getModel().getValueAt(row, 4).toString();
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

		JScrollPane jsp = new JScrollPane(pizzaMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pizzaMenuTable.setFillsViewportHeight(true);

		panelPizzaMenuTable.add(jsp);



		panelPizzaMenuTable.add(pizzaMenuSouthControls(), BorderLayout.SOUTH);

		return panelPizzaMenuTable;
	}
	
	public JPanel pizzaMenuSouthControls() {
		JPanel panelPizzaMenuButtons = new JPanel(new GridBagLayout());
		
		comboboxPizzaMenuQuantity = new JComboBox<Integer>();
		for (int i = 1; i <= 50; i ++) {
			comboboxPizzaMenuQuantity.addItem(i);;
		}
		comboboxPizzaMenuQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pizzaMenuQuantity = (comboboxPizzaMenuQuantity.getSelectedItem().toString());
			}
		});
		

		JButton buttonAddToOrder = new JButton("Add to Order");
		buttonAddToOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] data = {getSelectedMenuItem(pizzaMenuTable), getSelectedMenuItemPrice(pizzaMenuTable), getQuantity(comboboxPizzaMenuQuantity)
				};
				orderTableModel.addRow(data);

				totalPrice += Double.parseDouble(getSelectedMenuItemPrice(pizzaMenuTable)) * Integer.parseInt(getQuantity(comboboxPizzaMenuQuantity));
				setTotalPrice(totalPrice);
			}
		});

		
		

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelPizzaMenuButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelPizzaMenuButtons.add(new JLabel("Quantity: "), gbc);

		gbc.gridx++;
		panelPizzaMenuButtons.add(comboboxPizzaMenuQuantity, gbc);

		gbc.gridx++;
		panelPizzaMenuButtons.add(buttonAddToOrder, gbc);

		return panelPizzaMenuButtons;
	}

	public JPanel sidesMenuTable() {
		JPanel panelSidesMenuTable = new JPanel(new BorderLayout());



		sidesMenuTableModel = menuGUI.getSidesMenuTableModel();

		sidesMenuTable = new JTable(sidesMenuTableModel) {
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

		sidesMenuTable.setFont(new Font("", 0, 14));
		sidesMenuTable.setRowHeight(sidesMenuTable.getRowHeight() + 10);
		sidesMenuTable.setAutoCreateRowSorter(true);
		sidesMenuTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sidesMenuTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = pizzaMenuTable.getSelectedRow();
					String houseNumber = pizzaMenuTable.getModel().getValueAt(row, 2).toString();
					String address = pizzaMenuTable.getModel().getValueAt(row, 3).toString();
					String city = pizzaMenuTable.getModel().getValueAt(row, 4).toString();
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

		JScrollPane jsp = new JScrollPane(sidesMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		sidesMenuTable.setFillsViewportHeight(true);

		panelSidesMenuTable.add(jsp);

		


		panelSidesMenuTable.add(sidesMenuSouthControls(), BorderLayout.SOUTH);
		
		
		return panelSidesMenuTable;
	}
	
	
	public JPanel sidesMenuSouthControls() {
		JPanel panelSidesMenuButtons = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");
		buttonAddToOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] data = {getSelectedMenuItem(sidesMenuTable), getSelectedMenuItemPrice(sidesMenuTable), getQuantity(comboboxSidesMenuQuantity)
				};
				orderTableModel.addRow(data);

				totalPrice += Double.parseDouble(getSelectedMenuItemPrice(sidesMenuTable)) * Integer.parseInt(getQuantity(comboboxSidesMenuQuantity));
				setTotalPrice(totalPrice);
			}
		});

		comboboxSidesMenuQuantity = new JComboBox<Integer>();
		for (int i = 1; i <= 50; i ++) {
			comboboxSidesMenuQuantity.addItem(i);;
		}

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelSidesMenuButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelSidesMenuButtons.add(new JLabel("Quantity: "), gbc);

		gbc.gridx++;
		panelSidesMenuButtons.add(comboboxSidesMenuQuantity, gbc);

		gbc.gridx++;
		panelSidesMenuButtons.add(buttonAddToOrder, gbc);

		return panelSidesMenuButtons;
	}

	public JPanel drinksMenuTable() {
		JPanel panelDrinksMenuTable = new JPanel(new BorderLayout());



		
		drinksMenuTableModel = menuGUI.getDrinksMenuTableModel();

		drinksMenuTable = new JTable(drinksMenuTableModel) {
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

		drinksMenuTable.setFont(new Font("", 0, 14));
		drinksMenuTable.setRowHeight(drinksMenuTable.getRowHeight() + 10);
		drinksMenuTable.setAutoCreateRowSorter(true);
		drinksMenuTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		drinksMenuTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				
			}
		});

		//populateCustomersTable();

		JScrollPane jsp = new JScrollPane(drinksMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		drinksMenuTable.setFillsViewportHeight(true);

		panelDrinksMenuTable.add(jsp);



		panelDrinksMenuTable.add(drinksMenuSouthControls(), BorderLayout.SOUTH);

		return panelDrinksMenuTable;
	}


	public JPanel drinksMenuSouthControls() {
		JPanel panelDrinksMenuButtons = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");
		buttonAddToOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] data = {getSelectedMenuItem(drinksMenuTable), getSelectedMenuItemPrice(drinksMenuTable), getQuantity(comboboxDrinksMenuQuantity)
				};
				orderTableModel.addRow(data);

				totalPrice += Double.parseDouble(getSelectedMenuItemPrice(drinksMenuTable)) * Integer.parseInt(getQuantity(comboboxDrinksMenuQuantity));
				setTotalPrice(totalPrice);
			}
		});

		comboboxDrinksMenuQuantity = new JComboBox<Integer>();
		for (int i = 1; i <= 50; i ++) {
			comboboxDrinksMenuQuantity.addItem(i);;
		}

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelDrinksMenuButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelDrinksMenuButtons.add(new JLabel("Quantity: "), gbc);

		gbc.gridx++;
		panelDrinksMenuButtons.add(comboboxDrinksMenuQuantity, gbc);

		gbc.gridx++;
		panelDrinksMenuButtons.add(buttonAddToOrder, gbc);

		return panelDrinksMenuButtons;
	}
	
	public JPanel dessertsMenuTable() {
		JPanel panelDessertsMenuTable = new JPanel(new BorderLayout());



		
		dessertsMenuTableModel = menuGUI.getDessertsMenuTableModel();

		dessertsMenuTable = new JTable(dessertsMenuTableModel) {
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

		dessertsMenuTable.setFont(new Font("", 0, 14));
		dessertsMenuTable.setRowHeight(dessertsMenuTable.getRowHeight() + 10);
		dessertsMenuTable.setAutoCreateRowSorter(true);
		dessertsMenuTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		dessertsMenuTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				
			}
		});

		//populateCustomersTable();

		JScrollPane jsp = new JScrollPane(dessertsMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		dessertsMenuTable.setFillsViewportHeight(true);

		panelDessertsMenuTable.add(jsp);



		panelDessertsMenuTable.add(dessertsMenuSouthControls(), BorderLayout.SOUTH);

		return panelDessertsMenuTable;
	}


	public JPanel dessertsMenuSouthControls() {
		JPanel panelDessertsMenuButtons = new JPanel(new GridBagLayout());

		JButton buttonAddToOrder = new JButton("Add to Order");
		buttonAddToOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] data = {getSelectedMenuItem(dessertsMenuTable), getSelectedMenuItemPrice(dessertsMenuTable), getQuantity(comboboxDessertsMenuQuantity)
				};
				orderTableModel.addRow(data);

				totalPrice += Double.parseDouble(getSelectedMenuItemPrice(dessertsMenuTable)) * Integer.parseInt(getQuantity(comboboxDessertsMenuQuantity));
				setTotalPrice(totalPrice);
			}
		});

		comboboxDessertsMenuQuantity = new JComboBox<Integer>();
		for (int i = 1; i <= 50; i ++) {
			comboboxDessertsMenuQuantity.addItem(i);;
		}

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelDessertsMenuButtons.add(new JLabel(), gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelDessertsMenuButtons.add(new JLabel("Quantity: "), gbc);

		gbc.gridx++;
		panelDessertsMenuButtons.add(comboboxDessertsMenuQuantity, gbc);

		gbc.gridx++;
		panelDessertsMenuButtons.add(buttonAddToOrder, gbc);

		return panelDessertsMenuButtons;
	}

	/*
	public void setPizzaMenuTableModel() {




		//pizzaMenuTableModel = menuGUI.getPizzaMenuTableModel();
		//menuTable.setModel(pizzaMenuTableModel);



	}

	public void setdrinksMenuTableModel() {

		drinksMenuTableModel = menuGUI.getDrinksMenuTableModel();
		menuTable.setModel(drinksMenuTableModel);
	}


	*/


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

		textFieldOrderType = new JTextField(20);
		textFieldOrderType.setEditable(false);

		textFieldTableName = new JTextField(20);
		textFieldTableName.setEditable(false);

		textFieldCustomerName = new JTextField(20);
		textFieldCustomerName.setEditable(false);

		textFieldCustomerHouseNumber = new JTextField(20);
		textFieldCustomerHouseNumber.setEditable(false);

		textFieldCustomerAddress = new JTextField(20);
		textFieldCustomerAddress.setEditable(false);

		textFieldCustomerCity = new JTextField(20);
		textFieldCustomerCity.setEditable(false);

		textFieldCustomerPostcode = new JTextField(20);
		textFieldCustomerPostcode.setEditable(false);

		textFieldCustomerPhoneNumber = new JTextField(20);
		textFieldCustomerPhoneNumber.setEditable(false);

		textFieldTotalPrice = new JTextField(20);
		textFieldTotalPrice.setEditable(false);








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

	public String getSelectedTableName() {
		System.out.println("SELECTED TABLE NAME " + tablesList.getSelectedValue().toString());
		return tablesList.getSelectedValue().toString();
	}


	public String getSelectedMenuItem(JTable menuTable) {



		String menuItemName;

		int column = 0;
		int row = menuTable.getSelectedRow();
		menuItemName = menuTable.getModel().getValueAt(row, 0).toString();

		System.out.println("SELECTED ROW sff" + menuItemName);

		System.out.println("getSelectedMenuItemName = " + menuItemName);
		return menuItemName;
	}
	
	



	public String getSelectedMenuItemPrice(JTable menuTable) {
		String menuItemPrice;

		
		int row = menuTable.getSelectedRow();
		menuItemPrice = menuTable.getModel().getValueAt(row, 1).toString();

		return menuItemPrice;
	}

	public String getQuantity(JComboBox combobox) {
		
		
		System.out.println(combobox.getSelectedItem().toString());
		return combobox.getSelectedItem().toString();
	}

	public void setOrderDetailsOrderType(String orderType) {
		textFieldOrderType.setText(orderType);
	}

	public void setOrderDetailsTableName(String tableName	) {
		textFieldTableName.setText(tableName);
	}

	public void setOrderDetailsCustomerName(String customerName) {
		textFieldCustomerName.setText(customerName);
	}
	
	public void setOrderDetailsCustomerPhoneNumber(String phoneNumber) {
		textFieldCustomerPhoneNumber.setText(phoneNumber);
	}
	
	public void grayCustomerInfoTextFields() { // Used when order type is TABLE.
		textFieldCustomerName.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerHouseNumber.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerAddress.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerCity.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerPostcode.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerPhoneNumber.setBackground(Color.LIGHT_GRAY);
	}

	public void grayTextFieldsForCollection() { // Used when order type is COLLECTION.
		textFieldTableName.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerHouseNumber.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerAddress.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerCity.setBackground(Color.LIGHT_GRAY);
		textFieldCustomerPostcode.setBackground(Color.LIGHT_GRAY);
	}
	
	public void setTotalPrice(Double totalPrice) {
		textFieldTotalPrice.setText(totalPrice.toString());
	}

	public JPanel getOrdersPanel() {
		return panelNewOrdersMain;
	}



}
