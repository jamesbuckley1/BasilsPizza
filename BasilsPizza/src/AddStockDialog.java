import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddStockDialog {
	
	//SQLite error codes
	private static final int SQLITE_CONSTRAINT_PRIMARYKEY = 19;

	private JFrame frame;
	private JTextField textFieldItem, textFieldPricePounds, textFieldPricePence, textFieldQuantity;
	private JDialog dialogAddStock;

	// Boolean variables used for automatically replacing text in JTextFields when clicked on
	private boolean boolPricePoundsPlaceholderText = false;
	private boolean boolPricePencePlaceholderText = false;
	private boolean boolQuantityPlaceholderText = false;

	// EDIT STOCK ITEM VARIABLES - Used to set text on JTextFields when editing stock items
	private boolean editFlag = false;
	private String item = "";
	private String pricePounds = "";
	private String pricePence = "";
	private String quantity = "";

	public AddStockDialog(JFrame frame) {
		this.frame = frame;
	}
	// Constructor for edit stock item
	public AddStockDialog(JFrame frame, ArrayList<String> textFieldValuesArray) {
		this.frame = frame;
		editFlag = true;
		item = textFieldValuesArray.get(0);
		String[] splitPrice = Stock.splitPrice(textFieldValuesArray.get(1));
		pricePounds = splitPrice[0];
		pricePence = splitPrice[1];
		quantity = textFieldValuesArray.get(2);
	}

	public void createDialog() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		JPanel panelForm = new JPanel();
		JPanel panelButtons = new JPanel(new FlowLayout());

		dialogAddStock = new JDialog();
		//frame = (JFrame)SwingUtilities.getRoot(dialogAddStock);

		GroupLayout layout = new GroupLayout(panelForm);
		layout.setAutoCreateGaps(true);

		panelForm.setLayout(layout);

		JLabel labelItem = new JLabel(" Item:");
		JLabel labelPrice = new JLabel(" Price:");
		JLabel labelQuantity = new JLabel(" Quantity:");
		JLabel labelDot = new JLabel(".");

		textFieldItem = new JTextField();

		textFieldPricePounds = new JTextField();

		// FOCUS LISTENER ON TEXT FIELDS TO REMOVE PLACEHOLDER TEXT ON CLICK
		textFieldPricePounds.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (boolPricePoundsPlaceholderText == false) {
					boolPricePoundsPlaceholderText = true;
					setTextFieldPricePounds("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus lost");
			}

		});

		textFieldPricePence = new JTextField();

		textFieldPricePence.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (boolPricePencePlaceholderText == false) {
					boolPricePencePlaceholderText = true;
					setTextFieldPricePence("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus lost");
			}

		});

		textFieldQuantity = new JTextField();

		textFieldQuantity.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

				if (boolQuantityPlaceholderText == false) {
					boolQuantityPlaceholderText = true;
					textFieldQuantity.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				
			}

		});

		// GROUP LAYOUT 
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(labelItem)
						.addComponent(textFieldItem))
				.addGroup(layout.createSequentialGroup()
						.addComponent(labelPrice)
						.addComponent(textFieldPricePounds)
						.addComponent(labelDot)
						.addComponent(textFieldPricePence))
				.addGroup(layout.createSequentialGroup()
						.addComponent(labelQuantity)
						.addComponent(textFieldQuantity)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(labelItem)
						.addComponent(textFieldItem))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(labelPrice)
						.addComponent(textFieldPricePounds)
						.addComponent(labelDot)
						.addComponent(textFieldPricePence))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(labelQuantity)
						.addComponent(textFieldQuantity)));

		// BUTTONS
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialogAddStock.dispose();
			}
		});

		JButton buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String currentItem = item;
					String item = textFieldItem.getText();
					String pricePounds = textFieldPricePounds.getText();
					String pricePence = textFieldPricePence.getText();
					String quantity = textFieldQuantity.getText();

					Stock s = new Stock(item, pricePounds, pricePence, quantity);

					if (s.validateItem() && s.validatePrice() && s.validateQuantity()) {
						if (editFlag) {
							s.editStockToDatabase(currentItem);
						} else {
						s.addStockToDatabase();
						}
						dialogAddStock.dispose();
					} else if (!s.validateItem()){
						JOptionPane.showMessageDialog(frame, "Invalid item.",
								"Invalid Entry", JOptionPane.ERROR_MESSAGE);
					} else if (!s.validatePrice()){
						JOptionPane.showMessageDialog(frame,  "Invalid price.",
								"Invalid Entry", JOptionPane.ERROR_MESSAGE);
					} else if (!s.validateQuantity()) {
						JOptionPane.showMessageDialog(frame,  "Invalid quantity.",
								"Invalid Entry", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e) {
					if (e.getErrorCode() == SQLITE_CONSTRAINT_PRIMARYKEY) {
						JOptionPane.showMessageDialog(frame,  "Item already exists.",
								"Duplicate Entry", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					Database.closeDB();
					JOptionPane.showMessageDialog(frame, "Failed to insert into database.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		panelButtons.add(buttonCancel);
		panelButtons.add(buttonOk);

		panelButtons.add(buttonCancel);
		panelButtons.add(buttonOk);

		panelMain.add(panelForm, BorderLayout.CENTER);
		panelMain.add(panelButtons, BorderLayout.SOUTH);


		if (editFlag) {
			dialogAddStock.setTitle("Edit Stock");
			textFieldItem.setText(item);
			textFieldPricePounds.setText(pricePounds);
			textFieldPricePence.setText(pricePence);
			textFieldQuantity.setText(quantity);
		} else {
			dialogAddStock.setTitle("Add Stock");
		}

		dialogAddStock.add(panelMain);
		dialogAddStock.setSize(300, 180);
		dialogAddStock.setModal(true); // Always on top
		dialogAddStock.setLocationRelativeTo(frame); // Open dialog in middle of frame
		dialogAddStock.setVisible(true);


	}

	public String getItemTextField() {
		return textFieldItem.getText();
	}

	public void setItemTextField(String text) {
		textFieldItem.setText(text);

	}

	public String getTextFieldPricePounds() {
		return textFieldPricePounds.getText();
	}

	public void setTextFieldPricePounds(String text) {
		textFieldPricePounds.setText(text);
	}

	public String getTextFieldPricePence() {
		return textFieldPricePence.getText();
	}

	public void setTextFieldPricePence(String text) {
		textFieldPricePence.setText(text);
	}

	public String getTextFieldQuantity() {
		return textFieldQuantity.getText();
	}

	public void setTextFieldQuantity(String text) {
		textFieldQuantity.setText(text);
	}


}
