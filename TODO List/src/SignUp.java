
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SignUp extends JFrame implements ActionListener {
	
	JButton jb1,jb2;
	JLabel jl,jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	JTextField jt1,jt2,jt3,jt4,jt5; 
	Connection con;
	
	SignUp()
	{
		con = DB.dbconnect();
		
		jl7 = new JLabel("***Registration***");
		jl7.setBounds(80, 70, 300, 35);
		jl7.setFont(new java.awt.Font("Tahoma", 1, 28));
	    jl7.setForeground(new java.awt.Color(255, 51, 0));
		add(jl7);
		
		jb1 = new JButton("Sign Up");
		jb1.setBounds(231, 471, 110, 29);
		jb1.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jb1);
		
		jb2 = new JButton("Login");
		jb2.setBounds(56, 471, 85, 29);
		jb2.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jb2);
		
		jl1 = new JLabel("UserID");
		jl1.setBounds(55, 275, 120, 13);
		jl1.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jl1);
		
		jt1 = new JTextField();
		jt1.setBounds(200, 274, 200, 26);
		jt1.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jt1);
		
		jl2 = new JLabel("First Name");
		jl2.setBounds(56, 150, 120, 22);
		jl2.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jl2);
		
		jt2 = new JTextField();
		jt2.setBounds(200, 152, 200, 26);
		jt2.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jt2);
		
		jl3 = new JLabel("Last Name");
		jl3.setBounds(56, 216, 120, 22);
		jl3.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jl3);
		
		jt3 = new JTextField();
		jt3.setBounds(200, 220, 200, 26);
		jt3.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jt3);
		
		jl4 = new JLabel("Email");
		jl4.setBounds(56, 333, 120, 22);
		jl4.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jl4);
		
		jt4 = new JTextField();
		jt4.setBounds(200, 337, 200, 26);
		jt4.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jt4);
		
		jl6 = new JLabel("Password");
		jl6.setBounds(55, 387, 120, 22);
		jl6.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jl6);
		
		jt5 = new JTextField();
		jt5.setBounds(200, 391, 200, 26);
		jt5.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jt5);
		
		jl5 = new JLabel(new ImageIcon("src/signup.jpg"));
		jl5.setBounds(0, 0, 450, 600);
		add(jl5);
		
		
		
		jb1.addActionListener(this);
	    jb2.addActionListener(this);
		
		setTitle("SignUp Form");
		setLayout(null);
	    setSize(450, 600);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jb1) {
			if(isAllFieldFillup()) {
				
				String first=jt2.getText();
				String last=jt3.getText();
				String user=jt1.getText();
				String password=jt5.getText();
				String email=jt4.getText();
				
				try {
					
					PreparedStatement pst=((java.sql.Connection) con).prepareStatement("insert into signup(first_name,last_name,user_id,password,email_id) value(?,?,?,?,?)");
					pst.setString(1,first);
					pst.setString(2,last);
					pst.setString(3,user);
					pst.setString(4,password);
					pst.setString(5,email);
					
					int rs = pst.executeUpdate();
                    if (rs == 1) {    
                        JOptionPane.showMessageDialog(jb1, "Your account is successfully created");
                        dispose();
                        new Login();
                    } else {
                        JOptionPane.showMessageDialog(jb1, "This UserID is already exist");
                    }
					
					jt1.setText("");
					jt2.setText("");
					jt3.setText("");
					jt4.setText("");
					jt5.setText("");
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(jb1, "This account is already exit");
				}
				
			}
			
		}
		
		
		if(e.getSource() == jb2) {
			dispose();
			new Login();
		}
	}
	
	
	 private boolean isAllFieldFillup(){
	        boolean fillup;
	        if(jt1.getText().trim().isEmpty()||jt2.getText().trim().isEmpty()||jt3.getText().trim().isEmpty()||jt4.getText().trim().isEmpty()||jt5.getText().trim().isEmpty())
	        {

	            JOptionPane.showMessageDialog(this,"All Fields are Mandetory!!","Alert",JOptionPane.WARNING_MESSAGE);
	            
	            fillup = false;
	        }
	        else fillup = true;
	        return fillup;
	 }
	 
	public static void main(String[] args) {
		
		
		 //new SignUp();
		

	}
}
