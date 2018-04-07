package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	private static DefaultTableModel tableOrdersTableModel;
	private static JTable tableOrdersTable;
	
	private JList tableList;
	private DefaultListModel tableListModel;
	
	
	private JPanel panelTablesMain;
	
	
	public TablesGUI() {
		initGUI();
	}
	
	public void initGUI() {
		
		panelTablesMain = new JPanel(new BorderLayout());
		JPanel panelTablesMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelTablesSelectReserveInfoGrid = new JPanel(new GridLayout(3, 1));
		
		panelTablesSelectReserveInfoGrid.add(tableSelect());
		panelTablesSelectReserveInfoGrid.add(tableInfo());
		panelTablesMainGrid.add(panelTablesSelectReserveInfoGrid);
		panelTablesMainGrid.add(tableOrders());
		
		panelTablesMain.add(panelTablesMainGrid, BorderLayout.CENTER);
		
	}
	
	public JPanel tableSelect() {
		JPanel panelTablesSelect = new JPanel(new BorderLayout());
		JPanel panelTablesSelectEmptyBorder = new JPanel(new BorderLayout());
		
		tableListModel = new DefaultListModel();
		tableList = new JList(tableListModel);
		//tableList.getPreferredScrollableViewportSize();
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
		JButton buttonDeleteTable = new JButton("Delete");
		
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
		
		String testArray[] = {"Select Staff from Dropdown List", "STAFF NAME 1", "STAFF NAME 2", "STAFF NAME 3"};
		JComboBox comboBoxStaff = new JComboBox(testArray); // Add array to this
		
		JTextField textFieldStaffAssigned = new JTextField(20);
		
		JTextArea textAreaSpecialRequirements = new JTextArea();
		textAreaSpecialRequirements.setLineWrap(true);
		
		
		JScrollPane jsp = new JScrollPane(textAreaSpecialRequirements,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setPreferredSize(new Dimension(245,100));
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		// ASSIGN STAFF
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		//gbc.insets = new Insets(0, 10, 0, 0);
		panelTableInfo.add(new JLabel("Assigned Table Staff: "), gbc);
		
		gbc.gridy++;
		panelTableInfo.add(new JLabel("Dietary/Special Requirements: "), gbc);
		
		gbc.gridy++;
		panelTableInfo.add(new JLabel("Birthday: "), gbc);
		
		gbc.gridy++;
		panelTableInfo.add(new JLabel("Assign Table Staff: "), gbc);
		
		/*
		gbc.gridy++;
		panelTableInfo.add(new JLabel("test "), gbc);
		
		gbc.gridy++;
		panelTableInfo.add(new JLabel("Meal duration: "), gbc);
		*/
		
		
		//////////////
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelTableInfo.add(textFieldStaffAssigned, gbc);
		
		gbc.gridy++;
		panelTableInfo.add(jsp, gbc);
		
		/*
		gbc.gridy++;
		panelTableInfo.add(checkboxBirthday, gbc);
		*/
		
		gbc.gridy++;
		panelTableInfo.add(comboBoxStaff, gbc);
		
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
		//panelTableInfoMain.add(panelTableInfoAssignStaff, BorderLayout.NORTH);
		
		
		
		return panelTableInfoMain;
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
		
		JButton paidBtn = new JButton("Paid");
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
		
		gbc.gridx++;
		panelTableOrdersTableButtons.add(paidBtn, gbc);
		
		
		
		
		return panelTableOrdersTableButtons;
	}
	
	public void populateTableOrdersTable() {
		
	}
	
	
	public JPanel getTablesPanel() {
		return panelTablesMain;
	}
	

}
