import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

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
	
	private static JTable stockTable;
	private static JPanel panelStockTable;
	private static DefaultTableModel stockTableModel;
	
	public StockGUI() {
		frame = (JFrame)SwingUtilities.getRoot(panelStockTable);
	}

	private JPanel stockPanel(){
		JPanel panelStockMain = new JPanel();
		panelStockMain.setLayout(new BorderLayout());

		JPanel panelStockGrid = new JPanel(new GridLayout(1, 2));

		panelStockTable = new JPanel(new BorderLayout());
		JPanel panelStockForm = new JPanel(new GridBagLayout());



		JPanel panelStockButtons = new JPanel();

		//JTable stockTable = new JTable();

		stockTableModel = new DefaultTableModel(new String[]{"Item", "Price", "Quantity"}, 0);

		stockTable = new JTable(stockTableModel) {

			//Disables editing of table
			public boolean isCellEditable(int row, int columns) {
				return false;
			}


			// Makes every other row a different colour for readability
			public Component prepareRenderer(TableCellRenderer r, int row, int columns) {
				Component c = super.prepareRenderer(r, row, columns);



				//String cellData = stockTable.getModel().getValueAt(row, 0).toString();

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



		// Stock table mouse listener
		stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					editItem(); // OPEN EDIT ITEM DIALOG ON DOUBLE CLICK
				}



			}
		});

		// Loop through result set ArrayList and adds to new array which can be used by TableModel


		populateStockTable();	

		//stockTable.setPreferredScrollableViewportSize(new Dimension(450, 350)); // 450, 63 - original size
		stockTable.setFillsViewportHeight(true);

		JScrollPane jsp = new JScrollPane(stockTable);







		// Buttons

		JButton addBtn = new JButton();
		addBtn.setText("Add");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new AddStockGUI();
				System.out.println("Add Stock GUI loaded");
				
				populateStockTable();


			}
		});

		JButton editBtn = new JButton();
		editBtn.setText("Edit");
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				editItem();
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

		panelStockButtons.add(addBtn);
		panelStockButtons.add(editBtn);
		panelStockButtons.add(deleteBtn);

		panelStockTable.add(jsp, BorderLayout.CENTER);
		panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);


		panelStockMain.add(panelStockTable, BorderLayout.CENTER);
		//panelStockMain.add(panelStockTable, BorderLayout.CENTER);
		//panelStockTable.add(panelStockButtons, BorderLayout.SOUTH);

		return panelStockMain;
	}

	public void editItem() {
		try {
			int row = stockTable.getSelectedRow();
			String cellDataItem = stockTable.getModel().getValueAt(row, 0).toString();
			String cellDataPrice = stockTable.getModel().getValueAt(row, 1).toString();
			String cellDataQuantity = stockTable.getModel().getValueAt(row, 2).toString();
			
			//AddStock editStock = new AddStock(cellDataItem, cellDataPrice, cellDataQuantity);
			//editStock.splitPrice();
			//editStock.validate();
			//editStock.addStock();
			
		} catch (Exception e){
			JOptionPane.showMessageDialog(frame, "Please select an item to edit.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void populateStockTable() {
    	int rows = stockTableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i --) {
			stockTableModel.removeRow(i);
		}
    		
    		Database.selectStock();
    		
		for (int i = 0; i < Database.getStockArray().size(); i++) {
			
			
			String item = Database.getStockArray().get(i).getItem();
			String price = Database.getStockArray().get(i).getPrice(); 
			String quantity = Database.getStockArray().get(i).getQuantity();
			
			
			Object[] data = {item, price, quantity};
			//Arrays.sort(data);
				
			
			stockTableModel.addRow(data);
			
			/*
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(stockTable.getModel());
			stockTable.setRowSorter(sorter);
			
			List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
			sorter.setSortKeys(sortKeys);
			*/
			}
	}
	
	public JPanel getPanel() {
		
		return stockPanel();
	}

}
