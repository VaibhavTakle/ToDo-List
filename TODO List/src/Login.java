import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
class Login extends JFrame  implements ActionListener   {
	   
	JButton jb1,jb2;
	JLabel jl1,jl2,jl3,jl5,jl7;
	JTextField jt1;
	JPasswordField jp1;
	
	java.sql.Connection con=null;
	Login(){
		
			con=DB.dbconnect();
		jb1 = new JButton("Login"); 
		jb1.setBounds(350, 200, 100, 35);
	    jb1.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jb1);
		
		jb2 = new JButton("Sign Up");
		jb2.setBounds(350, 250, 120, 35);
	    jb2.setFont(new java.awt.Font("Tahoma", 1, 17));
		add(jb2);
		
		jl1 = new JLabel("****TODO List****");
		jl1.setBounds(150,20, 480, 50);
	    jl1.setFont(new java.awt.Font("Tahoma", 1, 28));
	    jl1.setForeground(new java.awt.Color(255, 51, 0));
		add(jl1);
		
		jl2 = new JLabel("UserID");
		jl2.setBounds(60, 90, 100, 38);
		jl2.setFont(new java.awt.Font("Tahoma", 1, 23));
		jl2.setForeground(Color.black);
		add(jl2);
		
		jl3 = new JLabel("Password");
		jl3.setBounds(60, 145, 120, 38);
		jl3.setFont(new java.awt.Font("Tahoma", 1, 23));
		jl3.setForeground(Color.black);
		add(jl3);
		
		jt1 = new JTextField();
		jt1.setBounds(190, 90, 250, 30);
		jt1.setFont(new java.awt.Font("Tahoma", 1, 15));
		add(jt1);
		
		jp1 = new JPasswordField();
		jp1.setBounds(190, 145, 250, 30);
		add(jp1);
		
		jl7 = new JLabel("New User?");
		jl7.setBounds(50, 250, 200, 38);
		jl7.setFont(new java.awt.Font("Tahoma", 1, 23));
		jl7.setForeground(Color.red);
		add(jl7);
		
		jl5 = new JLabel(new ImageIcon("src/signup.jpg"));
		jl5.setBounds(0, 0, 600, 400);
		add(jl5);
		
		
	    jb1.addActionListener(this);
        jb2.addActionListener(this);
		
		  setLayout(null);
	      setSize(600, 400);
	      setTitle("Login");
	      setVisible(true);
	      
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jb1) {
			if(isAllFieldFillup()) {
				
				try {
					String user=jt1.getText();
					String pwd=String.valueOf(jp1.getPassword());
					PreparedStatement pst=con.prepareStatement("select * from signup where user_id=? and password=?");
					pst.setString(1,user);
					pst.setString(2, pwd);
					ResultSet  r=pst.executeQuery();
					if(r.next()) {
						
						JOptionPane.showMessageDialog(null," Log in successful");
						To_Do s= new To_Do(user);
						s.setVisible(true);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null," Incorrect UserID or Password");
					}
					
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(this,"Error in Connectivity!!","Alert",JOptionPane.WARNING_MESSAGE);
					}
			}
		}
		 
		if(e.getSource() == jb2) {
			dispose();
			new SignUp();
			
		}
		
}
	
	 @SuppressWarnings("deprecation")
	private boolean isAllFieldFillup(){
	        boolean fillup;
	        if(jt1.getText().trim().isEmpty()||jp1.getText().isEmpty()){

	            JOptionPane.showMessageDialog(this,"User Id or Password should not Empty.","Alert",JOptionPane.WARNING_MESSAGE);
	            
	            fillup = false;
	        }
	        else fillup = true;
	        return fillup;
	 }
	 
	 
	
	public static void main(String[] args) {
		
		 new Login();

	}

}

