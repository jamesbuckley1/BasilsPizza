package basilspizza;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
		
		panelNewOrdersLeftSideGrid.add(selectCustomer());
		panelNewOrdersLeftSideGrid.add(selectMenu());
		//panelNewOrdersLeftSideGrid.add(orderSummary());
		
		//panelNewOrdersRightSideGrid.add(allOrdersTable());
		
		
		panelNewOrdersMainGrid.add(panelNewOrdersLeftSideGrid);
		panelNewOrdersMainGrid.add(panelNewOrdersRightSideGrid);
		
		panelNewOrdersMain.add(panelNewOrdersMainGrid, BorderLayout.CENTER);
		
	}
	
	public JPanel selectCustomer() {
		JPanel panelCustomerSelectMain = new JPanel(new BorderLayout());
		JPanel panelCustomerSelect = new JPanel(new GridBagLayout());
		
		JComboBox comboboxOrderType = new JComboBox();
		JTextField textFieldCustomerSearch = new JTextField(15);
		JButton buttonCustomerSearch = new JButton("Search");
		JButton buttonNewCustomer = new JButton("New Customer");
		JButton buttonMapInfo = new JButton("Map/Info");
		JButton buttonAddCustomer = new JButton("Add to Order");
		
		customerList = new JList();
		customerListModel = new DefaultListModel();
		JScrollPane jsp = new JScrollPane(customerList);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0.5;
		panelCustomerSelect.add(new JLabel("Order Type: "), gbc);
		
		gbc.gridx++;
		gbc.gridwidth = 3;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelCustomerSelect.add(comboboxOrderType, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weighty = 0.5;
		panelCustomerSelect.add(new JLabel(), gbc);
		
		//gbc.gridx++;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.LINE_END;
		//gbc.insets = new Insets(0, 10, 0, 10);
		panelCustomerSelect.add(new JLabel("Customers: "), gbc);
		
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelCustomerSelect.add(textFieldCustomerSearch, gbc);
		
		gbc.gridx++;
		panelCustomerSelect.add(buttonCustomerSearch, gbc);
		
		
		
		gbc.gridx++;
		panelCustomerSelect.add(buttonNewCustomer, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panelCustomerSelect.add(jsp, gbc);
		
		
		
		gbc.gridx = 2;
		gbc.gridy++;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_END;
		//gbc.insets = new Insets(0, 10, 0, 10);
		panelCustomerSelect.add(buttonMapInfo, gbc);
		
		gbc.gridx++;
		gbc.weightx = 0.5;
		panelCustomerSelect.add(buttonAddCustomer, gbc);
		
		panelCustomerSelect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panelCustomerSelectMain.add(panelCustomerSelect, BorderLayout.CENTER);
		
		
		TitledBorder border = new TitledBorder("Order Type & Customer: ");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelCustomerSelectMain.setBorder(border);
		
	
		return panelCustomerSelectMain;
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
