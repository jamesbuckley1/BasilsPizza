package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class MenuGUI {

	JPanel panelMenuMain;
	private JTable menuTable;
	private DefaultTableModel pizzaMenuTableModel, sidesMenuTableModel, drinksMenuTableModel, dessertsMenuTableModel;
	
	private JComboBox comboboxMenuItemType;
	private JTextField textFieldMenuItemName, textFieldMenuItemPrice;

	public MenuGUI() {

		initMenuTable();

		panelMenuMain = new JPanel(new BorderLayout());
		

		panelMenuMain.add(selectMenu(), BorderLayout.CENTER);
		panelMenuMain.add(menuSouthControls(), BorderLayout.SOUTH);


	}

	public void initMenuTable() {
		
		menuTable = new JTable(sidesMenuTableModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

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
		
		menuTable.setFont(new Font("", 0, 14));
		menuTable.setRowHeight(menuTable.getRowHeight() + 10);
		menuTable.setAutoCreateRowSorter(true);
		menuTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		menuTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = menuTable.getSelectedRow();
					String houseNumber = menuTable.getModel().getValueAt(row, 2).toString();
					String address = menuTable.getModel().getValueAt(row, 3).toString();
					String city = menuTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});
		
		

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

		pizzaMenuTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price"
		}, 0);

		JTable pizzaMenuTable = new JTable(pizzaMenuTableModel) { // Needs it's own JTable otherwise it doesn't work.
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

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

		


		populatePizzaMenuTable();




		JScrollPane jsp = new JScrollPane(pizzaMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		pizzaMenuTable.setFillsViewportHeight(true);

		panelPizzaMenu.add(jsp, BorderLayout.CENTER);


		return panelPizzaMenu;
	}



	public void populatePizzaMenuTable() {
		int rows = pizzaMenuTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			pizzaMenuTableModel.removeRow(i);
		}

		Database.selectMenuItems("Pizza");

		for (int i = 0; i < Database.getMenuItemArray().size(); i++) {

			String itemName = Database.getMenuItemArray().get(i).getItemName();
			double itemPrice = Database.getMenuItemArray().get(i).getItemPrice();



			Object[] data = {itemName, itemPrice
			};

			pizzaMenuTableModel.addRow(data);
			//menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuTable.getSelectionModel().setSelectionInterval(0, 0);
			//menuTable.setColumnSelectionInterval(0, 0);
			//menuTable.requestFocusInWindow();
		}
	}





	public JPanel sidesMenu() {
		JPanel panelSidesMenu = new JPanel(new BorderLayout());

		sidesMenuTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price"
		}, 0);



		populateSidesMenuTable();

		

		
		
		menuTable.setModel(sidesMenuTableModel);


		JScrollPane jsp = new JScrollPane(menuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);




		panelSidesMenu.add(jsp, BorderLayout.CENTER);


		return panelSidesMenu;
	}

	public void populateSidesMenuTable() {


		int rows = sidesMenuTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			sidesMenuTableModel.removeRow(i);
		}

		Database.selectMenuItems("Sides");

		for (int i = 0; i < Database.getMenuItemArray().size(); i++) {

			String itemName = Database.getMenuItemArray().get(i).getItemName();
			double itemPrice = Database.getMenuItemArray().get(i).getItemPrice();



			Object[] data = {itemName, itemPrice
			};

			sidesMenuTableModel.addRow(data);
			//sidesMenuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//sidesMenuTable.getSelectionModel().setSelectionInterval(0, 0);
			//sidesMenuTable.setColumnSelectionInterval(0, 0);
			//sidesMenuTable.requestFocusInWindow();
		}
	}

	public JPanel drinksMenu() {
		JPanel panelDrinksMenu = new JPanel(new BorderLayout());

		drinksMenuTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price"
		}, 0);

		JTable drinksMenuTable = new JTable(drinksMenuTableModel) { // Needs it's own JTable otherwise it doesn't work.
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

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

				try {
					int row = drinksMenuTable.getSelectedRow();
					String houseNumber = drinksMenuTable.getModel().getValueAt(row, 2).toString();
					String address = drinksMenuTable.getModel().getValueAt(row, 3).toString();
					String city = drinksMenuTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		


		populateDrinksMenuTable();




		JScrollPane jsp = new JScrollPane(drinksMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		drinksMenuTable.setFillsViewportHeight(true);

		panelDrinksMenu.add(jsp, BorderLayout.CENTER);


		return panelDrinksMenu;
	}



	public void populateDrinksMenuTable() {
		int rows = drinksMenuTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			drinksMenuTableModel.removeRow(i);
		}

		Database.selectMenuItems("Drinks");

		for (int i = 0; i < Database.getMenuItemArray().size(); i++) {

			String itemName = Database.getMenuItemArray().get(i).getItemName();
			double itemPrice = Database.getMenuItemArray().get(i).getItemPrice();



			Object[] data = {itemName, itemPrice
			};

			drinksMenuTableModel.addRow(data);
			//menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuTable.getSelectionModel().setSelectionInterval(0, 0);
			//menuTable.setColumnSelectionInterval(0, 0);
			//menuTable.requestFocusInWindow();
		}
	}
	
	
	public JPanel dessertsMenu() {
		JPanel panelDessertsMenu = new JPanel(new BorderLayout());

		dessertsMenuTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price"
		}, 0);

		JTable dessertsMenuTable = new JTable(dessertsMenuTableModel) { // Needs it's own JTable otherwise it doesn't work.
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);

				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

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

				try {
					int row = dessertsMenuTable.getSelectedRow();
					String houseNumber = dessertsMenuTable.getModel().getValueAt(row, 2).toString();
					String address = dessertsMenuTable.getModel().getValueAt(row, 3).toString();
					String city = dessertsMenuTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		


		populateDessertsMenuTable();




		JScrollPane jsp = new JScrollPane(dessertsMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		dessertsMenuTable.setFillsViewportHeight(true);

		panelDessertsMenu.add(jsp, BorderLayout.CENTER);


		return panelDessertsMenu;
	}



	public void populateDessertsMenuTable() {
		int rows = dessertsMenuTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			dessertsMenuTableModel.removeRow(i);
		}

		Database.selectMenuItems("Desserts");

		for (int i = 0; i < Database.getMenuItemArray().size(); i++) {

			String itemName = Database.getMenuItemArray().get(i).getItemName();
			double itemPrice = Database.getMenuItemArray().get(i).getItemPrice();



			Object[] data = {itemName, itemPrice
			};

			dessertsMenuTableModel.addRow(data);
			//menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//menuTable.getSelectionModel().setSelectionInterval(0, 0);
			//menuTable.setColumnSelectionInterval(0, 0);
			//menuTable.requestFocusInWindow();
		}
	}
	
	public JPanel menuSouthControls() {
		JPanel panelMenuButtons = new JPanel(new GridBagLayout());
		
		JButton buttonAddMenuItem = new JButton("Add Menu Item");
		buttonAddMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddMenuItemDialogGUI();
			}
		});
		
		JButton buttonDeleteMenuItem = new JButton("Delete");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		// SPACING
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelMenuButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelMenuButtons.add(buttonDeleteMenuItem, gbc);
		
		gbc.gridx++;
		
		panelMenuButtons.add(buttonAddMenuItem, gbc);
		
		
		
		return panelMenuButtons;
	}

	public String getSelectedPizzaMenuItem() {



		String pizzaMenuItemName;

		int column = 0;
		int row = menuTable.getSelectedRow();
		pizzaMenuItemName = menuTable.getModel().getValueAt(row, 0).toString();

		System.out.println("SELECTED ROW sff" + pizzaMenuItemName);

		return pizzaMenuItemName;
	}

	public DefaultTableModel getPizzaMenuTableModel() {
		return pizzaMenuTableModel;
	}

	public DefaultTableModel getSidesMenuTableModel() {
		return sidesMenuTableModel;
	}
	
	public DefaultTableModel getDrinksMenuTableModel() {
		return drinksMenuTableModel;
	}
	
	public DefaultTableModel getDessertsMenuTableModel() {
		return dessertsMenuTableModel;
	}

	public JPanel getMenuPanel() {
		return panelMenuMain;
	}

}
