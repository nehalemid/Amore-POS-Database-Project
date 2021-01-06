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

public class Accountant extends JFrame implements ActionListener {

	JPanel panel_north = new JPanel(new BorderLayout());
	JMenuBar menubar = new JMenuBar();
	JMenu account = new JMenu("Account");
	JMenu finance = new JMenu("Finance");
	JMenuItem acc_logout = new JMenuItem("Log Out");
	JMenuItem fi_viewMonthlyReport = new JMenuItem("View Monthly Report");
	JInternalFrame frame;
	JDesktopPane desktop = new JDesktopPane();
	
	public Accountant() {
		this.setTitle("Admin Main Menu");
		menubar.add(account);
		menubar.add(finance);
		
		account.add(acc_logout);

		finance.add(fi_viewMonthlyReport);
		
		fi_viewMonthlyReport.addActionListener(this);
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
		if(e.getSource() == fi_viewMonthlyReport){
			desktop.add(new fi_viewMonthlyReport());
		}
		if((e.getSource() == acc_logout)) {
			dispose();
			new Login();
		}
	}
}

