package basilspizza;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class AddStaffDialogGUI {

	JFrame frame;
	
	private JTextField textFieldEmployeeNumber;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldJobTitle;

	public AddStaffDialogGUI(JFrame frame) {
		this.frame = frame;
		initDialog();
	}


	public void initDialog() {
		JDialog dialogAddStaff = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		JPanel panelForm = new JPanel(new GridBagLayout());
		JPanel panelButtons = new JPanel(new GridBagLayout());
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel labelEmployeeNumber = new JLabel("Employee no: ");
		JLabel labelFirstName = new JLabel("First name: ");
		JLabel labelLastName = new JLabel("Last name: ");
		JLabel labelJobTitle = new JLabel("Job Title: ");
		
		//JTextField textFieldEmployeeNumber = new JTextField(15);
		JTextField textFieldFirstName = new JTextField(15);
		JTextField textFieldLastName = new JTextField(15);
		JTextField textFieldJobTitle = new JTextField(15);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogAddStaff.dispose();
			}
		});
		
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String employeeNumber = textFieldEmployeeNumber.getText().toUpperCase();
				String firstName = textFieldFirstName.getText().toUpperCase();
				String lastName = textFieldLastName.getText().toUpperCase();
				String jobTitle = textFieldJobTitle.getText().toUpperCase();
				
				Staff s = new Staff(firstName, lastName, jobTitle);
				
				System.out.println("ADDING STAFF TO DATABASE");
				try {
					s.addNewStaffToDatabase();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				dialogAddStaff.dispose();
			}
		});
		
		
		// LABELS
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		//gbc.insets = new Insets(10,10,10,10);
		//panelForm.add(labelEmployeeNumber, gbc);
		
		//gbc.gridy++;
		panelForm.add(labelFirstName, gbc);
		
		gbc.gridy++;
		panelForm.add(labelLastName, gbc);
		
		gbc.gridy++;
		panelForm.add(labelJobTitle, gbc);
		
		// SPACING
		gbc.gridx = 20;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelForm.add(new JLabel(), gbc);
		
		// TEXT FIELDS
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		//panelForm.add(textFieldEmployeeNumber, gbc);
		
		//gbc.gridy++;
		panelForm.add(textFieldFirstName, gbc);
		
		gbc.gridy++;
		panelForm.add(textFieldLastName, gbc);
		
		gbc.gridy++;
		panelForm.add(textFieldJobTitle, gbc);
		
		// BUTTONS
		
		
		GridBagConstraints gbcBtn = new GridBagConstraints();
		
		// SPACING
		gbcBtn.gridx = 0;
		gbcBtn.gridy = 20;
		gbcBtn.weightx = 1.0;
		gbcBtn.weighty = 1.0;
		panelButtons.add(new JLabel(), gbcBtn);
		
		gbcBtn.gridx++;
		gbcBtn.weightx = 0;
		gbcBtn.weighty = 0;
		panelButtons.add(cancelBtn, gbcBtn);
		
		gbcBtn.gridx++;
		panelButtons.add(okBtn, gbcBtn);
		
		panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		TitledBorder border = new TitledBorder("Add Staff: ");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		panelMain.setBorder(border);
		
		panelMain.add(panelForm, BorderLayout.CENTER);
		panelMain.add(panelButtons, BorderLayout.SOUTH);
		
		dialogAddStaff.add(panelMain);
		dialogAddStaff.setTitle("Add Staff");
		
		
		dialogAddStaff.add(panelMain);
		dialogAddStaff.setModal(true); 
		dialogAddStaff.pack();
		dialogAddStaff.setLocationRelativeTo(frame);
		dialogAddStaff.setVisible(true);
		
	}

}
