package basilspizza;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class StaffGUI {
	private JFrame frame;
	private JPanel panelStaffMain;
	private DefaultTableModel staffClockedInTableModel, staffClockedOutTableModel;
	private JTable staffClockedInTable, staffClockedOutTable;
	private GridBagConstraints gbc;
	private JTextField textFieldStaffSearch;

	public StaffGUI() {
		initGUI();
	}

	private void initGUI() {
		//frame = 
		gbc = new GridBagConstraints();
		panelStaffMain = new JPanel(new BorderLayout());
		
		JPanel panelMainGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelTopRow = new JPanel(new BorderLayout());
		JPanel panelBottomRow = new JPanel(new BorderLayout());
		
		panelTopRow.add(staffClockedInTable);
		panelBottomRow.add(staffClockedOutTable);
		
		panelMainGrid.add(panelTopRow);
		panelMainGrid.add(panelBottomRow);
		
		panelStaffMain.add(panelMainGrid);

		/*
		JPanel panelMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelLeftGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelRightGrid = new JPanel(new GridLayout(2, 1));
		
		panelLeftGrid.add(staffClockedInTable());
		panelLeftGrid.add(staffClockedOutTable());
		
		panelRightGrid.add(staffClockInOutForm());
		panelRightGrid.add(staffManagementForm());
		
		panelMainGrid.add(panelLeftGrid);
		panelMainGrid.add(panelRightGrid);


		panelStaffMain.add(panelMainGrid);
		*/
		

	}

	private JPanel staffClockedInTable() {
		JPanel panelStaffClockedInTable = new JPanel(new BorderLayout());
		

		staffClockedInTableModel = new DefaultTableModel(new String[] {
				"EMPLOYEE NO", "FIRST NAME", "LAST NAME", "JOB TITLE",
				"START TIME"
		}, 0);

		staffClockedInTable = new JTable(staffClockedInTableModel ) {
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

		staffClockedInTable.setFont(new Font("", 0, 14));
		//staffClockedInTable.setRowHeight(staffClockedInTable.getRowHeight() + 10);
		staffClockedInTable.setAutoCreateRowSorter(true);
		staffClockedInTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		staffClockedInTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = staffClockedInTable.getSelectedRow();
					String houseNumber = staffClockedInTable.getModel().getValueAt(row, 2).toString();
					String address = staffClockedInTable.getModel().getValueAt(row, 3).toString();
					String city = staffClockedInTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populatestaffClockedInTable();

		JScrollPane jsp = new JScrollPane(staffClockedInTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		staffClockedInTable.setFillsViewportHeight(true);

		panelStaffClockedInTable.add(jsp, BorderLayout.CENTER);
		panelStaffClockedInTable.add(staffClockedInTableButtons(), BorderLayout.SOUTH);
		panelStaffClockedInTable.add(staffClockedInTableSearch(), BorderLayout.NORTH);

		TitledBorder border = new TitledBorder("Search Currently Clocked-In Staff:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelStaffClockedInTable.setBorder(border);

		return panelStaffClockedInTable;
	}

	private JPanel staffClockedInTableButtons() {
		JPanel panelStaffClockedInTableButtons = new JPanel(new GridBagLayout());

		return panelStaffClockedInTableButtons;
	}

	private JPanel staffClockedInTableSearch() {
		
		JPanel panelStaffClockedInTableSearch = new JPanel(new GridBagLayout());


		JButton searchClearBtn = new JButton();
		searchClearBtn.setText("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldStaffSearch.setText("");
			}
		});

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(staffClockedInTable.getModel());
		staffClockedInTable.setRowSorter(rowSorter);

		textFieldStaffSearch = new JTextField(20);
		textFieldStaffSearch.getDocument().addDocumentListener(new DocumentListener() {
			// Set up search filter function
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textFieldStaffSearch.getText();

				if(text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = textFieldStaffSearch.getText();

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
		panelStaffClockedInTableSearch.add(new JLabel(), gbc);

		// TEXT FIELD
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelStaffClockedInTableSearch.add(textFieldStaffSearch, gbc);

		// BUTTON
		gbc.gridx++;
		panelStaffClockedInTableSearch.add(searchClearBtn, gbc);

		return panelStaffClockedInTableSearch;
	}

	private void populatestaffClockedInTable() {

	}

	private JPanel staffClockedOutTable() {
		JPanel panelStaffClockedOutTable = new JPanel(new BorderLayout());

		staffClockedOutTableModel = new DefaultTableModel(new String[] {
				"EMPLOYEE NO", "FIRST NAME", "LAST NAME", "JOB TITLE",
				"LAST CLOCK IN", "LAST CLOCK OUT"
		}, 0);

		staffClockedOutTable = new JTable(staffClockedOutTableModel ) {
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

		staffClockedOutTable.setFont(new Font("", 0, 14));
		staffClockedOutTable.setRowHeight(staffClockedOutTable.getRowHeight() + 10);
		staffClockedOutTable.setAutoCreateRowSorter(true);
		//staffClockedOutTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		staffClockedOutTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = staffClockedOutTable.getSelectedRow();
					String houseNumber = staffClockedOutTable.getModel().getValueAt(row, 2).toString();
					String address = staffClockedOutTable.getModel().getValueAt(row, 3).toString();
					String city = staffClockedOutTable.getModel().getValueAt(row, 4).toString();
					//populateMap(houseNumber, address, city);
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateStaffClockedOutTable();

		JScrollPane jsp = new JScrollPane(staffClockedOutTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		staffClockedOutTable.setFillsViewportHeight(true);

		panelStaffClockedOutTable.add(jsp, BorderLayout.CENTER);
		panelStaffClockedOutTable.add(staffClockedOutTableButtons(), BorderLayout.SOUTH);
		panelStaffClockedOutTable.add(staffClockedOutTableSearch(), BorderLayout.NORTH);

		TitledBorder border = new TitledBorder("Search Currently Clocked-Out Staff:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelStaffClockedOutTable.setBorder(border);

		return panelStaffClockedOutTable;
	}

	private JPanel staffClockedOutTableButtons() {
		JPanel panelStaffClockedOutTableButtons = new JPanel(new GridBagLayout());

		return panelStaffClockedOutTableButtons;
	}

	private JPanel staffClockedOutTableSearch() {
		JPanel panelStaffClockedOutTableSearch = new JPanel(new GridBagLayout());


		JButton searchClearBtn = new JButton();
		searchClearBtn.setText("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldStaffSearch.setText("");
			}
		});

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(staffClockedOutTable.getModel());
		staffClockedOutTable.setRowSorter(rowSorter);

		textFieldStaffSearch = new JTextField(20);
		textFieldStaffSearch.getDocument().addDocumentListener(new DocumentListener() {
			// Set up search filter function
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textFieldStaffSearch.getText();

				if(text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = textFieldStaffSearch.getText();

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
		panelStaffClockedOutTableSearch.add(new JLabel(), gbc);

		// TEXT FIELD
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		panelStaffClockedOutTableSearch.add(textFieldStaffSearch, gbc);

		// BUTTON
		gbc.gridx++;
		panelStaffClockedOutTableSearch.add(searchClearBtn, gbc);

		return panelStaffClockedOutTableSearch;
	}

	private void populateStaffClockedOutTable() {

	}

	private JPanel staffClockInOutForm() {
		JPanel panelStaffClockInOutFormMain = new JPanel(new BorderLayout());
		JPanel panelStaffClockInOutForm = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelStaffClockInOutForm.add(new JLabel("Staff Clock-In"), gbc);

		panelStaffClockInOutFormMain.add(panelStaffClockInOutForm, BorderLayout.CENTER);
		
		return panelStaffClockInOutFormMain;
	}
	
	private JPanel staffManagementForm() {
		JPanel panelStaffManagementFormMain = new JPanel(new BorderLayout());
		JPanel panelStaffManagementForm = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelStaffManagementForm.add(new JLabel("Management"), gbc);
		
		panelStaffManagementFormMain.add(panelStaffManagementForm, BorderLayout.CENTER);
		
		return panelStaffManagementFormMain;
	}

	public JPanel getStaffPanel() {
		return panelStaffMain;
	}
}
