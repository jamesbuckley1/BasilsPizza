import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

public class AddStockGUI {
	
	private JFrame frame;
	private JTextField textFieldItem, textFieldPricePounds, textFieldPricePence, textFieldQuantity;
	private JDialog dialogAddStock;

	private boolean boolPricePoundsPlaceholderText;
	private boolean boolPricePencePlaceholderText;
	private boolean boolQuantityPlaceholderText;
	
	public AddStockGUI() {
		frame = (JFrame)SwingUtilities.getRoot(dialogAddStock);
		boolPricePoundsPlaceholderText = false;
		boolPricePencePlaceholderText = false;
		boolQuantityPlaceholderText = false;
		createDialog();
	}
	
	public void createDialog() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		JPanel panelForm = new JPanel();
		JPanel panelButtons = new JPanel(new FlowLayout());
		dialogAddStock = new JDialog();

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
				//System.out.println("Focus lost");
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
					String item = textFieldItem.getText();
					String pricePounds = textFieldPricePounds.getText();
					String pricePence = textFieldPricePence.getText();
					String quantity = textFieldQuantity.getText();
					
					Stock s = new Stock(item, pricePounds, pricePence, quantity);
					
					if (s.validateItem() && s.validatePrice() && s.validateQuantity()) {
						s.addStockToDatabase();
						dialogAddStock.dispose();
					} else if (!s.validateItem()){
						JOptionPane.showMessageDialog(frame,  "Invalid item.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (!s.validatePrice()){
						JOptionPane.showMessageDialog(frame,  "Invalid price.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (!s.validateQuantity()) {
						JOptionPane.showMessageDialog(frame,  "Invalid quantity.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (Exception e) {
					Database.closeDB();


					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Failed to insert into database.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}

				//GUI.populateStockTable();
			}
		});

		panelButtons.add(buttonCancel);
		panelButtons.add(buttonOk);

		panelButtons.add(buttonCancel);
		panelButtons.add(buttonOk);

		panelMain.add(panelForm, BorderLayout.CENTER);
		panelMain.add(panelButtons, BorderLayout.SOUTH);

		/*// SET TITLE OF DIALOG - GOES IN LOGIC CLASS
		if (editFlag) {
			dialogAddStock.setTitle("Edit Stock");
		} else {
			dialogAddStock.setTitle("Add Stock");
		}
		*/
		
		dialogAddStock.add(panelMain);
		dialogAddStock.setSize(300, 180);
		dialogAddStock.setModal(true); // Always on top
		dialogAddStock.setLocationRelativeTo(frame);
		dialogAddStock.setVisible(true);
		
		//return dialogAddStock;
	}
	
	public String getItemTextField() {
		return textFieldItem.getText();
	}
	
	public void setItemTextField(String text) {
		textFieldItem.setText(text);
		
		/*
		// SET ITEM FIELD TEXT
				//textFieldItem = new JTextField();
				if (editFlag) {
					textFieldItem.setText(currentItem);
				} else {
					//textFieldItem.setText("0");
				}
		*/
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
