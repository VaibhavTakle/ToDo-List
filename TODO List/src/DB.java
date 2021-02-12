

import java.sql.*;

import javax.swing.*;


public class DB {
	
	public static Connection dbconnect(){  
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/to_do","root","Vaibhav@0911");  
			return conn;
		}catch(Exception e2) {
			JOptionPane.showMessageDialog(null,"Error in Connectivity!!","Alert",JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
}