package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class OrdersGUI {

	private JPanel panelOrdersMain;

	private static JTable tableTableOrders, tableCollectionOrders, tableDeliveryOrders;
	private static DefaultTableModel tableTableOrdersModel, tableCollectionOrdersModel, tableDeliveryOrdersModel;

	private DefaultTableModel tableOrderViewModel;

	private JTable tableOrderView;

	private int selectedTableOrdersRow, selectedCollectionOrdersRow, selectedDeliveryOrdersRow;
	
	private JTextField textFieldOrderType, textFieldTableName, textFieldCustomerName, textFieldCustomerHouseNumber,
						textFieldCustomerAddress, textFieldCustomerCity, textFieldCustomerPostcode,
						textFieldCustomerPhoneNumber, textFieldTotalPrice;

	public OrdersGUI() {
		initGUI();
		//startRefreshTablesTimer();
	}

	public void initGUI() {
		panelOrdersMain = new JPanel(new BorderLayout());
		JPanel panelOrdersMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelOrdersRightGrid = new JPanel(new GridLayout(2, 1));

		panelOrdersMainGrid.add(selectOrder());
		panelOrdersMainGrid.add(panelOrdersRightGrid);
		panelOrdersRightGrid.add(orderView());
		panelOrdersRightGrid.add(orderDetails());


		panelOrdersMain.add(panelOrdersMainGrid, BorderLayout.CENTER);


	}


	public JPanel selectOrder() {
		JPanel panelSelectOrderMain = new JPanel(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Table", tableOrders());
		tabbedPane.add("Collection", collectionOrders());
		tabbedPane.add("Delivery", deliveryOrders());
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0 && tableTableOrdersModel.getRowCount() >= 1) { // Row count must be higher than 0 or ArrayOutOfBounds exception occurs as nothing is in the table.
					populateTableOrdersView(getSelectedTableOrdersOrderId(getSelectedTableOrdersRow()));
				} else if (tabbedPane.getSelectedIndex() == 1 && tableCollectionOrdersModel.getRowCount() >= 1 ) {
					populateCollectionOrdersView(getSelectedCollectionOrdersOrderId(getSelectedCollectionOrdersRow()));
				} else if (tabbedPane.getSelectedIndex() == 2 && tableDeliveryOrdersModel.getRowCount() >= 1) {
					populateDeliveryOrdersView(getSelectedDeliveryOrdersOrderId(getSelectedDeliveryOrdersRow()));
				}
			}
		});
		
		TitledBorder border = new TitledBorder("Open Orders: ");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelSelectOrderMain.setBorder(border);

		panelSelectOrderMain.add(tabbedPane, BorderLayout.CENTER);

		return panelSelectOrderMain;
	}

	private JPanel tableOrders() {
		JPanel panelTableOrdersMain = new JPanel(new BorderLayout());

		//panelTableOrdersMain.add(new JLabel("panelTableOrdersMain"), BorderLayout.CENTER);

		tableTableOrdersModel = new DefaultTableModel(new String[] {
				"Order ID", "Table ID", "Order Time"
		}, 0);

		tableTableOrders = new JTable(tableTableOrdersModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth())); // Sets width of columns to fill content.


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

		tableTableOrders.setFont(new Font("", 0, 14));
		tableTableOrders.setRowHeight(tableTableOrders.getRowHeight() + 10);
		tableTableOrders.setAutoCreateRowSorter(true);
		tableTableOrders.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableTableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableTableOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableTableOrders.getSelectedRow();
					int orderId = Integer.parseInt(tableTableOrders.getModel().getValueAt(row, 0).toString());
					populateTableOrdersView(orderId);
					selectedTableOrdersRow = row;
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateTableOrders();
		

		JScrollPane jsp = new JScrollPane(tableTableOrders,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//tableTableOrders.setRowSelectionInterval(1, 0);
		tableTableOrders.setFillsViewportHeight(true);
		
		

		panelTableOrdersMain.add(jsp, BorderLayout.CENTER);

		

		return panelTableOrdersMain;
	}

	public static void populateTableOrders() {
		int rows = tableTableOrdersModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			tableTableOrdersModel.removeRow(i);
		}


		Database.selectTableOrders();

		for (int i = 0; i < Database.getTableOrdersArray().size(); i++) {

			int orderId = Database.getTableOrdersArray().get(i).getOrderId();
			String tableId = Database.getTableOrdersArray().get(i).getTableName();
			String orderTime = Database.getTableOrdersArray().get(i).getDateTime();



			Object[] data = {orderId, tableId, orderTime
			};

			tableTableOrdersModel.addRow(data);
			//menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuTable.getSelectionModel().setSelectionInterval(0, 0);
			//menuTable.setColumnSelectionInterval(0, 0);
			//menuTable.requestFocusInWindow();

			tableTableOrders.setModel(tableTableOrdersModel);


		}


	}

	private JPanel collectionOrders() {
		JPanel panelCollectionOrdersMain = new JPanel(new BorderLayout());


		tableCollectionOrdersModel = new DefaultTableModel(new String[] {
				"Order ID", "Customer Name", "Order Time"
		}, 0);

		tableCollectionOrders = new JTable(tableCollectionOrdersModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth())); // Sets width of columns to fill content.


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

		tableCollectionOrders.setFont(new Font("", 0, 14));
		tableCollectionOrders.setRowHeight(tableCollectionOrders.getRowHeight() + 10);
		tableCollectionOrders.setAutoCreateRowSorter(true);
		tableCollectionOrders.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableCollectionOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCollectionOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableCollectionOrders.getSelectedRow();
					int orderId = Integer.parseInt(tableCollectionOrders.getModel().getValueAt(row, 0).toString());
					
					//selectedCollectionOrdersRow = row;
					
					populateCollectionOrdersView(orderId);
					
					
					
					
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateCollectionOrders();

		JScrollPane jsp = new JScrollPane(tableCollectionOrders,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//tableCollectionOrders.setRowSelectionInterval(0, 0);
		
		tableCollectionOrders.setFillsViewportHeight(true);

		panelCollectionOrdersMain.add(jsp, BorderLayout.CENTER);

		return panelCollectionOrdersMain;
	}

	public static void populateCollectionOrders() {
		int rows = tableCollectionOrdersModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			tableCollectionOrdersModel.removeRow(i);
		}


		Database.selectCollectionOrders();

		for (int i = 0; i < Database.getCollectionOrdersArray().size(); i++) {

			int orderId = Database.getCollectionOrdersArray().get(i).getOrderId();
			String customerName = Database.getCollectionOrdersArray().get(i).getCustomerName();
			String orderTime = Database.getCollectionOrdersArray().get(i).getDateTime();



			Object[] data = {orderId, customerName, orderTime
			};

			tableCollectionOrdersModel.addRow(data);
			//menuCollection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuCollection.getSelectionModel().setSelectionInterval(0, 0);
			//menuCollection.setColumnSelectionInterval(0, 0);
			//menuCollection.requestFocusInWindow();

			tableCollectionOrders.setModel(tableCollectionOrdersModel);


		}
	}

	private JPanel deliveryOrders() {
		JPanel panelDeliveryOrdersMain = new JPanel(new BorderLayout());


		tableDeliveryOrdersModel = new DefaultTableModel(new String[] {
				"Order ID", "Customer Name", "Order Time"
		}, 0);

		tableDeliveryOrders = new JTable(tableDeliveryOrdersModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth())); // Sets width of columns to fill content.


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

		tableDeliveryOrders.setFont(new Font("", 0, 14));
		tableDeliveryOrders.setRowHeight(tableDeliveryOrders.getRowHeight() + 10);
		tableDeliveryOrders.setAutoCreateRowSorter(true);
		tableDeliveryOrders.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableDeliveryOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDeliveryOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableDeliveryOrders.getSelectedRow();
					int orderId = Integer.parseInt(tableDeliveryOrders.getModel().getValueAt(row, 0).toString());
					
					//selectedDeliveryOrdersRow = row;
					
					populateDeliveryOrdersView(orderId);
					
					
					
					
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateDeliveryOrders();

		JScrollPane jsp = new JScrollPane(tableDeliveryOrders,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//tableDeliveryOrders.setRowSelectionInterval(0, 0);
		
		tableDeliveryOrders.setFillsViewportHeight(true);

		panelDeliveryOrdersMain.add(jsp, BorderLayout.CENTER);

		return panelDeliveryOrdersMain;
	}

	public static void populateDeliveryOrders() {
		int rows = tableDeliveryOrdersModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			tableDeliveryOrdersModel.removeRow(i);
		}


		Database.selectDeliveryOrders();

		for (int i = 0; i < Database.getDeliveryOrdersArray().size(); i++) {

			int orderId = Database.getDeliveryOrdersArray().get(i).getOrderId();
			String customerName = Database.getDeliveryOrdersArray().get(i).getCustomerName();
			String orderTime = Database.getDeliveryOrdersArray().get(i).getDateTime();



			Object[] data = {orderId, customerName, orderTime
			};

			tableDeliveryOrdersModel.addRow(data);
			//menuDelivery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuDelivery.getSelectionModel().setSelectionInterval(0, 0);
			//menuDelivery.setColumnSelectionInterval(0, 0);
			//menuDelivery.requestFocusInWindow();

			tableDeliveryOrders.setModel(tableDeliveryOrdersModel);


		}
	}


	private JPanel orderView() {
		JPanel panelOrderViewMain = new JPanel(new BorderLayout());

		//panelOrderViewMain.add(new JLabel("panelOrderViewMain"), BorderLayout.CENTER);

		tableOrderViewModel = new DefaultTableModel(new String[] {
				"Order ID", "Menu Item ID", "Menu Item Name", "Quantity", "Price"
		}, 0);

		tableOrderView = new JTable(tableOrderViewModel) {
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

		tableOrderView.setFont(new Font("", 0, 14));
		tableOrderView.setRowHeight(tableOrderView.getRowHeight() + 10);
		tableOrderView.setAutoCreateRowSorter(true);
		tableOrderView.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableOrderView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableOrderView.getSelectedRow();
					int orderId = Integer.parseInt(tableOrderView.getModel().getValueAt(row, 0).toString());
					populateTableOrdersView(orderId);
					// NO!


				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		//populateOrderView();

		JScrollPane jsp = new JScrollPane(tableOrderView,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tableOrderView.setFillsViewportHeight(true);
		
		TitledBorder border = new TitledBorder("Order Contents: ");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelOrderViewMain.setBorder(border);

		panelOrderViewMain.add(jsp, BorderLayout.CENTER);

		return panelOrderViewMain;
	}


	private void populateTableOrdersView(int orderId) {


		int rows = tableOrderViewModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			tableOrderViewModel.removeRow(i);
		}


			Database.selectTableOrderItems(orderId);


			for (int i = 0; i < Database.getTableOrderItemsArray().size(); i++) {

				int tableOrderId= Database.getTableOrderItemsArray().get(i).getOrderId();
				int menuItemId = Database.getTableOrderItemsArray().get(i).getMenuItemId();
				String menuItemName = Database.getTableOrderItemsArray().get(i).getMenuItemName();
				int quantity = Database.getTableOrderItemsArray().get(i).getQuantity();
				double menuItemPrice = Database.getTableOrderItemsArray().get(i).getMenuItemPrice();



				Object[] data = {tableOrderId, menuItemId, menuItemName, quantity, menuItemPrice
				};

				tableOrderViewModel.addRow(data);
				//menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				//menuTable.getSelectionModel().setSelectionInterval(0, 0);
				//menuTable.setColumnSelectionInterval(0, 0);
				//menuTable.requestFocusInWindow();

				tableOrderView.setModel(tableOrderViewModel);


			}

		
	}
	
	private void populateCollectionOrdersView(int orderId) {
		int rows = tableOrderViewModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			tableOrderViewModel.removeRow(i);
		}

		System.out.println("RUNNING POPULATE COLLECTION ORDERS VIEW");

			Database.selectCollectionOrderItem(orderId);


			for (int i = 0; i < Database.getCollectionOrderItemsArray().size(); i++) {

				int collectionOrderId = Database.getCollectionOrderItemsArray().get(i).getOrderId();
				int menuItemId = Database.getCollectionOrderItemsArray().get(i).getMenuItemId();
				String menuItemName = Database.getCollectionOrderItemsArray().get(i).getMenuItemName();
				int quantity = Database.getCollectionOrderItemsArray().get(i).getQuantity();
				double menuItemPrice = Database.getCollectionOrderItemsArray().get(i).getMenuItemPrice();

				System.out.println("HELOO!!!");
				System.out.println(collectionOrderId);
				System.out.println(menuItemId);
				System.out.println(menuItemName);
				System.out.println(quantity);
				System.out.println(menuItemPrice);

				Object[] data = {collectionOrderId, menuItemId, menuItemName, quantity, menuItemPrice
				};

				tableOrderViewModel.addRow(data);
				//menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				//menuTable.getSelectionModel().setSelectionInterval(0, 0);
				//menuTable.setColumnSelectionInterval(0, 0);
				//menuTable.requestFocusInWindow();

				tableOrderView.setModel(tableOrderViewModel);


			}
	}
	
	private void populateDeliveryOrdersView(int orderId) {


		int rows = tableOrderViewModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			tableOrderViewModel.removeRow(i);
		}


			Database.selectDeliveryOrderItem(orderId);


			for (int i = 0; i < Database.getDeliveryOrderItemsArray().size(); i++) {

				int deliveryOrderId= Database.getDeliveryOrderItemsArray().get(i).getOrderId();
				int menuItemId = Database.getDeliveryOrderItemsArray().get(i).getMenuItemId();
				String menuItemName = Database.getDeliveryOrderItemsArray().get(i).getMenuItemName();
				int quantity = Database.getDeliveryOrderItemsArray().get(i).getQuantity();
				double menuItemPrice = Database.getDeliveryOrderItemsArray().get(i).getMenuItemPrice();



				Object[] data = {deliveryOrderId, menuItemId, menuItemName, quantity, menuItemPrice
				};

				tableOrderViewModel.addRow(data);
				//menuDelivery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				//menuDelivery.getSelectionModel().setSelectionInterval(0, 0);
				//menuDelivery.setColumnSelectionInterval(0, 0);
				//menuDelivery.requestFocusInWindow();

				tableOrderView.setModel(tableOrderViewModel);


			}

		
	}


	private JPanel orderDetails() {
		JPanel panelOrderDetailsMain = new JPanel(new BorderLayout());
		JPanel panelOrderDetails = new JPanel(new GridBagLayout());

		
		textFieldOrderType = new JTextField(20);
		textFieldTableName = new JTextField(20);
		textFieldCustomerName = new JTextField(20);
		textFieldCustomerHouseNumber = new JTextField(20);
		textFieldCustomerAddress = new JTextField(20);
		textFieldCustomerCity = new JTextField(20);
		textFieldCustomerPostcode = new JTextField(20);
		textFieldCustomerPhoneNumber = new JTextField(20);
		textFieldTotalPrice = new JTextField(20);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelOrderDetails.add(new JLabel("Assigned Staff: "), gbc);
		
		gbc.gridy++;
		panelOrderDetails.add(new JLabel("Total Price:"), gbc);
		
		panelOrderDetailsMain.add(panelOrderDetails, BorderLayout.CENTER);
		panelOrderDetailsMain.add(orderDetailsSouthControls(), BorderLayout.SOUTH);
		
		return panelOrderDetailsMain;
	}
	
	public JPanel orderDetailsSouthControls() {
		//JPanel panelOrderDetailsSouthControlsMain = new JPanel(new BorderLayout());
		JPanel panelOrderDetailsSouthControls = new JPanel(new GridBagLayout());
		
		JButton buttonCustomerInfo = new JButton("Info & Directions");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelOrderDetailsSouthControls.add(new JLabel(),  gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelOrderDetailsSouthControls.add(buttonCustomerInfo, gbc);
		
		
		return panelOrderDetailsSouthControls;
	}

	private void setSelectedTableOrdersRow() {
		tableTableOrders.getSelectionModel().setSelectionInterval(selectedTableOrdersRow, selectedTableOrdersRow);
	}
	
	private int getSelectedTableOrdersRow() {
		int row = tableTableOrders.getSelectedRow();
		return row;
	}
	
	private int getSelectedCollectionOrdersRow() {
		int row = tableCollectionOrders.getSelectedRow();
		return row;
	}
	
	private int getSelectedDeliveryOrdersRow() {
		int row = tableDeliveryOrders.getSelectedRow();
		return row;
	}
	
	private int getSelectedTableOrdersOrderId(int selectedRow) {
		int orderId = Integer.parseInt(tableTableOrders.getModel().getValueAt(selectedRow, 0).toString());
		return orderId;
	}
	
	private int getSelectedCollectionOrdersOrderId(int selectedRow) {
		int orderId = Integer.parseInt(tableCollectionOrders.getModel().getValueAt(selectedRow, 0).toString());
		return orderId;
	}
	
	private int getSelectedDeliveryOrdersOrderId(int selectedRow) {
		int orderId = Integer.parseInt(tableDeliveryOrders.getModel().getValueAt(selectedRow, 0).toString());
		return orderId;
	}


	
	

	

	public JPanel getOrdersPanel() {
		return panelOrdersMain;
	}

}
