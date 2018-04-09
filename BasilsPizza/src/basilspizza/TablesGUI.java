package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TablesGUI {
	
	private JFrame frame;
	
	private static JList tableList;
	private static DefaultListModel tableListModel;
	
	private static DefaultTableModel tableOrdersTableModel;
	private static JTable tableOrdersTable;
	
	private static JComboBox comboboxStaff;
	
	private static JTextArea textAreaSpecialRequirements;
	
	
	
	
	private JPanel panelTablesMain;
	
	
	public TablesGUI() {
		initGUI();
	}
	
	public void initGUI() {
		
		panelTablesMain = new JPanel(new BorderLayout());
		JPanel panelTablesMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelTablesSelectInfoPaymentGrid = new JPanel(new GridLayout(3, 1));
		
		panelTablesSelectInfoPaymentGrid.add(tableSelect());
		panelTablesSelectInfoPaymentGrid.add(tableInfo());
		panelTablesSelectInfoPaymentGrid.add(tablePayment());
		panelTablesMainGrid.add(panelTablesSelectInfoPaymentGrid);
		panelTablesMainGrid.add(tableOrders());
		
		panelTablesMain.add(panelTablesMainGrid, BorderLayout.CENTER);
		
	}
	
	public JPanel tableSelect() {
		JPanel panelTablesSelect = new JPanel(new BorderLayout());
		JPanel panelTablesSelectEmptyBorder = new JPanel(new BorderLayout());
		
		tableListModel = new DefaultListModel();
		
		tableList = new JList(tableListModel);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setAssignedStaff(getSelectedTable());
				setSpecialRequirements(getSelectedTable());
			}
		};
		tableList.addMouseListener(mouseListener);
		
		populateTables();
		
		JScrollPane jsp = new JScrollPane(tableList);
		
		
		// Test add item to list
		tableListModel.add(0, "TEST ITEM");
		
		
		
		panelTablesSelectEmptyBorder.add(jsp, BorderLayout.CENTER);
		
		panelTablesSelectEmptyBorder.setBorder(BorderFactory.createEmptyBorder(1, 7, 0, 7));
		
		panelTablesSelect.add(panelTablesSelectEmptyBorder, BorderLayout.CENTER);
		panelTablesSelect.add(tableSelectButtons(), BorderLayout.SOUTH);
		
		TitledBorder border = new TitledBorder("Tables:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelTablesSelect.setBorder(border);
		
		return panelTablesSelect;
	}
	
	public JPanel tableSelectButtons() {
		JPanel panelTableSelectButtons = new JPanel(new GridBagLayout());
		
		JButton buttonAddTable = new JButton("Add");
		buttonAddTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddTableDialogGUI(frame);
				populateTables();
			}
		});
		
		JButton buttonDeleteTable = new JButton("Delete");
		buttonDeleteTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.deleteTable(getSelectedTable());
				populateTables();
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelTableSelectButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelTableSelectButtons.add(buttonAddTable, gbc);
		
		gbc.gridx++;
		panelTableSelectButtons.add(buttonDeleteTable, gbc);
		
		
		
		return panelTableSelectButtons;
	}
	
	public JPanel tableInfo() {
		JPanel panelTableInfoMain = new JPanel(new BorderLayout());
		JPanel panelTableInfoAssignStaff = new JPanel(new GridBagLayout());
		JPanel panelTableInfo = new JPanel(new GridBagLayout());
		
		
		
		
		comboboxStaff = new JComboBox();
		populateComboboxStaff();
		
		JTextField textFieldStaffAssigned = new JTextField(20);
		
		textAreaSpecialRequirements = new JTextArea();
		textAreaSpecialRequirements.setLineWrap(true);
		
		
		JScrollPane jsp = new JScrollPane(textAreaSpecialRequirements,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setPreferredSize(new Dimension(205,100));
		
		/*
		JCheckBox checkboxBirthday = new JCheckBox();
		checkboxBirthday.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				//Object source = e.getItemSelectable();
				System.out.println("CHECKBOX STATE CHANGED");
			}
		});
		*/
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		// ASSIGN STAFF
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(10, 0, 0, 0);
		panelTableInfo.add(new JLabel("Assigned Table Staff: "), gbc);
		
		gbc.gridy++;
		panelTableInfo.add(new JLabel("Dietary/Special Requirements: "), gbc);
		
		/*
		gbc.gridy++;
		panelTableInfo.add(new JLabel("Birthday: "), gbc);
		*/
		
		
		
		//////////////
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelTableInfo.add(comboboxStaff, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(10, 5, 0 , 0);
		panelTableInfo.add(jsp, gbc);
		
		/*
		gbc.gridy++;
		gbc.insets = new Insets(10, 0 , 0, 0);
		panelTableInfo.add(checkboxBirthday, gbc);
		*/
	
		
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelTableInfo.add(new JLabel(), gbc);
		
		/*
		gbc.gridx = 20;
		gbc.gridy = 0;
		panelTableInfo.add(new JLabel(), gbc);
		*/
		
		
		panelTableInfoMain.add(panelTableInfo, BorderLayout.CENTER);
		panelTableInfoMain.add(tableInfoButtons(), BorderLayout.SOUTH);
		
		TitledBorder border = new TitledBorder("Table Information:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelTableInfoMain.setBorder(border);
		
		
		
		return panelTableInfoMain;
	}
	
	public JPanel tableInfoButtons() {
		JPanel panelTableInfoButtons = new JPanel(new GridBagLayout());
		
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.updateTableInfo(getSelectedStaff(), getSpecialRequirements(), getSelectedTable());
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelTableInfoButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelTableInfoButtons.add(buttonSave);
		
		return panelTableInfoButtons;
	}
	
	public JPanel tablePayment() {
		JPanel panelTablePaymentMain = new JPanel(new BorderLayout());
		JPanel panelTablePayment = new JPanel(new GridBagLayout());
		
		JCheckBox checkboxDiscount = new JCheckBox();
		checkboxDiscount.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				//Object source = e.getItemSelectable();
				System.out.println("DISCOUNT CHECKBOX STATE CHANGED");
			}
		});
		
		JTextField textFieldPromoCode = new JTextField(15);
		
		JLabel labelTotalPrice = new JLabel("TOTAL");
		Font font = new Font("Arial", Font.BOLD, 22);
		labelTotalPrice.setFont(font);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(20, 0, 0, 0);
		panelTablePayment.add(new JLabel("Student/Senior Discount (10%): "), gbc);
		
		gbc.gridy++;
		panelTablePayment.add(new JLabel("Promotional Code: "), gbc);
		
		gbc.gridy++;
		panelTablePayment.add(new JLabel("Total Due: "), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		panelTablePayment.add(checkboxDiscount, gbc);
		
		gbc.gridy++;
		gbc.insets = new Insets(20, 4, 0, 0);
		panelTablePayment.add(textFieldPromoCode, gbc);
		
		gbc.gridy++;
		//gbc.insets = new Insets(0, 0, 0, 0);
		panelTablePayment.add(labelTotalPrice, gbc);
		
		/*
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelTablePayment.add(new JLabel(), gbc);
		*/
		
		TitledBorder border = new TitledBorder("Payment:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelTablePaymentMain.setBorder(border);
		
		panelTablePaymentMain.add(panelTablePayment, BorderLayout.CENTER);
		panelTablePaymentMain.add(tablePaymentButtons(), BorderLayout.SOUTH);
				
		return panelTablePaymentMain;
	}
	
	public JPanel tablePaymentButtons() {
		JPanel panelTablePaymentButtons = new JPanel(new GridBagLayout());
		
		JButton buttonPrintBill = new JButton("Print Bill");
		JButton buttonPaidCash = new JButton("Paid Cash");
		JButton buttonPaidCard = new JButton("Paid Card");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelTablePaymentButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelTablePaymentButtons.add(buttonPrintBill, gbc);
		
		gbc.gridx++;
		panelTablePaymentButtons.add(buttonPaidCash, gbc);
	
		gbc.gridx++;
		panelTablePaymentButtons.add(buttonPaidCard, gbc);
		
		return panelTablePaymentButtons;
	}
	
	public JPanel tableOrders() {
		JPanel panelTableOrders = new JPanel(new BorderLayout());
		
		//panelTableOrders.add(new JLabel("Orders"));
		
		tableOrdersTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Quantity", "Price"
		}, 0);

		tableOrdersTable = new JTable(tableOrdersTableModel ) {
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

		tableOrdersTable.setFont(new Font("", 0, 14));
		tableOrdersTable.setRowHeight(tableOrdersTable.getRowHeight() + 10);
		tableOrdersTable.setAutoCreateRowSorter(true);
		tableOrdersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableOrdersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = tableOrdersTable.getSelectedRow();
					String houseNumber = tableOrdersTable.getModel().getValueAt(row, 2).toString();
					String address = tableOrdersTable.getModel().getValueAt(row, 3).toString();
					String city = tableOrdersTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateTableOrdersTable();

		JScrollPane jsp = new JScrollPane(tableOrdersTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tableOrdersTable.setFillsViewportHeight(true);

		panelTableOrders.add(jsp, BorderLayout.CENTER);
		panelTableOrders.add(tableOrdersTableButtons(), BorderLayout.SOUTH);
		//panelCustomersTable.add(tableOrdersTableSearch(), BorderLayout.NORTH);

		TitledBorder border = new TitledBorder("Table Orders:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelTableOrders.setBorder(border);

		
		return panelTableOrders;
	}
	
	public JPanel tableOrdersTableButtons() {
		JPanel panelTableOrdersTableButtons = new JPanel(new GridBagLayout());
		
		
		JButton clearBtn = new JButton("Clear");
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelTableOrdersTableButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelTableOrdersTableButtons.add(clearBtn, gbc);
		
		
		
		
		
		
		return panelTableOrdersTableButtons;
	}
	
	public void populateTables() {
		
		tableListModel.removeAllElements();

		Database.selectTables();

		for (int i = 0; i < Database.getTablesArray().size(); i++) {

			String tableId = Database.getTablesArray().get(i).getTableId();
			String assignedStaff = Database.getTablesArray().get(i).getAssignedStaff();
			String specialRequirements = Database.getTablesArray().get(i).getSpecialRequirements();
			String orderId = Database.getTablesArray().get(i).getOrderId();
		
			Object[] data = {tableId, assignedStaff, specialRequirements, orderId};

			tableListModel.addElement(tableId);
			//tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//tableList.getSelectionModel().setSelectionInterval(0, 0);
			
			/*
			allStaffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			allStaffTable.getSelectionModel().setSelectionInterval(0, 0);
			allStaffTable.setColumnSelectionInterval(0, 0);
			allStaffTable.requestFocusInWindow();
			*/
		}
	}
	
	public static void populateComboboxStaff() { // Needs to be public as this method is called from StaffGUI when other staff clock in.
		
		comboboxStaff.removeAllItems();
		
		Database.selectClockedInStaff();
		
		for (int i = 0; i < Database.getStaffClockedInArray().size(); i++) {

			String staffId = Database.getStaffClockedInArray().get(i).getStaffId();
			String firstName = Database.getStaffClockedInArray().get(i).getFirstName();
			String lastName = Database.getStaffClockedInArray().get(i).getLastName();
			String jobTitle = Database.getStaffClockedInArray().get(i).getJobTitle();
			


			//Object[] data = {staffId, firstName, lastName, jobTitle};
			
			String dataStr = staffId + " " + firstName +  " " + lastName + " " + jobTitle;
			
			comboboxStaff.addItem(dataStr);
			
		}
		
		
	}
	
	public void populateTableOrdersTable() {
		
	}
	
	public String getSelectedTable() {
		String selectedTable = tableList.getSelectedValue().toString();
		return selectedTable;
	}
	
	public String getSelectedStaff() {
		String selectedStaff = comboboxStaff.getItemAt(comboboxStaff.getSelectedIndex()).toString();
		return selectedStaff;
	}
	
	public String getSpecialRequirements() {
		return textAreaSpecialRequirements.getText();
	}
	
	public void setSpecialRequirements(String tableId) {
		textAreaSpecialRequirements.setText(Database.selectTableSpecialRequirements(tableId));
	}
	
	public void setAssignedStaff(String tableId) {
		comboboxStaff.setSelectedItem(Database.selectTableAssignedStaff(tableId));
	}
	
	public JPanel getTablesPanel() {
		return panelTablesMain;
	}
	

}
