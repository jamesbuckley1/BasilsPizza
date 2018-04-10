package basilspizza;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class NewOrderGUI {

	private JPanel panelNewOrdersMain;
	private static JList customerList;
	private static DefaultListModel customerListModel;

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
		//panelNewOrdersLeftSideGrid.add(orderSummary());

		//panelNewOrdersRightSideGrid.add(allOrdersTable());


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

		panelSelectOrderTypeMain.add(tabbedPane);

		return panelSelectOrderTypeMain;

	}
	

	public JPanel seatedOrderPane() {
		JPanel panelSeatedOrderMain = new JPanel(new BorderLayout());
		JPanel panelSeatedOrder = new JPanel(new GridBagLayout());


		JButton buttonAddToOrder = new JButton("Add to Order");

		JTextArea textAreaTables = new JTextArea();
		textAreaTables.setLineWrap(true);

		JScrollPane jsp = new JScrollPane(textAreaTables);

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
		
		JButton buttonAddToOrder = new JButton("Select Table");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelSeatedOrderSouthControls.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelSeatedOrderSouthControls.add(buttonAddToOrder, gbc);
		
		
		return panelSeatedOrderSouthControls;
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

		panelSelectMenu.add(tabbedPane, BorderLayout.CENTER);

		return panelSelectMenu;
	}

	public JPanel pizzaMenu() {
		JPanel panelPizzaMenu = new JPanel(new BorderLayout());


		return panelPizzaMenu;
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



	public JPanel getOrdersPanel() {
		return panelNewOrdersMain;
	}



}
