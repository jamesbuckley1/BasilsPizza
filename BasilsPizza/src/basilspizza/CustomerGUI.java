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
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class CustomerGUI {
	private static JFrame frame;
	private static final int SQLITE_CONSTRAINT_PRIMARYKEY = 19; // SQLite error code.
	private static DefaultTableModel customersTableModel, customersOrdersTableModel;
	private static JTable customersTable, customersOrdersTable;
	
	private JPanel panelCustomersMain;
	private JTextField textFieldCustomerFirstName, textFieldCustomerLastName,
	textFieldCustomerHouseNumber, textFieldCustomerAddress,
	textFieldCustomerCity, textFieldCustomerPostcode,
	textFieldCustomerPhoneNumber, textFieldCustomersSearch;
	private GridBagConstraints gbc;

	public CustomerGUI() {
		initGUI();
	}

	// Set up main GUI components.
	private void initGUI() {
		// Check if running on event dispatch thread.
		if (SwingUtilities.isEventDispatchThread()) { 
			System.err.println("CustomerGUI running on EDT");
		} else {
			System.err.println("CustomerGUI not running on EDT");
		}

		panelCustomersMain = new JPanel(new BorderLayout());
		JPanel panelCustomersMainGrid = new JPanel(new GridLayout(1, 2)); // Splits window into 2 columns.
		JPanel panelCustomersFormOrdersGrid = new JPanel(new GridLayout(2, 1)); // Panel for add new customer form and customer orders table.

		gbc = new GridBagConstraints();
		
		panelCustomersMainGrid.add(customersTable());

		panelCustomersFormOrdersGrid.add(customersForm());
		panelCustomersFormOrdersGrid.add(customersOrders()); 

		panelCustomersMainGrid.add(panelCustomersFormOrdersGrid);
		
		panelCustomersMain.add(panelCustomersMainGrid, BorderLayout.CENTER);
	}

	// Set up customers table.
	private JPanel customersTable() {
		
		JPanel panelCustomersTable = new JPanel(new BorderLayout());

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

		customersTable.setFont(new Font("", 0, 14));
		customersTable.setRowHeight(customersTable.getRowHeight() + 10);
		customersTable.setAutoCreateRowSorter(true);
		customersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		customersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = customersTable.getSelectedRow();
					String houseNumber = customersTable.getModel().getValueAt(row, 2).toString();
					String address = customersTable.getModel().getValueAt(row, 3).toString();
					String city = customersTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateCustomersTable();

		JScrollPane jsp = new JScrollPane(customersTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		customersTable.setFillsViewportHeight(true);

		panelCustomersTable.add(jsp, BorderLayout.CENTER);
		panelCustomersTable.add(customersTableButtons(), BorderLayout.SOUTH);
		panelCustomersTable.add(customersTableSearch(), BorderLayout.NORTH);

		TitledBorder border = new TitledBorder("Search Customers:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelCustomersTable.setBorder(border);

		return panelCustomersTable;
	}

	// Set up buttons for customers table.
	private JPanel customersTableButtons() {
		
		JPanel panelCustomersTableButtons = new JPanel(new GridBagLayout());
		
		JButton customerInfoBtn = new JButton();
		customerInfoBtn.setText("Info & Directions");
		customerInfoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				new CustomerInfoDialogGUI(frame, getSelectedCellValues());

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
				try {
					//CONFIRMATION DIALOG w/ manager password?
					Database.deleteCustomer(getSelectedCellValues());
					populateCustomersTable();
				} catch (SQLException e) {
					// NEEDS ERROR DIALOG
					e.printStackTrace();
				}
			}
		});

		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersTableButtons.add(new JLabel(), gbc);

		// BUTTONS
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelCustomersTableButtons.add(customerInfoBtn, gbc);

		gbc.gridx++;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersTableButtons.add(editCustomerBtn, gbc);

		gbc.gridx++;
		panelCustomersTableButtons.add(deleteCustomerBtn, gbc);

		return panelCustomersTableButtons;
	}

	private JPanel customersTableSearch() {
		
		JPanel panelCustomersTableSearch = new JPanel(new GridBagLayout());
		
		// Clear Button
		JButton searchClearBtn = new JButton();
		searchClearBtn.setText("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldCustomersSearch.setText("");
			}
		});
		
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(customersTable.getModel());
		customersTable.setRowSorter(rowSorter);

		textFieldCustomersSearch = new JTextField(20);
		textFieldCustomersSearch.getDocument().addDocumentListener(new DocumentListener() {
			// Set up search filter function
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textFieldCustomersSearch.getText();

				if(text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = textFieldCustomersSearch.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Exception");
			}

		});


		// SPACING
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersTableSearch.add(new JLabel(), gbc);

		// TEXT FIELD
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersTableSearch.add(textFieldCustomersSearch, gbc);

		// BUTTON
		gbc.gridx++;
		panelCustomersTableSearch.add(searchClearBtn, gbc);

		return panelCustomersTableSearch;
	}

	// Set up customer form
	public JPanel customersForm() {
		
		JPanel panelCustomersForm = new JPanel(new GridBagLayout());
		
		// Action listener to add a new customer. Used by "Add" button and also when user presses enter.
		Action addCustomerAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String firstName = textFieldCustomerFirstName.getText();
				String lastName = textFieldCustomerLastName.getText();
				String houseNumber = textFieldCustomerHouseNumber.getText();
				String address = textFieldCustomerAddress.getText();
				String city = textFieldCustomerCity.getText();
				String postcode = textFieldCustomerPostcode.getText();
				String phoneNumber = textFieldCustomerPhoneNumber.getText();

				// Create the customer object using values from text fields.
				Customer c = new Customer(firstName, lastName, houseNumber,
						address, city, postcode, phoneNumber);
				
				// Create the customer map object using the customer object.
				CustomerMap cm = new CustomerMap(c);

				// Customer form validation
				try {
					if (!c.validateFirstName()) {
						showError("Invalid first name.");
					} else if (!c.validateLastName()) {
						showError("Invalid last name.");
					} else if (!c.validateHouseNumber()) {
						showError("Invalid house number.");
					} else if (!c.validateAddress()) {
						showError("Invalid address.");
					} else if (!c.validateCity()) {
						showError("Invalid city.");
					} else if (!c.validatePostcode()) { 
						showError("Invalid postcode.");
					} else if (!c.validatePhoneNumber()) {
						showError("Invalid phone number.");
					} else {
						Thread t = new Thread() {
							public void run() {
								try {
									c.addCustomerToDatabase();
									// Do this on EDT.
									SwingUtilities.invokeLater(new Runnable() {
										public void run() {
											populateCustomersTable();
											clearCustomerForm();
										}
									});
									
									cm.getDirectionsData();
									cm.getStaticMapImage();
									
								} catch (SQLException e) {
									if (e.getErrorCode() == SQLITE_CONSTRAINT_PRIMARYKEY) {
										showError("Duplicate entry - Customer already exists.");
										e.printStackTrace();
									}
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						};
						t.start();
					}
				} catch (Exception e2) {
					showError("Error.");
					e2.printStackTrace();
				}
			}
		};
		
		JButton addCustomerBtn = new JButton("Add");
		addCustomerBtn.addActionListener(addCustomerAction);

		JLabel lblCustomerFirstName = new JLabel("First name: ");
		JLabel lblCustomerLastName = new JLabel("Last name: ");
		JLabel lblCustomerHouseNumber = new JLabel("House number: ");
		JLabel lblCustomerAddress = new JLabel("Address: ");
		JLabel lblCustomerCity = new JLabel("City: ");
		JLabel lblCustomerPostcode = new JLabel("Postcode: ");
		JLabel lblCustomerPhoneNumber = new JLabel("Phone number: ");

		textFieldCustomerFirstName = new JTextField(20);
		textFieldCustomerFirstName.addActionListener(addCustomerAction);

		textFieldCustomerLastName = new JTextField(20);
		textFieldCustomerLastName.addActionListener(addCustomerAction);

		textFieldCustomerHouseNumber = new JTextField(5);
		textFieldCustomerHouseNumber.addActionListener(addCustomerAction);

		textFieldCustomerAddress = new JTextField(20);
		textFieldCustomerAddress.addActionListener(addCustomerAction);

		textFieldCustomerCity = new JTextField(20);
		textFieldCustomerCity.addActionListener(addCustomerAction);

		textFieldCustomerPostcode = new JTextField(10);
		textFieldCustomerPostcode.addActionListener(addCustomerAction);

		textFieldCustomerPhoneNumber = new JTextField(15);
		textFieldCustomerPhoneNumber.addActionListener(addCustomerAction);

		// LABELS
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(30,0,0,0);
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(lblCustomerFirstName, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(5,0,0,0);
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
		gbc.insets = new Insets(30,0,0,0); 
		panelCustomersForm.add(textFieldCustomerFirstName, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		gbc.insets = new Insets(5,0,0,0);
		panelCustomersForm.add(textFieldCustomerLastName, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(textFieldCustomerHouseNumber, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(textFieldCustomerAddress, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(textFieldCustomerCity, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(textFieldCustomerPostcode, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		panelCustomersForm.add(textFieldCustomerPhoneNumber, gbc);

		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersForm.add(new JLabel(), gbc);

		// FORM BUTTON - ADD
		gbc.gridx = 1;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(addCustomerBtn, gbc);

		TitledBorder border = new TitledBorder("Add a New Customer:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelCustomersForm.setBorder(border);

		return panelCustomersForm;
		
	}

	// Set up customer orders table.
	private JPanel customersOrders() {
		
		JPanel panelCustomersOrders = new JPanel(new BorderLayout());

		customersOrdersTableModel = new DefaultTableModel(new String[] {
				"Order", "Date/Time", "Price"
		}, 0);

		customersOrdersTable = new JTable(customersOrdersTableModel ) {

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

		customersOrdersTable.setFont(new Font("", 0, 14));
		customersOrdersTable.setRowHeight(customersOrdersTable.getRowHeight() + 10);
		customersOrdersTable.setAutoCreateRowSorter(true);
		customersOrdersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// THIS PROBABLY WON'T BE NEEDED.
		customersOrdersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editCustomer(frame?)
				}

				try {
					int row = customersOrdersTable.getSelectedRow();
					//String houseNumber = customersOrdersTable.getModel().getValueAt(row, 2).toString();
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

		populateCustomersOrdersTable();

		JScrollPane jsp = new JScrollPane(customersOrdersTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		customersOrdersTable.setFillsViewportHeight(true);

		panelCustomersOrders.add(jsp, BorderLayout.CENTER);
		panelCustomersOrders.add(customersOrdersButtons(), BorderLayout.SOUTH);

		TitledBorder border = new TitledBorder("Customer orders:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);

		panelCustomersOrders.setBorder(border);

		return panelCustomersOrders;
	}

	private JPanel customersOrdersButtons() {
		
		JPanel panelCustomersOrdersButtons = new JPanel(new GridBagLayout());

		// THIS MIGHT NOT BE NEEDED
		JButton clearOrdersBtn = new JButton();
		clearOrdersBtn.setText("Clear");
		clearOrdersBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//editCustomer(frame?);
			}
		});

		// SPACING
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersOrdersButtons.add(new JLabel(), gbc);

		// BUTTON
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelCustomersOrdersButtons.add(clearOrdersBtn);

		return panelCustomersOrdersButtons;
	}

	private void populateCustomersOrdersTable() { // TO DO
		/*
	}
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

		 */
	}

	// Display error message.
	private static void showError(String error) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(frame, error,
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

	}

	// Add items to customers table.
	private static void populateCustomersTable() {
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
			customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			customersTable.getSelectionModel().setSelectionInterval(0, 0);
			customersTable.setColumnSelectionInterval(0, 0);
			customersTable.requestFocusInWindow();
		}
	}
	
	// Get all values from customer form text fields.
	private ArrayList<String> getCustomerTextFieldValues() {
		
		ArrayList<String> customerTextFieldsArray = new ArrayList<String>();

		customerTextFieldsArray.add(textFieldCustomerFirstName.getText());
		customerTextFieldsArray.add(textFieldCustomerLastName.getText());
		customerTextFieldsArray.add(textFieldCustomerHouseNumber.getText());
		customerTextFieldsArray.add(textFieldCustomerAddress.getText());
		customerTextFieldsArray.add(textFieldCustomerCity.getText());
		customerTextFieldsArray.add(textFieldCustomerPostcode.getText());
		customerTextFieldsArray.add(textFieldCustomerPhoneNumber.getText());

		return customerTextFieldsArray;
	}

	// get values from selected row in customers table.
	private static ArrayList<String> getSelectedCellValues() { // Col: 0 = First name, 1 = Last name, 2 = House number, 3 = Address, 4 = City, 5 = Postcode, 6 = Phone number.
		
		ArrayList<String> selectedCellValuesArray = new ArrayList<String>();
		int row = customersTable.getSelectedRow();
		String firstName = customersTable.getModel().getValueAt(row, 0).toString();
		String lastName = customersTable.getModel().getValueAt(row, 1).toString();
		String houseNumber = customersTable.getModel().getValueAt(row, 2).toString();
		String address = customersTable.getModel().getValueAt(row, 3).toString();
		String city = customersTable.getModel().getValueAt(row, 4).toString();
		String postcode = customersTable.getModel().getValueAt(row, 5).toString();
		String phoneNumber = customersTable.getModel().getValueAt(row, 6).toString();

		selectedCellValuesArray.add(firstName);
		selectedCellValuesArray.add(lastName);
		selectedCellValuesArray.add(houseNumber);
		selectedCellValuesArray.add(address);
		selectedCellValuesArray.add(city);
		selectedCellValuesArray.add(postcode);
		selectedCellValuesArray.add(phoneNumber);

		return selectedCellValuesArray;
	}

	/*

	private String getSelectedHouseNumber() { // REPLACE WITH getSelectedCellValue() !!!!!
		int row = customersTable.getSelectedRow();
		String selectedHouseNumber = customersTable.getModel().getValueAt(row, 2).toString();

		return selectedHouseNumber;
	}

	private String getSelectedAddress() {
		int row = customersTable.getSelectedRow();
		String selectedAddress = customersTable.getModel().getValueAt(row, 3).toString();

		return selectedAddress;
	}

	private String getSelectedCity() {
		int row = customersTable.getSelectedRow();
		String selectedCity = customersTable.getModel().getValueAt(row, 4).toString();

		return selectedCity;
	}

	 */

	private void clearCustomerForm() {
		textFieldCustomerFirstName.setText("");
		textFieldCustomerLastName.setText("");
		textFieldCustomerHouseNumber.setText("");
		textFieldCustomerAddress.setText("");
		textFieldCustomerCity.setText("");
		textFieldCustomerPostcode.setText("");
		textFieldCustomerPhoneNumber.setText("");
	}

	public JPanel getCustomerPanel() {
		return panelCustomersMain;
	}

}
