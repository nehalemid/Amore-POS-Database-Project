package projectBAD;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.nio.file.ProviderNotFoundException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class Admin extends JFrame implements ActionListener {

	JPanel panel_north = new JPanel(new BorderLayout());
	JMenuBar menubar = new JMenuBar();
	JMenu account = new JMenu("Account");
	JMenu manage = new JMenu("Manage");
	JMenuItem acc_logout = new JMenuItem("Log Out");
	JMenuItem manage_accounts = new JMenuItem("Accounts");
	JMenuItem manage_restaurantMenu = new JMenuItem("Restaurant Menu");
	JDesktopPane desktop = new JDesktopPane();
	JFrame amorepos = new JFrame("Amore POS");
	
	public Admin()  {
		this.setTitle("Admin Main Menu");
		menubar.add(account);
		menubar.add(manage);
		
		account.add(acc_logout);

		manage.add(manage_accounts);
		manage.add(manage_restaurantMenu);


		manage_accounts.addActionListener(this);
		manage_restaurantMenu.addActionListener(this);
		acc_logout.addActionListener(this);



		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel_north.add(menubar,BorderLayout.NORTH);
		add(panel_north);
		setContentPane(desktop);
		setJMenuBar(menubar);
		setSize(1000,800);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == manage_accounts){
			desktop.add(new manage_accounts());
		}
		if (e.getSource() == manage_restaurantMenu) {
			desktop.add(new manage_restaurantMenu());
		}
		if((e.getSource() == acc_logout)) {
			dispose();
		new Login();
		}
	}
}
