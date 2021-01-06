package projectBAD;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class manage_accounts extends JInternalFrame implements ActionListener, MouseListener {
	
	Random rand = new Random();
	
	int flag = 0;
	boolean notnull = false;
	boolean passwordvalidator = false; 
	boolean emailformat = false;
	boolean duplicate = false;
	
	JPanel panel_north = new JPanel(new GridLayout(5, 3, 10, 20));
	JPanel panel_center = new JPanel(new BorderLayout());
		
	JLabel idTitle = new JLabel("ID");
	JLabel userID = new JLabel("User ID");
	JLabel fullnameTitle = new JLabel("Fullname");
	JLabel roleTitle = new JLabel("Role");
	JLabel emailTitle = new JLabel("Email");
	JLabel passwordTitle = new JLabel("Password");
	JLabel notexist = new JLabel("");
	JLabel notexist2 = new JLabel("");

	
	JButton btn_insert = new JButton("Insert");
	JButton btn_update = new JButton("Update");
	JButton btn_delete = new JButton("Delete");
	
	String[] role = { "Accountant", "Admin", "Cashier"};
	JComboBox roles = new JComboBox(role);
	
	JTextField fullname = new JTextField(50);
	JTextField email = new JTextField(50);
	JTextField password = new JTextField(50);
	
	Font font = new Font("Calibri", 0, 35);
	
	JTable table = new JTable();
	DefaultTableModel dtm;
	Vector<Object>tRow,tHeader;
	Connect_db con = new Connect_db();
	
	public void initTable()
	{
		tHeader = new Vector<Object>();
		tHeader.add("ID");
		tHeader.add("Fullname");
		tHeader.add("Role");
		tHeader.add("Email");
		tHeader.add("Password");
		
	}
	
	public void fillData() {
		dtm = new DefaultTableModel(tHeader, 0);
		//executequery pakai con.rs
		//query untuk memasukan data ke tabel
		con.rs = con.executeQuery("SELECT * FROM users");
		try 
		{
			while (con.rs.next()) 
			{
			//untuk isi data kedalam tabel
				tRow = new Vector<Object>();
				tRow.add(con.rs.getString("userid"));
				tRow.add(con.rs.getString("fullname"));
				tRow.add(con.rs.getString("role"));
				tRow.add(con.rs.getString("email"));
				tRow.add(con.rs.getString("password"));
				dtm.addRow(tRow);
			}
			table.setModel(dtm);
		} 
		catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void table()
	{
		
		table = new JTable(dtm)
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		initTable();
		fillData();
		JScrollPane pane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(50, 100));
		table.addMouseListener(this);
		panel_center.add(pane,"Center");
	}
	
	public manage_accounts(){
		
		String title = "Manage Accounts";
		setTitle(title);
		setSize(1000,800);
		setVisible(true);
		panel_north.add(idTitle);
		panel_north.add(userID);
		panel_north.add(notexist);
		
		panel_north.add(fullnameTitle);
		panel_north.add(fullname);
		panel_north.add(btn_insert);

		panel_north.add(roleTitle);
		panel_north.add(roles);
		panel_north.add(btn_update);

		panel_north.add(emailTitle);
		panel_north.add(email);
		panel_north.add(btn_delete);

		panel_north.add(passwordTitle);
		panel_north.add(password);
		panel_north.add(notexist2);
		
		table();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setVisible(true);
		add(panel_north,"North");
		add(panel_center,"Center");
		
		panel_north.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_center.setBorder(new EmptyBorder(20, 10, 20 ,10));
			
		btn_insert.addActionListener(this);
		btn_update.addActionListener(this);
		btn_delete.addActionListener(this);
	}
	public String countOfAt() {
	// Java program to validate email in Java
		int countOfAt = 0;
		String mail = email.getText();

        for (int i = 0; i < mail.length(); i++) {
            if(mail.charAt(i)=='@')
                countOfAt++; 
        }
        
        if (countOfAt == 1) {
			return ("1");
		}
        else {
        	return ("Not 1");
        }
        
	}
	
	public void duplicateCheck(){
		String query;
		query = "SELECT * FROM users WHERE email ='"+email.getText()+"'";
		con.rs = con.executeQuery(query);
			try {
				if(con.rs.next()== true) { 
					 if(con.rs.getString("email").equals(email.getText())) {
						 JOptionPane.showMessageDialog (null,"Email already used","Error",JOptionPane.INFORMATION_MESSAGE);
						 duplicate = true;
					 }
				}
				else if (con.rs.next()== false)
					duplicate = false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
	}
	
	public String passwordValidator(){
		int a = 0;
		int b = 0;
		
		String pass = password.getText();
		
		for(int i=0; i<pass.length(); i++)
		{
			if(Character.isDigit(pass.charAt(i)))
				 a = 1;
			if(Character.isLetter(pass.charAt(i)))
				 b = 1;
			}
		if (a == 1 && b == 1)
			{
			return ("alphanumeric");
			}
		else
			{
			return ("non alphanumeric");
			}
	}
	
	public void validation() {
		
		String temp_fullname = fullname.getText();
		String temp_email = email.getText();
		String temp_password = password.getText();
		
		countOfAt();
		passwordValidator();
		
		if (fullname.getText().trim().equals("") || email.getText().trim().equals("") || email.getText().trim().equals("")) {
			JOptionPane.showMessageDialog (null,"All Fields must be filled","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			notnull = true;
		}
		
		if (email.getText().trim().startsWith("@") || email.getText().trim().endsWith("@") || email.getText().trim().endsWith(".") || email.getText().trim().contains(".@") || email.getText().trim().contains("@.") || countOfAt().equals("Not 1")) {
			JOptionPane.showMessageDialog (null,"Wrong Email Format","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		else
			emailformat = true;
		
		if (passwordValidator().equals("non alphanumeric")) {
			JOptionPane.showMessageDialog (null,"Password must be Alphanumeric","Error",JOptionPane.INFORMATION_MESSAGE);
		} 
		else
			passwordvalidator = true;
		
	}
	
	private String randomAlphaNum(int length) {
		
		String result = "";
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVXYZ0123456789";
		
		for (int i = 0; i < length; i++) {
			int idxRand = randomDigit(0, base.length()-1);
			result += base.charAt(idxRand);
		}
	return result;
	}
	
	private int randomDigit(int min, int max) {
		if (min >= max) {
			return -1;
		}
		int bound = max - min + 1;
		int tmp = rand.nextInt(bound)+min;
		return tmp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			if(e.getSource() == table)
			{
				//memasukan data yang di click ke textfield
				flag = 1;
				if(table.getSelectedRow() > -1)
				{
					int row = table.getSelectedRow();
					this.userID.setText((String) table.getValueAt(row, 0));
					this.fullname.setText((String) table.getValueAt(row, 1));
					this.roles.setSelectedItem(table.getValueAt(row, 2));
					this.email.setText((String) table.getValueAt(row, 3));
					this.password.setText((String) table.getValueAt(row, 4));
				}
			}
	}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//confirm
	if(e.getSource().equals(btn_update)) {
		
		notnull = false;
		emailformat = false;
		passwordvalidator = false;
		duplicate = true;
		
		String temp_userid = userID.getText();
		String temp_fullname = fullname.getText();
		String temp_role = (String) roles.getSelectedItem();
		String temp_email = email.getText();
		String temp_password = password.getText();
		
		validation();
		duplicateCheck();
		if(table.getSelectedRow() > -1) {
			try {
				if (notnull == true && emailformat == true && passwordvalidator == true && duplicate == false) {
					con.UpdateIntoUser(temp_fullname, temp_role, temp_email, temp_password, temp_userid);
					fillData();
					JOptionPane.showMessageDialog (null,"Update success!","Update success",JOptionPane.INFORMATION_MESSAGE);
					table.clearSelection();
					userID.setText("");
					fullname.setText("");
					roles.setSelectedIndex(-1);
					email.setText("");
					password.setText("");
				}
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
		}
		
		else {
			JOptionPane.showMessageDialog (null,"Please select data to be deleted first","Error",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	if(e.getSource().equals(btn_insert)) {
		notnull = false;
		emailformat = false;
		passwordvalidator = false;
		duplicate = true;
		
		String temp_userid = randomAlphaNum(10);
		String temp_fullname = fullname.getText();
		String temp_role = roles.getSelectedItem().toString();
		String temp_email = email.getText();
		String temp_password = password.getText();
		
		validation();
		duplicateCheck();
		
		if(notnull == true && emailformat == true && passwordvalidator == true && duplicate == false) {
				try {
					con.InsertIntoUser(temp_userid, temp_fullname, temp_role, temp_email, temp_password);
					fillData();
					JOptionPane.showMessageDialog (null,"Insert success!","Insert Success",JOptionPane.INFORMATION_MESSAGE);
					userID.setText("");
					fullname.setText("");
					roles.setSelectedIndex(-1);
					email.setText("");
					password.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
	
	if(e.getSource().equals(btn_delete)) {
		
		String temp_userid = userID.getText();
		if(table.getSelectedRow() > -1) {
				try {
					con.DeleteUser(temp_userid);
					fillData();
					JOptionPane.showMessageDialog (null,"Delete success!","Delete Success",JOptionPane.INFORMATION_MESSAGE);
					table.clearSelection();
					userID.setText("");
					fullname.setText("");
					roles.setSelectedIndex(-1);
					email.setText("");
					password.setText("");
				} 
				
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		
		else {
				JOptionPane.showMessageDialog (null,"Please select data to be deleted first","Error",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
	
