import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class CustomerGUI {

	private static DefaultTableModel customersTableModel, customersOrdersTableModel;
	private static JTable customersTable, customersOrdersTable;
	private static GridBagConstraints gbc;

	//SQLite error codes:
	private static final int SQLITE_CONSTRAINT_PRIMARYKEY = 19;

	private JFrame frame;
	
	private JPanel panelCustomersMain;

	private JTextField textFieldCustomerFirstName, textFieldCustomerLastName,
						textFieldCustomerHouseNumber, textFieldCustomerAddress,
						textFieldCustomerCity, textFieldCustomerPostcode,
						textFieldCustomerPhoneNumber, textFieldCustomersSearch;

	public CustomerGUI() {
		initGUI();
	}

	private void initGUI() {
		// Check if running on event dispatch thread.
		if (SwingUtilities.isEventDispatchThread()) { 
			System.err.println("CustomerGUI running on EDT");
		} else {
			System.err.println("CustomerGUI not running on EDT");
		}

		
		
		panelCustomersMain = new JPanel(new BorderLayout());
		//frame = (JFrame)SwingUtilities.getRoot(panelCustomersMain); // ?
		
		JPanel panelCustomersMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelCustomersFormOrdersGrid = new JPanel(new GridLayout(2, 1));
		
		
		
		
		gbc = new GridBagConstraints();
		panelCustomersMainGrid.add(customersTable());
		

	
		
		
		//panelCustomersOrders.add(customersOrders(), BorderLayout.CENTER);
		//panelCustomersOrders.add(new JLabel("TEST"), BorderLayout.SOUTH);
	
		panelCustomersFormOrdersGrid.add(customersForm());
		panelCustomersFormOrdersGrid.add(customersOrders()); 

		panelCustomersMainGrid.add(panelCustomersFormOrdersGrid);
		panelCustomersMain.add(panelCustomersMainGrid);

	}
	
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
					//editCustomer(frame?)
				}

				try {
					int row = customersTable.getSelectedRow();
					String houseNumber = customersTable.getModel().getValueAt(row, 2).toString();
					String address = customersTable.getModel().getValueAt(row, 3).toString();
					String city = customersTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to delete.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateCustomersTable();

		customersTable.setFillsViewportHeight(true);
		
		JScrollPane jsp = new JScrollPane(customersTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		panelCustomersTable.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelCustomersTable.add(jsp, BorderLayout.CENTER);
		panelCustomersTable.add(customersTableButtons(), BorderLayout.SOUTH);
		panelCustomersTable.add(customersTableSearch(), BorderLayout.NORTH);
		
		return panelCustomersTable;
	}
	
	private JPanel customersTableButtons() {
		JButton customerInfoBtn = new JButton();
		customerInfoBtn.setText("Info & Directions");
		customerInfoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new CustomerInfoDialogGUI(getSelectedCellValues());
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

		
		
		JPanel panelCustomersTableButtons = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersTableButtons.add(new JLabel(), gbc);
		
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
		
		JButton searchClearBtn = new JButton();
		searchClearBtn.setText("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldCustomersSearch.setText("");
			}
		});
		
		
		JPanel panelCustomersTableSearch = new JPanel(new GridBagLayout());
		
		textFieldCustomersSearch = new JTextField(20);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(customersTable.getModel());
		customersTable.setRowSorter(rowSorter);
		
		textFieldCustomersSearch.getDocument().addDocumentListener(new DocumentListener() {

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

		
		//TRICK
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersTableSearch.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersTableSearch.add(new JLabel("Filter: "));
	
		
		gbc.gridx++;
		panelCustomersTableSearch.add(textFieldCustomersSearch, gbc);

		gbc.gridx++;
		panelCustomersTableSearch.add(searchClearBtn, gbc);
		
		
		
		return panelCustomersTableSearch;
	}
	
	private JPanel customersForm() {
		JButton addCustomerBtn = new JButton();
		addCustomerBtn.setText("Add"); //Is this line needed?
		addCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				String firstName = textFieldCustomerFirstName.getText();
				String lastName = textFieldCustomerLastName.getText();
				String houseNumber = textFieldCustomerHouseNumber.getText();
				String address = textFieldCustomerAddress.getText();
				String city = textFieldCustomerCity.getText();
				String postcode = textFieldCustomerPostcode.getText();
				String phoneNumber = textFieldCustomerPhoneNumber.getText();


				Customer c = new Customer(firstName, lastName, houseNumber,
						address, city, postcode, phoneNumber);

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
						try {
							c.addCustomerToDatabase();
						} catch (SQLException e) {
							if (e.getErrorCode() == SQLITE_CONSTRAINT_PRIMARYKEY) {
								showError("Duplicate entry - Customer already exists.");
							}
						}
						populateCustomersTable();
						clearCustomerForm();
					}
				} catch (Exception e) {
					showError("Error.");
					e.printStackTrace();
				}


				
			}
		});
		
		JLabel lblCustomerFormTitle = new JLabel("Add new customer: ");
		JLabel lblCustomerFirstName = new JLabel("First name: ");
		JLabel lblCustomerLastName = new JLabel("Last name: ");
		JLabel lblCustomerHouseNumber = new JLabel("House number: ");
		JLabel lblCustomerAddress = new JLabel("Address: ");
		JLabel lblCustomerCity = new JLabel("City: ");
		JLabel lblCustomerPostcode = new JLabel("Postcode: ");
		JLabel lblCustomerPhoneNumber = new JLabel("Phone number: ");

		textFieldCustomerFirstName = new JTextField(20);
		textFieldCustomerLastName = new JTextField(20);
		textFieldCustomerHouseNumber = new JTextField(5);
		textFieldCustomerAddress = new JTextField(20);
		textFieldCustomerCity = new JTextField(20);
		textFieldCustomerPostcode = new JTextField(10);
		textFieldCustomerPhoneNumber = new JTextField(15);
		
		JPanel panelCustomersForm = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(15,0,0,0); //TOP, LEFT 
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(lblCustomerFormTitle, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(40,10,0,0); //TOP, LEFT 
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(lblCustomerFirstName, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(5,10,0,0); //TOP, LEFT 
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
		gbc.insets = new Insets(40,10,0,0); //TOP, LEFT 
		panelCustomersForm.add(textFieldCustomerFirstName, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		gbc.insets = new Insets(5,10,0,0); //TOP, LEFT 
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

		// FORM BUTTON - ADD
		gbc.gridx = 1;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelCustomersForm.add(addCustomerBtn, gbc);

		//TRICK
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersForm.add(new JLabel(), gbc);
		
		
		JPanel panelCustomersFormBorder = new JPanel(new BorderLayout());
		JPanel panelCustomersFormButtons = new JPanel(new FlowLayout());
		
		panelCustomersFormBorder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panelCustomersFormBorder.add(panelCustomersForm, BorderLayout.CENTER);
		panelCustomersFormBorder.add(panelCustomersFormButtons, BorderLayout.SOUTH);
		
		return panelCustomersForm;
		
	}
		
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

		customersOrdersTable.setFillsViewportHeight(true);
		
		JScrollPane jsp = new JScrollPane(customersOrdersTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panelCustomersOrders.add(new JLabel("Customer orders:"), BorderLayout.NORTH);
		panelCustomersOrders.setBorder(BorderFactory.createEmptyBorder(0,0,9,0));
		panelCustomersOrders.add(jsp, BorderLayout.CENTER);
		panelCustomersOrders.add(customersOrdersButtons(), BorderLayout.SOUTH);
		
		
		return panelCustomersOrders;
		
	}
	
	private JPanel customersOrdersButtons() {
		
		JButton clearOrdersBtn = new JButton();
		clearOrdersBtn.setText("Clear");
		clearOrdersBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//editCustomer(frame?);
			}
		});
		
		JPanel panelCustomersOrdersButtons = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCustomersOrdersButtons.add(new JLabel(), gbc);
		
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

	private void showError(String error) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(frame, error,
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

	}

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

			System.out.println("POPULATECUSTMERTABLE");
			System.out.println(firstName);
			System.out.println(lastName);
			System.out.println(houseNumber);
			System.out.println(address);
			System.out.println(city);
			System.out.println(postcode);
			System.out.println(phoneNumber);


			Object[] data = {firstName, lastName, houseNumber, address, city,
					postcode, phoneNumber
			};



			customersTableModel.addRow(data);


		}
	}
	/*
	private void populateMap(String houseNumber, String address, String city) {

		CustomerMap cm = new CustomerMap(houseNumber, address, city);
		panelCustomersMap.removeAll();


		//panelCustomersMap.add(cm.getImage(), BorderLayout.CENTER);
		panelCustomersMap.add(panelCustomersMapButtons, BorderLayout.SOUTH);

		panelCustomersMap.validate();
		panelCustomersMap.repaint();


	}

	private void expandMap(String url) {
		JEditorPane jep = new JEditorPane();
		jep.setEditable(false);

		try {
			jep.setPage(url);
		} catch (Exception e) {
			jep.setContentType("text/html");
			jep.setText("<html>Could not load page.</html>");
		}

		JScrollPane jsp = new JScrollPane(jep);
		JFrame frame = new JFrame("Google Maps");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(jsp);
		frame.setPreferredSize(new Dimension(800,600));
		frame.pack();
		frame.setVisible(true);
	}

	*/
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
	
	private ArrayList<String> getSelectedCellValues() { // Col: 0 = First name, 1 = Last name, 2 = House number, 3 = Address, 4 = City, 5 = Postcode, 6 = Phone number.
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
