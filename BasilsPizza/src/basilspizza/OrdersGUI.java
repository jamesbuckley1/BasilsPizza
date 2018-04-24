package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class OrdersGUI {

	private JPanel panelOrdersMain;

	private JTable tableTableOrders, tableCollectionOrders, tableDeliveryOrders;
	private DefaultTableModel tableTableOrdersModel, tableCollectionOrdersModel, tableDeliveryOrdersModel;

	private DefaultTableModel tableOrderViewModel;

	private JTable tableOrderView;

	private int selectedTableOrdersRow, selectedCollectionOrdersRow, selectedDeliveryOrdersRow;

	public OrdersGUI() {
		initGUI();
		startRefreshTablesTimer();
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

	public void startRefreshTablesTimer() {
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Populating orders tables");
				populateTableOrders();
				setSelectedTableOrdersRow(); // Run this method so user does not lose selected item.
				populateCollectionOrders();
				setSelectedCollectionOrdersRow();
				//populateDeliveryOrders();
				//setSelectedDeliveryOrdersRow();

			}
		});

		timer.start();
	}


	public JPanel selectOrder() {
		JPanel panelSelectOrderMain = new JPanel(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Table", tableOrders());
		tabbedPane.add("Collection", collectionOrders());
		tabbedPane.add("Delivery", deliveryOrders());

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
		tableTableOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableTableOrders.getSelectedRow();
					int orderId = Integer.parseInt(tableTableOrders.getModel().getValueAt(row, 0).toString());
					populateTableOrderView(orderId);
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

		tableTableOrders.setFillsViewportHeight(true);

		panelTableOrdersMain.add(jsp, BorderLayout.CENTER);


		return panelTableOrdersMain;
	}

	private void populateTableOrders() {
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
		tableCollectionOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableCollectionOrders.getSelectedRow();
					int orderId = Integer.parseInt(tableCollectionOrders.getModel().getValueAt(row, 0).toString());
					
					selectedCollectionOrdersRow = row;
					
					populateCollectionOrders();
					
					tableOrderView.setModel(tableCollectionOrdersModel);
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

		tableCollectionOrders.setFillsViewportHeight(true);

		panelCollectionOrdersMain.add(jsp, BorderLayout.CENTER);

		return panelCollectionOrdersMain;
	}

	private void populateCollectionOrders() {
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

		panelDeliveryOrdersMain.add(new JLabel("panelDeliveryOrdersMain"), BorderLayout.CENTER);

		return panelDeliveryOrdersMain;
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
					populateTableOrderView(orderId);
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

		panelOrderViewMain.add(jsp, BorderLayout.CENTER);

		return panelOrderViewMain;
	}


	private void populateTableOrderView(int orderId) {


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


	private JPanel orderDetails() {
		JPanel panelOrderDetails = new JPanel(new BorderLayout());

		panelOrderDetails.add(new JLabel("panelOrderDetails"), BorderLayout.CENTER);

		return panelOrderDetails;
	}

	private void setSelectedTableOrdersRow() {
		tableTableOrders.getSelectionModel().setSelectionInterval(selectedTableOrdersRow, selectedTableOrdersRow);
	}

	private void setSelectedCollectionOrdersRow() {
		tableCollectionOrders.getSelectionModel().setSelectionInterval(selectedCollectionOrdersRow, selectedCollectionOrdersRow);
	}

	private void setSelectedDeliveryOrdersRow() {
		tableDeliveryOrders.getSelectionModel().setSelectionInterval(selectedDeliveryOrdersRow, selectedDeliveryOrdersRow);
	}

	public JPanel getOrdersPanel() {
		return panelOrdersMain;
	}

}
