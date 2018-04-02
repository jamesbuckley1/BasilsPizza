package basilspizza;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
	private DefaultTableModel staffClockedInTableModel, allStaffTableModel;
	private JTable staffClockedInTable, allStaffTable;
	
	private JTextField textFieldStaffSearch;

	public StaffGUI() {
		initGUI();
	}

	private void initGUI() {
		//frame = 
		
		panelStaffMain = new JPanel(new BorderLayout());
		
		JPanel panelMainGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelTopRow = new JPanel(new BorderLayout());
		JPanel panelBottomRow = new JPanel(new BorderLayout());
		
		panelTopRow.add(staffClockedInTable(), BorderLayout.CENTER);
		panelBottomRow.add(allStaffTable());
		
		panelMainGrid.add(panelTopRow);
		panelMainGrid.add(panelBottomRow);
		
		panelStaffMain.add(panelMainGrid);

		/*
		JPanel panelMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelLeftGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelRightGrid = new JPanel(new GridLayout(2, 1));
		
		panelLeftGrid.add(staffClockedInTable());
		panelLeftGrid.add(allStaffTable());
		
		panelRightGrid.add(staffClockInOutForm());
		panelRightGrid.add(staffManagementForm());
		
		panelMainGrid.add(panelLeftGrid);
		panelMainGrid.add(panelRightGrid);


		panelStaffMain.add(panelMainGrid);
		*/
		

	}

	private JPanel staffClockedInTable() {
		JPanel panelStaffClockedInTable = new JPanel(new GridBagLayout());
		

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
		
		JPanel panelJsp = new JPanel(new BorderLayout());
		
		TitledBorder border = new TitledBorder("Search Currently Clocked-In Staff:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelJsp.setBorder(border);
		
		panelJsp.add(jsp, BorderLayout.CENTER);
		panelJsp.add(staffClockedInTableSearch(), BorderLayout.NORTH);

		staffClockedInTable.setFillsViewportHeight(true);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.9;
		gbc.weighty = 1.0;
		//gbc.gridwidth = 1;
		gbc.gridheight = 1;
		panelStaffClockedInTable.add(panelJsp, gbc);
	
		
		//JPanel formPanel = new JPanel(new BorderLayout());
		//formPanel.add(staffClockInOutForm(), BorderLayout.CENTER);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.gridwidth = 0;
		//gbc.gridheight = 0;
		//gbc.anchor = GridBagConstraints.LINE_START;
		panelStaffClockedInTable.add(staffClockInOutForm(), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		//gbc.anchor = GridBagConstraints.LINE_START;
		//panelStaffClockedInTable.add(new JLabel("test1"), gbc);
		
		
		
		
		
		//panelStaffClockedInTable.add(staffClockedInTableButtons(), BorderLayout.SOUTH);
		

		

		return panelStaffClockedInTable;
	}

	private JPanel staffClockedInTableButtons() {
		JPanel panelStaffClockedInTableButtons = new JPanel(new GridBagLayout());

		return panelStaffClockedInTableButtons;
	}

	private JPanel staffClockedInTableSearch() {
		
		JPanel panelStaffClockedInTableSearch = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

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

	private JPanel allStaffTable() {
		JPanel panelAllStaffTable = new JPanel(new BorderLayout());

		allStaffTableModel = new DefaultTableModel(new String[] {
				"EMPLOYEE NO", "FIRST NAME", "LAST NAME", "JOB TITLE",
				"LAST CLOCK IN", "LAST CLOCK OUT"
		}, 0);

		allStaffTable = new JTable(allStaffTableModel ) {
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

		allStaffTable.setFont(new Font("", 0, 14));
		allStaffTable.setRowHeight(allStaffTable.getRowHeight() + 10);
		allStaffTable.setAutoCreateRowSorter(true);
		//allStaffTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		allStaffTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = allStaffTable.getSelectedRow();
					String houseNumber = allStaffTable.getModel().getValueAt(row, 2).toString();
					String address = allStaffTable.getModel().getValueAt(row, 3).toString();
					String city = allStaffTable.getModel().getValueAt(row, 4).toString();
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

		JScrollPane jsp = new JScrollPane(allStaffTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		allStaffTable.setFillsViewportHeight(true);

		panelAllStaffTable.add(jsp, BorderLayout.CENTER);
		panelAllStaffTable.add(allStaffTableButtons(), BorderLayout.SOUTH);
		panelAllStaffTable.add(allStaffTableSearch(), BorderLayout.NORTH);

		TitledBorder border = new TitledBorder("Search All Staff:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelAllStaffTable.setBorder(border);

		return panelAllStaffTable;
	}

	private JPanel allStaffTableButtons() {
		JPanel allStaffTableButtons = new JPanel(new GridBagLayout());

		return allStaffTableButtons;
	}

	private JPanel allStaffTableSearch() {
		JPanel allStaffTableSearch = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		JButton searchClearBtn = new JButton();
		searchClearBtn.setText("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldStaffSearch.setText("");
			}
		});

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(allStaffTable.getModel());
		allStaffTable.setRowSorter(rowSorter);

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
		allStaffTableSearch.add(new JLabel(), gbc);

		// TEXT FIELD
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		allStaffTableSearch.add(textFieldStaffSearch, gbc);

		// BUTTON
		gbc.gridx++;
		allStaffTableSearch.add(searchClearBtn, gbc);

		return allStaffTableSearch;
	}

	private void populateStaffClockedOutTable() {

	}

	private JPanel staffClockInOutForm() {
		JPanel panelStaffClockInOutFormMain = new JPanel(new BorderLayout());
		JPanel panelStaffClockInOutForm = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JTextField textFieldEmployeeNumber = new JTextField(10);
		textFieldEmployeeNumber.setPreferredSize(new Dimension(100, 40));
		
		//Font currentFont = textFieldEmployeeNumber.getFon;
		Font font = new Font("Arial", Font.PLAIN, 16);
		textFieldEmployeeNumber.setFont(font);
		//textFieldEmployeeNumber.setText("12345");
		//textFieldEmployeeNumber.setMinimumSize(textFieldEmployeeNumber.getPreferredSize());
		
		JButton clockInBtn = new JButton("Clock In");
		clockInBtn.setPreferredSize(new Dimension(100, 40));
		
		JButton clockOutBtn = new JButton("Clock Out");
		clockOutBtn.setPreferredSize(new Dimension(100, 40));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		//gbc.anchor = GridBagConstraints.LINE_END;
		panelStaffClockInOutForm.add(new JLabel("Employee Number: "), gbc);
		
		gbc.gridy = 1;
		panelStaffClockInOutForm.add(new JLabel(" "), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelStaffClockInOutForm.add(clockInBtn, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		//gbc.anchor = GridBagConstraints.LINE_START;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		panelStaffClockInOutForm.add(textFieldEmployeeNumber, gbc);
	
		gbc.gridy = 1;
		panelStaffClockInOutForm.add(new JLabel(" "), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelStaffClockInOutForm.add(clockOutBtn, gbc);
		
		
		
		
		/*
		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelStaffClockInOutForm.add(new JLabel(), gbc);
		*/
		
		
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		//gbc.anchor = GridBagConstraints.LINE_START;
		//panelStaffClockInOutForm.add(new JLabel("Employee number: "), gbc);
		
		/*
		gbc.gridx++;
		//gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.anchor = GridBagConstraints.LINE_START;
		panelStaffClockInOutForm.add(textFieldEmployeeNumber, gbc);
		
		gbc.gridx++;
		
		*/
		//panelStaffClockInOutForm.add(new JLabel("tets"), gbc);
		
		TitledBorder border = new TitledBorder("Staff Clock-In/Out:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelStaffClockInOutForm.setBorder(border);

		panelStaffClockInOutFormMain.add(panelStaffClockInOutForm, BorderLayout.CENTER);
		
		return panelStaffClockInOutFormMain;
	}
	
	private JPanel staffManagementForm() {
		JPanel panelStaffManagementFormMain = new JPanel(new BorderLayout());
		JPanel panelStaffManagementForm = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
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
