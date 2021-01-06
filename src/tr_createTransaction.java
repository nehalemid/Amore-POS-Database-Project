package projectBAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class tr_createTransaction extends JInternalFrame implements ActionListener, MouseListener {
	int flag = 0;
	boolean notzero = false;
	boolean selectedrowmenu = false;
	boolean selectedrowcart = false;
	boolean check = false;
	
	int rowCount;
	int i;
	
	Random rand = new Random();
	JPanel panel_north = new JPanel(new GridLayout(3, 3, 20, 20));
	JPanel panel_center = new JPanel(new GridBagLayout());
	JPanel panel_south = new JPanel(new GridLayout(2,2, 20, 20));
	
	GridBagConstraints gbc = new GridBagConstraints();

	JLabel idTitle = new JLabel("ID");
	JLabel menuID = new JLabel("Menu ID");
	JLabel nameTitle = new JLabel("Name");
	JLabel menuName = new JLabel("Menu Name");
	JLabel menuTitle = new JLabel("Menu");
	JLabel cartTitle = new JLabel("Cart");
	JLabel sellPrice = new JLabel("Sell Price");
	JLabel qtyTitle = new JLabel("Quantity");
	JLabel totalTitle = new JLabel("Total");
	JLabel totalPrice = new JLabel("Total Price");
	JLabel menuPrice = new JLabel("Menu Price");
	
	JSpinner qty = new JSpinner();
	
	JButton btn_add = new JButton("Add");
	JButton btn_update = new JButton("Update");
	JButton btn_remove = new JButton("Remove");
	JButton btn_finish = new JButton("Finish");
	JButton btn_cancel = new JButton("Cancel");
	
	JTable table_menu = new JTable();
	JTable table_cart = new JTable();

	DefaultTableModel dtm1;
	DefaultTableModel dtm2;
	Vector<Object>tRow,tHeader;
	Vector<Object>tRow2,tHeader2;
	Connect_db con = new Connect_db();
	
	staffID staffID1 = new staffID();
	String staffID = staffID1.getStaffID();

	
	public void fillData() {
		dtm1 = new DefaultTableModel(tHeader, 0);
		con.rs = con.executeQuery("SELECT menuid, name, sellprice FROM menu");
		try 
		{
			while (con.rs.next()) 
			{
			//untuk isi data kedalam tabel
				tRow = new Vector<Object>();
				tRow.add(con.rs.getString("menuid"));
				tRow.add(con.rs.getString("name"));
				tRow.add(con.rs.getInt("sellprice"));
				dtm1.addRow(tRow);
			}
			table_menu.setModel(dtm1);
		} 
		catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initTableMenu()
	{
		tHeader = new Vector<Object>();
		tHeader.add("ID");
		tHeader.add("Name");
		tHeader.add("Sell Price");
	}	
	public void tableMenu()
	{
		initTableMenu();
		fillData();
		table_menu = new JTable(dtm1)
		{
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane pane = new JScrollPane(table_menu,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table_menu.addMouseListener(this);
		gbc.gridx = 0;
		gbc.gridy = 200;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		table_menu.setPreferredSize(new Dimension(400, 500));
		panel_center.add(pane, gbc);
	}

	public void initTableCart()
	{
		tHeader2 = new Vector<Object>();
		tHeader2.add("ID");
		tHeader2.add("Name");
		tHeader2.add("Price");
		tHeader2.add("Quantity");
	}	
	public void tableCart()
	{
		initTableCart();
		dtm2 = new DefaultTableModel(tHeader2, 0);
		table_cart = new JTable(dtm2)
		{
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		JScrollPane pane = new JScrollPane(table_cart,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table_cart.addMouseListener(this);
		gbc.gridx = 600;
		gbc.gridy = 200;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		table_cart.setPreferredSize(new Dimension(400, 500));
		panel_center.add(pane, gbc);
	}
	public void check() {
		String rowEntry;
		String temp_menuid = menuID.getText().toString();
		for (i = 0; i < dtm2.getRowCount(); i++) {
	        rowEntry = dtm2.getValueAt(i, 0).toString();
		    if (rowEntry.equals(temp_menuid)) {
		            check = true;
		            break;
		        }
		     else {
		     check = false;
		     }
		}
	}
	public void addQty() {
			
			int qtynew = (int) qty.getValue();
			int qtyExist = (int) dtm2.getValueAt(i, 3);
			int add = qtynew + qtyExist;
			
			dtm2.setValueAt(add, i, 3);

			int total = 0;
			int totalAdd = 0;
			
            for(int j = 0; j < dtm2.getRowCount(); j++) {
                totalAdd = Integer.parseInt(dtm2.getValueAt(j, 2).toString()) * Integer.parseInt(dtm2.getValueAt(j, 3).toString());
                total = total + totalAdd;
            }
            totalPrice.setText(Integer.toString(total));
            qty.setValue(0);;
		}
	
	public void fillDataCart() {
		
				tRow2 = new Vector<Object>();
				tRow2.add(menuID.getText());
				tRow2.add(menuName.getText());
				tRow2.add(menuPrice.getText());
				tRow2.add(qty.getValue());
				dtm2.addRow(tRow2);
				qty.setValue(0);
				
				int total = 0;
				int totalAdd = 0;
				
                for(int j = 0; j < dtm2.getRowCount(); j++) {
                    totalAdd = Integer.parseInt(dtm2.getValueAt(j, 2).toString()) * Integer.parseInt(dtm2.getValueAt(j, 3).toString());
                    total = total + totalAdd;
                }
         totalPrice.setText(Integer.toString(total));
	}
		
	
	public tr_createTransaction() {
		
		String title = "Transaction";
		setTitle(title);
		
		panel_north.add(idTitle);
		panel_north.add(menuID);
		panel_north.add(btn_add);
		panel_north.add(nameTitle);
		panel_north.add(menuName);
		panel_north.add(btn_update);
		panel_north.add(qtyTitle);
		panel_north.add(qty);
		panel_north.add(btn_remove);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		menuTitle.setPreferredSize(new Dimension(400,50));
		panel_center.add(menuTitle, gbc);
		
		gbc.gridx = 600;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		cartTitle.setPreferredSize(new Dimension(400, 50));	
		panel_center.add(cartTitle, gbc);
		
		tableMenu();
		tableCart();
		
		panel_south.add(totalTitle);
		panel_south.add(totalPrice);
		panel_south.add(btn_cancel);
		panel_south.add(btn_finish);
		
		panel_north.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_center.setBorder(new EmptyBorder(20, 10, 20,10));
		panel_south.setBorder(new EmptyBorder(20, 10, 20,10));
		
		btn_add.addActionListener(this);
		btn_update.addActionListener(this);
		btn_remove.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_finish.addActionListener(this);
	
		
		add(panel_north,"North");
		add(panel_center,"Center");
		add(panel_south,"South");
		
		setSize(1000,800);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setVisible(true);
	}
	 
	public String dateConvert() {
	        Date now = new Date();
	        String pattern = "yyyy-MM-dd";
	        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	        String newDate = formatter.format(now);
	        
			return newDate;
	    }
	
	public void finish() {
		String newDate = dateConvert();
		String temp_menuID;
		int quantity;
		
		for(int j = 0; j < dtm2.getRowCount(); j++) {
            quantity = (int) dtm2.getValueAt(j, 3);
            temp_menuID = (String) dtm2.getValueAt(j, 0);
            String temp_id = randomAlphaNum(10);
            try {
    			con.InsertTransactionHeader(temp_id, staffID, newDate);
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		
    		try {
    			con.InsertTransactionDetail(temp_id, temp_menuID, quantity);
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
		
		System.out.println("Success");
		String zero = "0";
        totalPrice.setText(zero);
		dtm2.setRowCount(0);
	}
		
	
	public void removeItem() {
		
			int a = table_cart.getSelectedRow();
			dtm2.removeRow(a);
			JOptionPane.showMessageDialog (null,"Item Removed","Success",JOptionPane.INFORMATION_MESSAGE);
			
			int totalAdd = 0;
			int total = 0;
			
			for(int j = 0; j < dtm2.getRowCount(); j++) {
	            totalAdd = Integer.parseInt(dtm2.getValueAt(j, 2).toString()) * Integer.parseInt(dtm2.getValueAt(j, 3).toString());
	            total = total + totalAdd;
	        }
	        totalPrice.setText(Integer.toString(total));
		}
	
	public void validation() {
		
		int quantity = (int) qty.getValue();
		
		if (quantity > 0) {
			notzero = true;
		} 
		
		else {
			notzero = false;
			JOptionPane.showMessageDialog (null,"Item quantity cannot be zero","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (table_cart.getSelectedRow() > -1) {
			selectedrowcart = true;
		}
		
		else {
			selectedrowcart = false;
		}
		
		if (table_menu.getSelectedRow() > -1) {
			selectedrowmenu = true;
		}
		else {
			selectedrowmenu = false;
		}
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
	
	public void update() {
		
		validation();
	
		int quantity = (int) qty.getValue();
		
		table_cart.setValueAt(quantity, table_cart.getSelectedRow(), 3);
		
		int totalAdd = 0;
		int total = 0;
		for(int j = 0; j < dtm2.getRowCount(); j++) {
            totalAdd = Integer.parseInt(dtm2.getValueAt(j, 2).toString()) * Integer.parseInt(dtm2.getValueAt(j, 3).toString());
            total = total + totalAdd;
        }
        totalPrice.setText(Integer.toString(total));
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == table_menu) {
			flag  = 1;
			
			if(table_menu.getSelectedRow() > -1)
			{
				int row = table_menu.getSelectedRow();
				this.menuID.setText((String) table_menu.getValueAt(row, 0).toString());
				this.menuName.setText((String) table_menu.getValueAt(row, 1).toString());
				this.menuPrice.setText((String) table_menu.getValueAt(row, 2).toString());
				this.qty.setValue(0);
			}
		}
		
		if(e.getSource() == table_cart) {
			if(table_cart.getSelectedRow() > -1)
				flag  = 1;
			{
				int row = table_cart.getSelectedRow();
				this.menuID.setText((String) table_cart.getValueAt(row, 0).toString());
				this.menuName.setText((String) table_cart.getValueAt(row, 1).toString());
				this.menuPrice.setText((String) table_cart.getValueAt(row, 2).toString());
				this.qty.setValue(table_cart.getValueAt(row, 3));
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
		if (e.getSource() == btn_add) {
			
			notzero = false;
			selectedrowmenu = false;
			check = false;
			
			check();
			
			validation();
			
			if(notzero == true && selectedrowmenu == true && check == true) {
				addQty();
				JOptionPane.showMessageDialog (null,"Item Added","Add Success",JOptionPane.INFORMATION_MESSAGE);
				table_menu.clearSelection();
				menuID.setText("");
				menuName.setText("");
				qty.setValue(0);
				}
			
			if(notzero == true && selectedrowmenu == true && check == false) {
				fillDataCart();
				JOptionPane.showMessageDialog (null,"Item Added","Add Success",JOptionPane.INFORMATION_MESSAGE);
				table_menu.clearSelection();
				menuID.setText("");
				menuName.setText("");
				qty.setValue(0);
				}
			if(selectedrowmenu == false) { 
				JOptionPane.showMessageDialog (null,"Please select item to be added","Error",JOptionPane.INFORMATION_MESSAGE);
					}
				}
		
		if (e.getSource() == btn_finish) {
			finish();
		}
		if (e.getSource() == btn_remove) {
			
			if (table_cart.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog (null,"Please select item to be removed","Error",JOptionPane.INFORMATION_MESSAGE);
			}
			
			if (table_cart.getSelectedRow() > -1) {
				removeItem();
				table_cart.clearSelection();
				menuID.setText("");
				menuName.setText("");
				qty.setValue(0);
			}
		}
		if (e.getSource() == btn_update) {
			validation();
			if(notzero == true && selectedrowcart == true) {
				update();
				JOptionPane.showMessageDialog (null,"Item Updated","Update Success",JOptionPane.INFORMATION_MESSAGE);
				table_cart.clearSelection();
				menuID.setText("");
				menuName.setText("");
				qty.setValue(0);
			}
			if(selectedrowcart == false) {
				JOptionPane.showMessageDialog (null,"Please select item to be updated","Error",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (e.getSource() == btn_cancel) {
			dispose();
		}
	}
}
