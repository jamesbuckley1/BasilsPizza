package basilspizza;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddTableDialogGUI {

	private JFrame frame;
	private JDialog dialogAddTable;
	private JTextField textFieldTableName;

	public AddTableDialogGUI(JFrame frame) {
		this.frame = frame;
		initDialog();
	}

	public void initDialog() {
		dialogAddTable = new JDialog();
		JPanel panelMain = new JPanel(new BorderLayout());
		
		panelMain.add(addTableDialogForm(), BorderLayout.CENTER);
		panelMain.add(addTableDialogButtons(), BorderLayout.SOUTH);
		
		dialogAddTable.add(panelMain);
		dialogAddTable.setTitle("Add Table");
		dialogAddTable.setModal(true); 
		dialogAddTable.pack();
		dialogAddTable.setLocationRelativeTo(frame);
		dialogAddTable.setVisible(true);

	}

	public JPanel addTableDialogForm() {
		//JPanel panelFormMain = new JPanel(new BorderLayout());
		JPanel panelForm = new JPanel(new GridBagLayout());

		textFieldTableName = new JTextField(20);

		GridBagConstraints gbc = new GridBagConstraints();


		gbc.gridx = 0;
		gbc.gridy = 0;
		panelForm.add(new JLabel("Table name: "), gbc);

		gbc.gridx++;
		panelForm.add(textFieldTableName, gbc);

		panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		return panelForm;
	}

	public JPanel addTableDialogButtons() {
		//JPanel panelButtonsMain = new JPanel(new BorderLayout());
		JPanel panelButtons = new JPanel(new GridBagLayout());
		
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogAddTable.dispose();
			}
		});
		
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.insertTable(textFieldTableName.getText());
				
				dialogAddTable.dispose();
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelButtons.add(new JLabel(), gbc);
		
		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panelButtons.add(buttonCancel);
		
		gbc.gridx++;
		panelButtons.add(buttonOK);
		
		return panelButtons;
	}

}
