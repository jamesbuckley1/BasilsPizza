package ordersystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ViewCustomerOrderDialogGUI {
	
	JDialog dialogOrder;
	JFrame frame;
	
	private int orderId;
	
	private JTable tableOrder;
	private DefaultTableModel orderTableModel;
	
	private JTextField textFieldTotalPrice;
	
	public ViewCustomerOrderDialogGUI(int orderId) {
		this.orderId = orderId;
		initGUI();
	}
	
	private void initGUI() {
		dialogOrder = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		
		textFieldTotalPrice = new JTextField(10);
		
		orderTableModel = new DefaultTableModel(new String[] {
				"Order ID", "Menu Item ID", "Menu Item Name", "Quantity", "Price"
		}, 0);
		
		
		
		
		tableOrder = new JTable(orderTableModel) {

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			
			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Alternate row colours for readability.
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

		tableOrder.setFont(new Font("", 0, 14));
		tableOrder.setRowHeight(tableOrder.getRowHeight() + 10);
		tableOrder.setAutoCreateRowSorter(true);
		tableOrder.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// THIS PROBABLY WON'T BE NEEDED.
		tableOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editCustomer(frame?)
				}

				try {
					int row = tableOrder.getSelectedRow();
					//String houseNumber = customerOrdersTable.getModel().getValueAt(row, 2).toString();
					//String address = customersTable.getModel().getValueAt(row, 3).toString();
					//String city = customersTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to delete.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateCustomerOrdersTable(orderId);

		JScrollPane jsp = new JScrollPane(tableOrder,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		tableOrder.setFillsViewportHeight(true);
		
		
		
		
		
		panelMain.add(jsp, BorderLayout.CENTER);
		panelMain.add(orderTableSouthControls(), BorderLayout.SOUTH);
		
		dialogOrder.add(panelMain);
		
		//dialogOrder.setModal(true); 
		dialogOrder.pack();
		dialogOrder.setLocationRelativeTo(frame);
		dialogOrder.setVisible(true);
	}
	
	private JPanel orderTableSouthControls() {
		JPanel panelSouth = new JPanel(new GridBagLayout());
		
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelSouth.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelSouth.add(new JLabel("Total Price: "), gbc);
		
		gbc.gridx++;
		panelSouth.add(textFieldTotalPrice, gbc);
		
		return panelSouth;
	}
	
	private void populateCustomerOrdersTable(int orderId) {
		int rows = orderTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			orderTableModel.removeRow(i);
		}


		Database.selectDeliveryOrderItem(orderId);

		double totalPrice = 0;
		

		for (int i = 0; i < Database.getDeliveryOrderItemsArray().size(); i++) {

			int deliveryOrderId= Database.getDeliveryOrderItemsArray().get(i).getOrderId();
			int menuItemId = Database.getDeliveryOrderItemsArray().get(i).getMenuItemId();
			String menuItemName = Database.getDeliveryOrderItemsArray().get(i).getMenuItemName();
			int quantity = Database.getDeliveryOrderItemsArray().get(i).getQuantity();
			double menuItemPrice = Database.getDeliveryOrderItemsArray().get(i).getMenuItemPrice();

			//System.out.println("RETURNED CUSTOMER ID = " + customerId);

			totalPrice += menuItemPrice * quantity;

			Object[] data = {deliveryOrderId, menuItemId, menuItemName, quantity, menuItemPrice
			};

			orderTableModel.addRow(data);
			//menuDelivery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuDelivery.getSelectionModel().setSelectionInterval(0, 0);
			//menuDelivery.setColumnSelectionInterval(0, 0);
			//menuDelivery.requestFocusInWindow();

			


		}
		
		textFieldTotalPrice.setText(String.valueOf(totalPrice));
		dialogOrder.setTitle("Order Details");
		tableOrder.setModel(orderTableModel);

	}
	
	

}
