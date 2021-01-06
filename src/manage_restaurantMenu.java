package projectBAD;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Random;
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

public class manage_restaurantMenu extends JInternalFrame implements ActionListener, MouseListener {
	
	Random rand = new Random();
	
	boolean notnull = false;
	boolean numeric = false;
	
	JPanel panel_north = new JPanel(new GridLayout(4, 3, 10, 20));
	JPanel panel_center = new JPanel(new BorderLayout());
	
	JLabel idTitle = new JLabel("ID");
	JLabel id = new JLabel("Menu ID");
	JLabel nameTitle = new JLabel("Name");
	JLabel sellPriceTitle = new JLabel("Sell Price");
	JLabel ingredientPriceTitle = new JLabel("Ingredient Sell Price");
	JLabel notexist = new JLabel("");
	
	JTextField nameInput = new JTextField(50);
	JTextField sellPriceInput = new JTextField(11);
	JTextField ingredientPriceInput = new JTextField(11);
	
	JButton btn_insert = new JButton("Insert");
	JButton btn_update = new JButton("Update");
	JButton btn_delete = new JButton("Delete");
	
	Font defaultF = new Font("Arial", 0, 15);

	JTable table = new JTable();
	DefaultTableModel dtm;
	Vector<Object>tRow,tHeader;
	Connect_db con = new Connect_db();
	
	public void initTable()
	{
		tHeader = new Vector<Object>();
		tHeader.add("ID");
		tHeader.add("Name");
		tHeader.add("Sell Price");
		tHeader.add("Ingredient Price");
	}
	
	public void fillData() {
		dtm = new DefaultTableModel(tHeader, 0);
		con.rs = con.executeQuery("SELECT * FROM menu");
		try 
		{
			while (con.rs.next()) 
			{
			//untuk isi data kedalam tabel
				tRow = new Vector<Object>();
				tRow.add(con.rs.getString("menuid"));
				tRow.add(con.rs.getString("name"));
				tRow.add(con.rs.getInt("sellprice"));
				tRow.add(con.rs.getInt("ingredientprice"));
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
	
	public manage_restaurantMenu(){
		String title = "Manage Restaurant Menu";
		setTitle(title);
		setSize(1000,800);
		setVisible(true);
		table();
		panel_north.add(idTitle);
		panel_north.add(id);
		panel_north.add(notexist);
		
		panel_north.add(nameTitle);
		panel_north.add(nameInput);
		panel_north.add(btn_insert);

		panel_north.add(sellPriceTitle);
		panel_north.add(sellPriceInput);
		panel_north.add(btn_update);

		panel_north.add(ingredientPriceTitle);
		panel_north.add(ingredientPriceInput);
		panel_north.add(btn_delete);
		
		panel_north.setBorder(new EmptyBorder(20, 10, 20, 10));
		panel_center.setBorder(new EmptyBorder(20, 10, 20 ,10));
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setVisible(true);
		add(panel_north,"North");
		add(panel_center,"Center");
			
		btn_insert.addActionListener(this);
		btn_update.addActionListener(this);
		btn_delete.addActionListener(this);
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
	
	public void validation() {
		String temp_menuname = nameInput.getText();
		int sellprice = Integer.parseInt(sellPriceInput.getText());
		int ingredientprice = Integer.parseInt(ingredientPriceInput.getText());
		
		if (nameInput.getText().trim().equals("") || sellPriceInput.getText().trim().equals("") || ingredientPriceInput.getText().trim().equals("")) {
			JOptionPane.showMessageDialog (null,"Field must be filled","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			notnull = true;
		}
		
		if (intValidator() == "alphanumeric" || intValidator() == "one numeric, one alphanumeric") {
			JOptionPane.showMessageDialog (null,"Prices must be a number","Error",JOptionPane.INFORMATION_MESSAGE);
		}
		
		else {
			numeric = true;
		}
		
	}
	
	public String intValidator(){
		int a = 0;
		int b = 0;
		
		String sellprice = sellPriceInput.getText();
		String ingredientprice = sellPriceInput.getText();
		
		for(int i=0; i<sellprice.length(); i++)
		{
			if(Character.isLetter(sellprice.charAt(i)))
				 a = 1;
		}
		
		for(int i=0; i<ingredientprice.length(); i++) {
			if(Character.isLetter(ingredientprice.charAt(i)))
				 b = 1;
		}
		
		if (a == 1 && b == 1) {
			return ("alphanumeric");
		}
		else if ((a == 1 && b == 0) || (a == 0 && b == 1)) {
			return ("one numeric, one alphanumeric");
		}
		else {
			return ("numeric");
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == table) {
			//memasukan data yang di click ke textfield
			int flag = 1;
			
			if(table.getSelectedRow() > -1) {
				int row = table.getSelectedRow();
				this.id.setText((String) table.getValueAt(row, 0));
				this.nameInput.setText((String) table.getValueAt(row, 1));
				this.sellPriceInput.setText(Integer.toString((int)table.getValueAt(row, 2)));
				this.ingredientPriceInput.setText(Integer.toString((int)table.getValueAt(row, 3)));
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
		if(e.getSource().equals(btn_update)) {
			String menuid = id.getText();
			String menuname = nameInput.getText();
			int menusellprice = Integer.parseInt(sellPriceInput.getText());
			int menuingredientprice = Integer.parseInt(ingredientPriceInput.getText());
			
			intValidator();
			validation();
				if (table.getSelectedRow() > -1) {
					if(numeric == true && notnull == true) {
						try {
							con.UpdateMenu(menuname, menusellprice,menuingredientprice, menuid);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					fillData();
					JOptionPane.showMessageDialog (null,"Update success!","Update success",JOptionPane.INFORMATION_MESSAGE);
					id.setText("");
					nameInput.setText("");
					sellPriceInput.setText("");
					ingredientPriceInput.setText("");
					}
				}
				else {
					JOptionPane.showMessageDialog (null,"Please select data to be deleted first","Error",JOptionPane.INFORMATION_MESSAGE);
				}
		}
			
		if(e.getSource().equals(btn_insert)) {
			
			String menuid = randomAlphaNum(10);
			String menuname = nameInput.getText();
			int menusellprice = Integer.parseInt(sellPriceInput.getText());
			int menuingredientprice = Integer.parseInt(ingredientPriceInput.getText());
			
			intValidator();
			validation();
			
			if(numeric == true && notnull == true) {
					try {
						con.InsertMenu(menuid, menuname, menusellprice, menuingredientprice);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				fillData();
				JOptionPane.showMessageDialog (null,"Insert success!","Insert Success",JOptionPane.INFORMATION_MESSAGE);
				table.clearSelection();
				id.setText("");
				nameInput.setText("");
				sellPriceInput.setText("");
				ingredientPriceInput.setText("");
			}
		}
		
		if(e.getSource().equals(btn_delete)) {
			
			if (table.getSelectedRow() > -1) {
			
				String menuid = id.getText();
				
					try {
						con.DeleteMenu(menuid);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				fillData();
				JOptionPane.showMessageDialog (null,"Delete success!","Delete Success",JOptionPane.INFORMATION_MESSAGE);
				table.clearSelection();
				id.setText("");
				nameInput.setText("");
				sellPriceInput.setText("");
				ingredientPriceInput.setText("");
			}
			else {
				JOptionPane.showMessageDialog (null,"Please select data to be deleted first","Error",JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}
}
