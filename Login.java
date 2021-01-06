package projectBAD;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener{
	
	JPanel panel_north = new JPanel(new BorderLayout());
	JPanel panel_center = new JPanel(new GridLayout(2,2, 10, 10));
	JPanel panel_south = new JPanel (new FlowLayout());
	JLabel email1 = new JLabel("Email");
	JLabel pass = new JLabel("Password");
	JLabel title = new JLabel("Amore POS", JLabel.CENTER);
	JButton login = new JButton("Login");
	
	JTextField txt_email = new JTextField();
	JPasswordField txt_password = new JPasswordField();
	
	Font defaultF = new Font("Arial", 0, 15);

	
	staffID staffID1 = new staffID();
	

	public Login() {
	
		setSize(250,200);
		setLocationRelativeTo(null);
		
		panel_north.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_center.setBorder(new EmptyBorder(5, 5, 5 ,5));
		panel_south.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		title.setFont(defaultF);
		pass.setFont(defaultF);
		txt_password.setFont(defaultF);
		email1.setFont(defaultF);
		txt_email.setFont(defaultF);
		login.setFont(defaultF);
		
		login.addActionListener(this);
		
		panel_north.add(title);
		panel_center.add(email1);
		panel_center.add(txt_email);
		panel_center.add(pass);
		panel_center.add(txt_password);
		panel_south.add(login);
		
		add(panel_north,"North");
		add(panel_center,"Center");
		add(panel_south,"South");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
		
		public void db_login(){
			Connect_db con = new Connect_db();
			String role,query;
			
			 query = "SELECT * FROM users WHERE email ='"+txt_email.getText()+"' AND password = '"+txt_password.getText()+"'";
			 con.rs = con.executeQuery(query);
			 try {
				 if(con.rs.next()== true) { 
					 
					 if(con.rs.getString("role").equals("Admin"))
					 {
						role = "Admin";
						JOptionPane.showMessageDialog(this, "Welcome "+con.rs.getString("fullname"));
						setVisible(false);
						new Admin();
						staffID1.setStaffID(con.rs.getString("userid"));
					 }
					 
					 if(con.rs.getString("role").equals("Accountant"))
					 {
						role = "Accountant";
						JOptionPane.showMessageDialog(this, "Welcome "+con.rs.getString("fullname"));
						setVisible(false);
						new Accountant();
						staffID1.setStaffID(con.rs.getString("userid"));
					 }
					 
					 if(con.rs.getString("role").equals("Cashier"))
					 {
						role = "Cashier";
						JOptionPane.showMessageDialog(this, "Welcome "+con.rs.getString("fullname"));
						setVisible(false);
						new Cashier();
						staffID1.setStaffID(con.rs.getString("userid"));
					 }
				}
				 else JOptionPane.showMessageDialog(null, "Incorrect Email or Password","Login failed",JOptionPane.INFORMATION_MESSAGE);
			 } catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
		
		//login validation
		public void login_verification(){
			
			if(txt_email.getText().trim().equals("") || (txt_password.getText().trim().equals("")))
			{
				JOptionPane.showMessageDialog(null,"Username and Password cannot be empty","Error",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				db_login();
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == login){
				login_verification();
			}
		}
		
	}
		