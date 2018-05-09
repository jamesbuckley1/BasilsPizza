package ordersystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ClosedOrdersGUI {
	
	JPanel panelMain;
	static DefaultTableModel closedTableOrdersTableModel;
	DefaultTableModel closedCollectionOrdersTableModel;
	DefaultTableModel closedDeliveryOrdersTableModel;
	static JTable closedTableOrdersTable;
	JTable closedCollectionOrdersTable;
	JTable closedDeliveryOrdersTable;
	
	public ClosedOrdersGUI() {
		initGUI();
	}
	
	private void initGUI() {
		panelMain = new JPanel(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Table", closedTableOrders());
		tabbedPane.add("Collection", closedCollectionOrders());
		tabbedPane.add("Delivery", closedDeliveryOrders());
		
		panelMain.add(tabbedPane, BorderLayout.CENTER);
		
		
		
		
	}
	
	private JPanel closedTableOrders() {
		
		JPanel panelClosedTableOrders = new JPanel(new BorderLayout());
		closedTableOrdersTableModel = new DefaultTableModel(new String[]{"Order ID", "Table ID", "Order Time"}, 0);

		closedTableOrdersTable = new JTable(closedTableOrdersTableModel) {

			// Disables editing of table
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

		//closedTableOrdersTable font and cell height
		closedTableOrdersTable.setFont(new Font("", 0, 14));
		closedTableOrdersTable.setRowHeight(closedTableOrdersTable.getRowHeight() + 10);
		closedTableOrdersTable.setAutoCreateRowSorter(true);
		closedTableOrdersTable.setFillsViewportHeight(true);

		// ClosedOrders table mouse listener
		closedTableOrdersTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editItem(); // OPEN EDIT ITEM DIALOG ON DOUBLE CLICK
				}
			}
		});

		// Add closedOrders items to closedOrders table
		populateClosedTableOrdersTable();	



		JScrollPane jsp = new JScrollPane(closedTableOrdersTable);

		// Add scroll pane and button panel to closedOrders table panel
		panelClosedTableOrders.add(jsp, BorderLayout.CENTER);
		panelClosedTableOrders.add(closedOrdersSouthControls(), BorderLayout.SOUTH);

		// Add closedOrders table panel to main panel
		//panelClosedOrdersMain.add(panelClosedOrdersTable, BorderLayout.CENTER);
		
		return panelClosedTableOrders;
	}
	
	private JPanel closedOrdersSouthControls() {
		JPanel panelClosedOrderButtons = new JPanel(new GridBagLayout());
		
		JButton buttonReopenOrder = new JButton("Reopen Order");
		buttonReopenOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Database.reopenOrder(getSelectedOrderId)
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelClosedOrderButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelClosedOrderButtons.add(buttonReopenOrder, gbc);
		
		
		return panelClosedOrderButtons;
	}
	
	public static void populateClosedTableOrdersTable() {
		int rows = closedTableOrdersTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			closedTableOrdersTableModel.removeRow(i);
		}

		if (!Database.getTableOrdersArray().isEmpty()) {
		Database.selectClosedTableOrders(getSelectedTableOrderId(getSelectedClosedTableOrderRow()));

		// Loop through result set ArrayList and adds to new array which can be used by TableModel
		for (int i = 0; i < Database.getTableOrdersArray().size(); i++) {
			int orderId = Database.getTableOrdersArray().get(i).getOrderId();
			String tableId = Database.getTableOrdersArray().get(i).getTableName(); 
			String dateTime = Database.getTableOrdersArray().get(i).getDateTime();

			Object[] data = {orderId, tableId, dateTime};

			closedTableOrdersTableModel.addRow(data);
		}
		}
	}
	
	private JPanel closedCollectionOrders() {
		JPanel panelClosedCollectionOrders = new JPanel(new BorderLayout());
		closedCollectionOrdersTableModel = new DefaultTableModel(new String[]{"Order ID", "Customer Name", "Order Time"}, 0);

		closedCollectionOrdersTable = new JTable(closedCollectionOrdersTableModel) {

			// Disables editing of collection
			public boolean isCellEdicollection(int row, int columns) {
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

		//closedCollectionOrdersCollection font and cell height
		closedCollectionOrdersTable.setFont(new Font("", 0, 14));
		closedCollectionOrdersTable.setRowHeight(closedCollectionOrdersTable.getRowHeight() + 10);
		closedCollectionOrdersTable.setAutoCreateRowSorter(true);
		closedCollectionOrdersTable.setFillsViewportHeight(true);

		// ClosedOrders collection mouse listener
		closedCollectionOrdersTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editItem(); // OPEN EDIT ITEM DIALOG ON DOUBLE CLICK
				}
			}
		});

		// Add closedOrders items to closedOrders collection
		populateClosedCollectionOrdersTable();	



		JScrollPane jsp = new JScrollPane(closedCollectionOrdersTable);

		// Add scroll pane and button panel to closedOrders collection panel
		panelClosedCollectionOrders.add(jsp, BorderLayout.CENTER);
		panelClosedCollectionOrders.add(closedOrdersSouthControls(), BorderLayout.SOUTH);

		// Add closedOrders collection panel to main panel
		//panelClosedOrdersMain.add(panelClosedOrdersCollection, BorderLayout.CENTER);
		
		return panelClosedCollectionOrders;
	}
	
	private void populateClosedCollectionOrdersTable() {
		int rows = closedTableOrdersTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			closedTableOrdersTableModel.removeRow(i);
		}

		//Database.selectClosedTableOrders(getSelectedCollectionOrderId(getSelectedCollectionTableRow));

		// Loop through result set ArrayList and adds to new array which can be used by TableModel
		for (int i = 0; i < Database.getTableOrdersArray().size(); i++) {
			int orderId = Database.getTableOrdersArray().get(i).getOrderId();
			String tableId = Database.getTableOrdersArray().get(i).getTableName(); 
			String dateTime = Database.getTableOrdersArray().get(i).getDateTime();

			Object[] data = {orderId, tableId, dateTime};

			closedTableOrdersTableModel.addRow(data);
		}
	}
	
	private JPanel closedDeliveryOrders() {
		JPanel panelClosedDeliveryOrders = new JPanel(new BorderLayout());
		closedDeliveryOrdersTableModel = new DefaultTableModel(new String[]{"Order ID", "Delivery ID", "Order Time"}, 0);

		closedDeliveryOrdersTable = new JTable(closedDeliveryOrdersTableModel) {

			// Disables editing of delivery
			public boolean isCellEdidelivery(int row, int columns) {
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

		//closedDeliveryOrdersDelivery font and cell height
		closedDeliveryOrdersTable.setFont(new Font("", 0, 14));
		closedDeliveryOrdersTable.setRowHeight(closedDeliveryOrdersTable.getRowHeight() + 10);
		closedDeliveryOrdersTable.setAutoCreateRowSorter(true);
		closedDeliveryOrdersTable.setFillsViewportHeight(true);

		// ClosedOrders delivery mouse listener
		closedDeliveryOrdersTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editItem(); // OPEN EDIT ITEM DIALOG ON DOUBLE CLICK
				}
			}
		});

		// Add closedOrders items to closedOrders delivery
		populateClosedDeliveryOrdersTable();	



		JScrollPane jsp = new JScrollPane(closedDeliveryOrdersTable);

		// Add scroll pane and button panel to closedOrders delivery panel
		panelClosedDeliveryOrders.add(jsp, BorderLayout.CENTER);
		panelClosedDeliveryOrders.add(closedOrdersSouthControls(), BorderLayout.SOUTH);

		// Add closedOrders delivery panel to main panel
		//panelClosedOrdersMain.add(panelClosedOrdersDelivery, BorderLayout.CENTER);
		
		return panelClosedDeliveryOrders;
	}
	
	private void populateClosedDeliveryOrdersTable() {
		
	}
	
	private static int getSelectedClosedTableOrderRow() {
		int row = closedTableOrdersTable.getSelectedRow();
		return row;
	}
	
	private static int getSelectedTableOrderId(int selectedRow) {
		//int row = closedTableOrdersTable.getSelectedRow();
		
		int orderId = Integer.parseInt(closedTableOrdersTable.getModel().getValueAt(selectedRow, 0).toString());
		return orderId;
		
	}
	
	public JPanel getClosedOrdersPanel() {
		return panelMain;
	}

}
