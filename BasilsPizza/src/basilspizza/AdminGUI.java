package basilspizza;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class AdminGUI {
	
	JPanel panelAdminMain;
	
	public AdminGUI() {
		panelAdminMain = new JPanel(new BorderLayout());
		JPanel panelAdminMainGrid = new JPanel(new GridLayout(1, 2));
		JPanel panelMenuGrid = new JPanel(new GridLayout(2, 1));
		JPanel panelStaffGrid = new JPanel(new GridLayout(2, 1));
		
		panelAdminMainGrid.add(panelMenuGrid);
		panelAdminMainGrid.add(panelStaffGrid);
		
		panelMenuGrid.add(menuAdmin());
		//panelMenuGrid.add(staffAdmin());
		
	}
	
	public JPanel menuAdmin() {
		JPanel panelMenuMain = new JPanel(new BorderLayout());
		//JPanel panelMenu = new JPanel(new GridBagLayout());
		
		
		
		
		return panelMenuMain;
	}
	
	public JPanel getAdminPanel() {
		return panelAdminMain;
	}

}
