package projectBAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;
import com.sun.media.sound.ModelAbstractChannelMixer;


public class fi_viewMonthlyReport extends JInternalFrame implements ActionListener {
	
	JPanel panel_north = new JPanel(new GridLayout(2, 3, 10, 20));
	JPanel panel_center = new JPanel(new BorderLayout());
	
	JLabel month = new JLabel("Month");
	JLabel year = new JLabel("Year");
	
	JTextField monthInput = new JTextField(2);
	JTextField yearInput = new JTextField(4);
	
	JButton view = new JButton("View");
	
	DefaultTableModel dtm;
	JTable salesReport = new JTable();
	Vector<Object>tRow,tHeader;

	Vector<th> tTemp = new Vector<th>();
	
	Connect_db con = new Connect_db();
	
	boolean notnull = false;
	boolean numeric = false;
	boolean inrange = false;
	
	public fi_viewMonthlyReport() {
		
		String title = "Finance Report";
		setTitle(title);
		
		panel_north.add(month);
		panel_north.add(monthInput);
		panel_north.add(view);
		panel_north.add(year);
		panel_north.add(yearInput);
		
		panel_north.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_center.setBorder(new EmptyBorder(20, 10, 20 ,10));
		
		view.addActionListener(this);
		
		table();
		
		add(panel_north,"North");
		add(panel_center,"Center");
		
		setTitle("Manage Finance Report");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,800);
		setResizable(false);
		setVisible(true);
		setMaximizable(true);
		setClosable(true);
	}
	
	public static boolean numericCheck(String number) {

		String num = "";

		for (char a : number.toCharArray()) {
			if (a >= 48 && a <= 57) {
				num += Character.toString(a);
			}
		}

		if (num.length() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void view() {
		
	String transactionID = "";
	String quantity = "";
	String ingredientPrice = "";
	String sellPrice = "";

	int modal = 0;
	int earn = 0;
	int gain = 0;

	String month = monthInput.getText();
	String year = yearInput.getText();
	
		if (month.equals("") || year.equals("")) {
			JOptionPane.showMessageDialog (null,"Month and Year must be not null","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		
		else {
			notnull = true;
		}
		if (numericCheck(month) == false || numericCheck(year) == false) {
			JOptionPane.showMessageDialog (null,"Month and Year must be a number","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			numeric = true;
		}
		if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
			JOptionPane.showMessageDialog (null,"Month must be between 1-12","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			inrange = true;
		}

		if (notnull == true && numeric == true && inrange == true) {
			
			con.rs = con.executeQuery(
					"SELECT * FROM transactionheader, transactiondetail, menu WHERE transactionheader.transactionid = transactiondetail.transactionid AND transactiondetail.menuid = menu.menuid AND month(transactiondate) = '"
							+ Integer.parseInt(month) + "' AND YEAR(transactiondate) = '" + Integer.parseInt(year)
							+ "'");
			try {
				while (con.rs.next()) {
					try {
						transactionID = con.rs.getString("transactionid").toString();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}

					try {
						quantity = con.rs.getString("quantity");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}

					try {
						ingredientPrice = con.rs.getString("ingredientprice");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}

					try {
						sellPrice = con.rs.getString("sellprice");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
						int qty = Integer.parseInt(quantity);
						int ingPrice = Integer.parseInt(ingredientPrice);
						int slPrice = Integer.parseInt(sellPrice);
						
						modal = qty * ingPrice;
						earn = qty * slPrice;
						gain = earn - modal;
							tTemp.add(new th(transactionID, modal, earn, gain));
					}
					
					boolean[] check = new boolean[tTemp.size()];
					Arrays.fill(check, Boolean.FALSE);
					for (int i = 0; i < tTemp.size(); i++) {
						if (check[i] == false) {
							
							tRow = new Vector<Object>();
							tRow.add(tTemp.get(i).getTransactionID());
							tRow.add(tTemp.get(i).getModal());
							tRow.add(tTemp.get(i).getEarn());
							tRow.add(tTemp.get(i).getGain());
							dtm.addRow(tRow);
							salesReport.setModel(dtm);
								
						}
					}
				}
				
		catch(Exception e) {
			
		}
	}

}
	
	public void initTable()
		{
			tHeader = new Vector<Object>();
			tHeader.add("Transaction ID");
			tHeader.add("Modal");
			tHeader.add("Earn");
			tHeader.add("Gain");
		}	
	
	public void table()
	{
		initTable();
		dtm = new DefaultTableModel(tHeader, 0);
		salesReport = new JTable(dtm)
		{
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		JScrollPane pane = new JScrollPane(salesReport,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(50, 100));
		panel_center.add(pane,"Center");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(view)) {
			dtm.setRowCount(0);
			tTemp.clear();
			view();
			}
		}
	}
	
