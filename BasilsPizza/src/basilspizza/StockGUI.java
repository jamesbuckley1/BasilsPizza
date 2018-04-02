package basilspizza;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class StockGUI {

	private static JFrame frame;
	
	private JPanel panelStockMain;
	private JTable stockTable;
	private JPanel panelStockTable;
	private DefaultTableModel stockTableModel;
	
	public StockGUI() {
		// Get frame of panelStockTable
		frame = (JFrame)SwingUtilities.getRoot(panelStockTable);
		initGUI();
	}

	// Set up main GUI components.
	private void initGUI(){
		
		// Check if running on event dispatch thread.
		if (SwingUtilities.isEventDispatchThread()) { 
		    System.err.println("Is running on EDT");
		} else {
		    System.err.println("Is not running on EDT");
		}
		
		panelStockMain = new JPanel();
		JPanel panelStockButtons = new JPanel(); // Panel for buttons.
		panelStockTable = new JPanel(new BorderLayout()); // Panel for table.

		panelStockMain.setLayout(new BorderLayout());

		stockTableModel = new DefaultTableModel(new String[]{"Item", "Price", "Quantity"}, 0);

		stockTable = new JTable(stockTableModel) {

			// Disables editing of table
			public boolean isCellEditable(int row, int columns) {
				return false;
			}

			// Makes every other row a different colour for readability
			public Component prepareRenderer(TableCellRenderer r, int row, int columns) {
				Component c = super.prepareRenderer(r, row, columns);

				if (row % 2 == 0) {
					c.setBackground(Color.WHITE);
				}
				else {
					c.setBackground(new Color(234, 234, 234));
				}

				if (isRowSelected(row)) {
					c.setBackground(new Color(24, 134, 254));
				}

				return c;
			}
		};

		//stockTable font and cell height
		stockTable.setFont(new Font("", 0, 14));
		stockTable.setRowHeight(stockTable.getRowHeight() + 10);
		stockTable.setAutoCreateRowSorter(true);
		stockTable.setFillsViewportHeight(true);

		// Stock table mouse listener
		stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					//editItem(); // OPEN EDIT ITEM DIALOG ON DOUBLE CLICK
				}
			}
		});

		// Add stock items to stock table
		populateStockTable();	

		// Buttons
		JButton addBtn = new JButton();
		addBtn.setText("Add");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				AddStockDialogGUI a = new AddStockDialogGUI(frame);
				populateStockTable();
			}
		});

		JButton editBtn = new JButton();
		editBtn.setText("Edit");
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
					try {
					AddStockDialogGUI a = new AddStockDialogGUI(frame, getTextFieldValues());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				populateStockTable();
			}
		});

		JButton deleteBtn = new JButton();
		deleteBtn.setText("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					int row = stockTable.getSelectedRow();
					String cellData = stockTable.getModel().getValueAt(row, 0).toString();
					System.out.println(cellData);
					Database.deleteStock(cellData);
					populateStockTable();
				} catch (Exception e) {
					JOptionPane.showMessageDialog((JFrame) SwingUtilities.getRoot(stockTable), "Please select an item to delete.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Add buttons to buttons panel
		panelStockButtons.add(addBtn);
		panelStockButtons.add(editBtn);
		panelStockButtons.add(deleteBtn);

		JScrollPane jsp = new JScrollPane(stockTable);

		// Add scroll pane and button panel to stock table panel
		panelStockTable.add(jsp, BorderLayout.CENTER);
		panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);

		// Add stock table panel to main panel
		panelStockMain.add(panelStockTable, BorderLayout.CENTER);
		
	}

	// Clears stockTableModel then retrieves fresh data
	 
	private void populateStockTable() {
		int rows = stockTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			stockTableModel.removeRow(i);
		}

		Database.selectStock();

		// Loop through result set ArrayList and adds to new array which can be used by TableModel
		for (int i = 0; i < Database.getStockArray().size(); i++) {
			String item = Database.getStockArray().get(i).getItem();
			String price = Stock.getFormattedPrice(Database.getStockArray().get(i).getPrice()); 
			String quantity = Database.getStockArray().get(i).getQuantity();

			Object[] data = {item, price, quantity};

			stockTableModel.addRow(data);
		}
	}

	private ArrayList<String> getTextFieldValues() {
		int row = stockTable.getSelectedRow();
		String cellDataItem = stockTable.getModel().getValueAt(row, 0).toString();
		String cellDataPrice = stockTable.getModel().getValueAt(row, 1).toString();
		String cellDataQuantity = stockTable.getModel().getValueAt(row, 2).toString();
		
		System.out.println("getTextFieldValues " + cellDataItem + cellDataPrice + cellDataQuantity);
		
		ArrayList<String> textFieldValuesArray = new ArrayList<String>();
		textFieldValuesArray.add(cellDataItem);
		textFieldValuesArray.add(cellDataPrice);
		textFieldValuesArray.add(cellDataQuantity);

		return textFieldValuesArray;
	}

	public JPanel getStockPanel() {
		return panelStockMain;
	}

}
