package projectBAD;
import java.awt.BorderLayout;
import java.awt.Window;
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

public class Cashier extends JFrame implements ActionListener {

	JPanel panel_north = new JPanel(new BorderLayout());
	JMenuBar menubar = new JMenuBar();
	JMenu account = new JMenu("Account");
	JMenu transaction = new JMenu("Transaction");
	JMenuItem acc_logout = new JMenuItem("Log Out");
	JMenuItem tr_createTransaction = new JMenuItem("Create Transaction");
	JInternalFrame frame;
	JDesktopPane desktop = new JDesktopPane();
	
	public Cashier() {
		this.setTitle("Admin Main Menu");
		menubar.add(account);
		menubar.add(transaction);
		
		account.add(acc_logout);
		transaction.add(tr_createTransaction);

		tr_createTransaction.addActionListener(this);
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
		if(e.getSource() == tr_createTransaction){
			desktop.add(new tr_createTransaction());
		}
		
		if((e.getSource() == acc_logout)) {
			dispose();
			new Login();
		}
	}
}
