package projectBAD;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

public class Connect_db {
	public ResultSet rs;
	Statement st;
	Connection con;
	PreparedStatement pStat;
	ResultSetMetaData rsm;
	PreparedStatement pst;
	
	public Connect_db() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root",""); 
            st = con.createStatement();       
		    System.out.println("Connected to the database..");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Connection");
		}
	}

	public ResultSet executeQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error Connection RS");
				}
		return rs;
	}

	public void executeUpdate(String query) {
		try {
			st.executeUpdate(query);
			} catch (SQLException e) {
			e.printStackTrace();
			}
	}

	public void executePStatement(String query) {
		try {
			pStat = con.prepareStatement(query);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error Connection PSTATEMENT");
			}
		}
	
	 public void InsertIntoUser(String userid,String fullname,String role, String email,String password) throws SQLException
	    {
	    	pst = (PreparedStatement) con.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?)");
	    	pst.setString(1, userid);
	    	pst.setString(2, fullname);
	    	pst.setString(3, role);
	    	pst.setString(4, email);
	    	pst.setString (5, password);
	    	pst.execute();
	    }
	    
	    public void UpdateIntoUser(String fullname,String role, String email,String password, String userid) throws SQLException
	    {
	 	  pst = (PreparedStatement) con.prepareStatement("UPDATE users SET fullname = ?, role = ?, email = ?, password = ? WHERE userid = ?");
	 	  pst.setString(1, fullname);
	 	  pst.setString(2, role);
	 	  pst.setString(3, email);
	 	  pst.setString(4, password);
	 	  pst.setString(5, userid);	 	  
	 	  pst.execute();
	    }
	    
	    public void DeleteUser(String userid) throws SQLException
	    {
	 	   pst = (PreparedStatement) con.prepareStatement("DELETE FROM users WHERE userid = ?");
	 	   pst.setString(1, userid);
	 	   pst.execute();
	    }
	    
	    public void InsertMenu(String menuid, String name, int sellprice, int ingredientprice) throws SQLException
	    {
	    	pst = (PreparedStatement) con.prepareStatement("INSERT INTO menu VALUES (?,?,?,?)");
	    	pst.setString(1, menuid);
	    	pst.setString(2, name);
	    	pst.setInt(3, sellprice);
	    	pst.setInt(4, ingredientprice);
	    	pst.execute();
	    } 
	    
	    public void UpdateMenu(String name, int sellprice, int ingredientprice, String menuid) throws SQLException
	    {
	 	   pst = (PreparedStatement) con.prepareStatement("UPDATE menu SET name = ?, sellprice = ?, ingredientprice = ? WHERE menuid = ?");
	 	   pst.setString(1, name);
	 	   pst.setInt(2, sellprice);
	 	   pst.setInt(3, ingredientprice);
	 	   pst.setString(4, menuid);
	 	   pst.execute();
	    }
	    public void DeleteMenu(String menuid) throws SQLException
	    {
	 	   pst = (PreparedStatement) con.prepareStatement("DELETE FROM menu WHERE menuid = ?");
	 	   pst.setString(1, menuid);
	 	   pst.execute();
	    }
	    
	    public void InsertTransactionDetail(String transactionid, String menuid, int quantity ) throws SQLException
	    {
	 	   pst = (PreparedStatement) con.prepareStatement("INSERT INTO transactiondetail VALUES (?,?,?)");
	 	   pst.setString(1, transactionid);
	 	   pst.setString(2, menuid);
	 	   pst.setInt(3, quantity);
	 	   pst.execute();
	    }
	   
	    public void InsertTransactionHeader(String transactionid, String staffid, String transactiondate ) throws SQLException
	    {
	 	   pst = (PreparedStatement) con.prepareStatement("INSERT INTO transactionheader VALUES (?,?,?)");
	 	   pst.setString(1, transactionid);
	 	   pst.setString(2, staffid);
	 	   pst.setString(3, transactiondate);
	 	   pst.execute();
	    }
}
	    
