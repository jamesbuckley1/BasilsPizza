package basilspizza;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class MenuGUI {
	
	JPanel panelMenuMain;
	private JTable pizzaMenuTable;
	private DefaultTableModel pizzaMenuTableModel;
	
	public MenuGUI() {
		panelMenuMain = new JPanel(new BorderLayout());
		JPanel panelMenuMainGrid = new JPanel(new GridLayout(1, 2));
		//JPanel panelMenuGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelFormGrid = new JPanel(new GridLayout(2, 1));
		
		//panelMenuMainGrid.add(selectMenu());
		//panelMenuMainGrid.add(panelFormGrid);
		
		NewOrderGUI n = new NewOrderGUI();
		panelMenuMainGrid.add(n.selectMenu());
		panelMenuMainGrid.add(new JLabel("TEST"));
		//panelMenuGrid.add(staffMenu());
		
		panelMenuMain.add(panelMenuMainGrid, BorderLayout.CENTER);
		
	}
	
	public JPanel selectMenu() {
		JPanel panelMenuSelect = new JPanel(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Pizza", pizzaMenu());
		//tabbedPane.add("Sides", sidesMenu());
		//tabbedPane.add("Drinks", drinksMenu());
		//tabbedPane.add("Desserts", dessertsMenu());
		
		TitledBorder border = new TitledBorder("Menu:");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		tabbedPane.setBorder(border);

		//panelSelectMenu.add(tabbedPane, BorderLayout.CENTER);
		
		
		
		
		return panelMenuSelect;
		
		
	}
	
	public JPanel pizzaMenu() {
		JPanel panelMenuMain = new JPanel(new BorderLayout());
		//JPanel panelMenu = new JPanel(new GridBagLayout());
		
		pizzaMenuTable = new JTable();
		pizzaMenuTableModel = new DefaultTableModel();
		
		pizzaMenuTableModel = new DefaultTableModel(new String[] {
				"Menu Item", "Price", "Quantity"
		}, 0);

		pizzaMenuTable = new JTable(pizzaMenuTableModel ) {
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


		//populatePizzaMenuTable();



		JScrollPane jsp = new JScrollPane(pizzaMenuTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pizzaMenuTable.setFillsViewportHeight(true);
		
		
		return panelMenuMain;
	}
	
	public JPanel getMenuPanel() {
		return panelMenuMain;
	}

}
