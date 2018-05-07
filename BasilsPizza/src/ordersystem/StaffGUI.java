package ordersystem;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class StaffGUI {
	private static JTable allStaffTable;
	private JFrame frame;
	private JPanel panelStaffMain;
	private DefaultTableModel staffClockedInTableModel, allStaffTableModel;
	private JTable staffClockedInTable;
	private JTextField textFieldStaffId;
	private boolean boolAllStaffSearchPlaceholderText = false;
	private boolean boolClockedInStaffSearchPlaceholderText = false;
	private JTextField textFieldAllStaffSearch;
	private JTextField textFieldClockedInStaffSearch;

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
				"STAFF ID", "FIRST NAME", "LAST NAME", "JOB TITLE",
				"START TIME"
		}, 0);

		staffClockedInTable = new JTable(staffClockedInTableModel ) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);
				
				// Next 3 lines adapted from https://stackoverflow.com/questions/17858132/automatically-adjust-jtable-column-to-fit-content/25570812
				int rendererWidth = c.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth())); // Sets width of columns to fill content.

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
		staffClockedInTable.setRowHeight(staffClockedInTable.getRowHeight() + 10);
		staffClockedInTable.setAutoCreateRowSorter(true);
		//staffClockedInTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		staffClockedInTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					// EDIT CUSTOMER - Do this is there's time.
				}

				try {
					int row = staffClockedInTable.getSelectedRow();
					String staffId = staffClockedInTable.getModel().getValueAt(row, 0).toString();
					setTextFieldStaffId(staffId);
					
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateStaffClockedInTable();

		JScrollPane jsp = new JScrollPane(staffClockedInTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel panelJsp = new JPanel(new BorderLayout());

		TitledBorder border = new TitledBorder("Clocked-In Staff:");
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



	private JPanel staffClockedInTableSearch() {

		JPanel panelStaffClockedInTableSearch = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		JButton searchClearBtn = new JButton();
		searchClearBtn.setText("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldClockedInStaffSearch.setText("");
			}
		});

		// Following search code adapted from https://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(staffClockedInTable.getModel());
		staffClockedInTable.setRowSorter(rowSorter);

		textFieldClockedInStaffSearch = new JTextField("Type to search...", 20);
		textFieldClockedInStaffSearch.getDocument().addDocumentListener(new DocumentListener() {
			// Set up search filter function
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textFieldClockedInStaffSearch.getText();
				if (!textFieldClockedInStaffSearch.getText().equals("Type to search...")) {
					if(text.trim().length() == 0) {
						rowSorter.setRowFilter(null);
					} else {
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = textFieldClockedInStaffSearch.getText();

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

		textFieldClockedInStaffSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (boolClockedInStaffSearchPlaceholderText == false) {
					boolClockedInStaffSearchPlaceholderText = true;
					textFieldClockedInStaffSearch.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				boolClockedInStaffSearchPlaceholderText = false;
				textFieldClockedInStaffSearch.setText("Type to search...");
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
		panelStaffClockedInTableSearch.add(textFieldClockedInStaffSearch, gbc);

		// BUTTON
		gbc.gridx++;
		panelStaffClockedInTableSearch.add(searchClearBtn, gbc);

		return panelStaffClockedInTableSearch;
	}

	private void populateStaffClockedInTable() {
		int rows = staffClockedInTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			staffClockedInTableModel.removeRow(i);
		}

		Database.selectClockedInStaff();

		for (int i = 0; i < Database.getStaffClockedInArray().size(); i++) {



			String staffId = Database.getStaffClockedInArray().get(i).getStaffId();
			String firstName = Database.getStaffClockedInArray().get(i).getFirstName();
			String lastName = Database.getStaffClockedInArray().get(i).getLastName();
			String jobTitle = Database.getStaffClockedInArray().get(i).getJobTitle();
			String startTime = Database.getStaffClockedInArray().get(i).getClockInTime();


			Object[] data = {staffId, firstName, lastName, jobTitle, startTime};

			staffClockedInTableModel.addRow(data);
			staffClockedInTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			staffClockedInTable.getSelectionModel().setSelectionInterval(0, 0);
			staffClockedInTable.setColumnSelectionInterval(0, 0);
			staffClockedInTable.requestFocusInWindow();
		}
	}

	private JPanel allStaffTable() {
		JPanel panelAllStaffTable = new JPanel(new BorderLayout());

		allStaffTableModel = new DefaultTableModel(new String[] {
				"STAFF ID", "FIRST NAME", "LAST NAME", "JOB TITLE",
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
					String staffId = allStaffTable.getModel().getValueAt(row, 0).toString();
					setTextFieldStaffId(staffId);
					
				} catch (Exception e) {
					/*
        				JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
        						"Error", JOptionPane.ERROR_MESSAGE);
					 */
				}
			}
		});

		populateAllStaffTable();

		JScrollPane jsp = new JScrollPane(allStaffTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		allStaffTable.setFillsViewportHeight(true);

		panelAllStaffTable.add(jsp, BorderLayout.CENTER);
		panelAllStaffTable.add(allStaffTableControls(), BorderLayout.NORTH);

		TitledBorder border = new TitledBorder("All Staff:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelAllStaffTable.setBorder(border);

		return panelAllStaffTable;
	}

	private JPanel allStaffTableControls() {
		JPanel allStaffTableSearch = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		JButton addStaffBtn = new JButton("Add");
		addStaffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerPasswordDialog addStaff = new ManagerPasswordDialog(frame);
				addStaff.addStaff();
				//new AddStaffDialogGUI(frame);
				populateAllStaffTable();
			}
		});

		JButton editStaffBtn = new JButton("Edit");
		editStaffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton deleteStaffBtn = new JButton("Delete");
		deleteStaffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ManagerPasswordDialog(frame, getSelectedCellValues());
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(frame, "Please select a staff member to delete."
							,"Error", JOptionPane.ERROR_MESSAGE);
				}
				populateAllStaffTable();
			}
		});



		JButton searchClearBtn = new JButton("Clear");
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				textFieldAllStaffSearch.setText("");
			}
		});

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(allStaffTable.getModel());
		allStaffTable.setRowSorter(rowSorter);


		textFieldAllStaffSearch = new JTextField("Type to search...", 20);
		textFieldAllStaffSearch.getDocument().addDocumentListener(new DocumentListener() {
			// Set up search filter function
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textFieldAllStaffSearch.getText();
				if (!textFieldAllStaffSearch.getText().equals("Type to search...")) {
					if(text.trim().length() == 0) {
						rowSorter.setRowFilter(null);
					} else {
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = textFieldAllStaffSearch.getText();

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

		textFieldAllStaffSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (boolAllStaffSearchPlaceholderText == false) {
					boolAllStaffSearchPlaceholderText = true;
					textFieldAllStaffSearch.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				boolAllStaffSearchPlaceholderText = false;
				textFieldAllStaffSearch.setText("Type to search...");
			}

		});


		// SPACING
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		allStaffTableSearch.add(new JLabel(), gbc);







		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		allStaffTableSearch.add(addStaffBtn, gbc);

		gbc.gridx++;
		allStaffTableSearch.add(editStaffBtn, gbc);

		gbc.gridx++;
		allStaffTableSearch.add(deleteStaffBtn, gbc);

		// TEXT FIELD
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		allStaffTableSearch.add(textFieldAllStaffSearch, gbc);

		// BUTTON
		gbc.gridx++;
		allStaffTableSearch.add(searchClearBtn, gbc);

		return allStaffTableSearch;
	}

	private void populateAllStaffTable() {
		int rows = allStaffTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			allStaffTableModel.removeRow(i);
		}

		Database.selectStaff();

		for (int i = 0; i < Database.getStaffArray().size(); i++) {



			String staffId = Database.getStaffArray().get(i).getStaffId();
			String firstName = Database.getStaffArray().get(i).getFirstName();
			String lastName = Database.getStaffArray().get(i).getLastName();
			String jobTitle = Database.getStaffArray().get(i).getJobTitle();
			String lastClockIn = Database.getStaffArray().get(i).getClockInTime();
			String lastClockOut = Database.getStaffArray().get(i).getClockOutTime();


			Object[] data = {staffId, firstName, lastName, jobTitle, lastClockIn, lastClockOut};

			allStaffTableModel.addRow(data);
			allStaffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			allStaffTable.getSelectionModel().setSelectionInterval(0, 0);
			allStaffTable.setColumnSelectionInterval(0, 0);
			allStaffTable.requestFocusInWindow();
		}
	}

	private JPanel staffClockInOutForm() {
		JPanel panelStaffClockInOutFormMain = new JPanel(new BorderLayout());
		JPanel panelStaffClockInOutForm = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		textFieldStaffId = new JTextField(10);
		textFieldStaffId.setPreferredSize(new Dimension(100, 40));

		//Font currentFont = textFieldEmployeeNumber.getFon;
		Font font = new Font("Arial", Font.PLAIN, 16);
		textFieldStaffId.setFont(font);
		//textFieldEmployeeNumber.setText("12345");
		//textFieldEmployeeNumber.setMinimumSize(textFieldEmployeeNumber.getPreferredSize());

		JButton clockInBtn = new JButton("Clock In");
		clockInBtn.setPreferredSize(new Dimension(100, 40));
		clockInBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Staff s = new Staff(textFieldStaffId.getText());
				s.clockIn();
				populateStaffClockedInTable();
				populateAllStaffTable();
				
				
				
			}
		});

		JButton clockOutBtn = new JButton("Clock Out");
		clockOutBtn.setPreferredSize(new Dimension(100, 40));
		clockOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Staff s = new Staff(textFieldStaffId.getText());
					s.clockOut();
					s.updateLastClockOut();
					populateStaffClockedInTable();
					populateAllStaffTable();
					
					
					Database.selectStaffFromId(Integer.parseInt(textFieldStaffId.getText()));
					
					for (int i = 0; i < Database.getStaffArray().size(); i ++) {
						String id = Database.getStaffArray().get(i).getStaffId();
						String firstName = Database.getStaffArray().get(i).getFirstName();
						String lastName = Database.getStaffArray().get(i).getLastName();
						String jobTitle = Database.getStaffArray().get(i).getJobTitle();
						
						Database.unassignTablesOnClockOut(id + " " + firstName + " " + lastName + " " + jobTitle);
					}
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

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
		panelStaffClockInOutForm.add(textFieldStaffId, gbc);

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



	private static ArrayList<String> getSelectedCellValues() {
		ArrayList<String> selectedCellValuesArray = new ArrayList<String>();
		int row = allStaffTable.getSelectedRow();
		String employeeNumber = allStaffTable.getModel().getValueAt(row, 0).toString();
		String firstName = allStaffTable.getModel().getValueAt(row, 1).toString();
		String lastName = allStaffTable.getModel().getValueAt(row, 2).toString();
		String jobTitle = allStaffTable.getModel().getValueAt(row, 3).toString();

		selectedCellValuesArray.add(employeeNumber);
		selectedCellValuesArray.add(firstName);
		selectedCellValuesArray.add(lastName);
		selectedCellValuesArray.add(jobTitle);

		return selectedCellValuesArray;
	}
	
	private void setTextFieldStaffId(String id) {
		textFieldStaffId.setText(id);
	}

	public JPanel getStaffPanel() {
		return panelStaffMain;
	}
}
