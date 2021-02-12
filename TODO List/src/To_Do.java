
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class To_Do extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					To_Do frame = new To_Do("Vaibhav_X");
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	java.sql.Connection con=null;
	private String user;
	public To_Do(String user) {
		
		this.user = user;
		
		con=DB.dbconnect();
		
		
		setTitle("TO-DO List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 595);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("To-Do List");
		lblNewLabel.setFont(new Font("Rockwell Condensed", Font.BOLD, 25));
		lblNewLabel.setBounds(411, 26, 126, 47);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Other Tasks :");
		lblNewLabel_2.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		lblNewLabel_2.setBounds(47, 188, 119, 22);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(183, 124, 223, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Important Task :");
		lblNewLabel_3.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		lblNewLabel_3.setBounds(47, 124, 126, 22);
		contentPane.add(lblNewLabel_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(176, 177, 230, 268);
		contentPane.add(textArea);
		
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			if(textField.getText().isEmpty()||textArea.getText().isEmpty()) {
				JOptionPane.showMessageDialog(btnNewButton, "Please enter task!!!!");
			}
			else {
				try {
					String imp=textField.getText();
					String other=textArea.getText();
					PreparedStatement pst =con.prepareStatement("insert into to_do(user_id,important,others)values(?,?,?)");
					pst.setString(1, user);
					pst.setString(2, imp);
					pst.setString(3, other);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(btnNewButton, "Task Added");
					textField.setText("");
					textArea.setText("");
					
					refresh();
						
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(btnNewButton,"Error in Connectivity!!","Alert",JOptionPane.WARNING_MESSAGE);
				}
			}
					
			}
		});
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		btnNewButton.setBounds(47, 483, 85, 28);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				DefaultTableModel df=(DefaultTableModel) table.getModel();
				int s=table.getSelectedRow();
				if(s!=-1) {
				int id=Integer.parseInt(df.getValueAt(s, 0).toString());
					try {			
						String imp=textField.getText();
						String other=textArea.getText();
						PreparedStatement pst=con.prepareStatement("update to_do set important=?,others=? where id=?");
						pst.setString(1, imp);
						pst.setString(2, other);
						pst.setInt(3,id);
						int rs = pst.executeUpdate();
						if(rs == -1) {
							JOptionPane.showMessageDialog(btnNewButton_1, "Please Select Task!!!!","Alert",JOptionPane.WARNING_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(btnNewButton_1, "Task updated succsefully!!");
							textField.setText("");
							textArea.setText("");
						}
						
					
						refresh();
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(btnNewButton_1, "Please Select Task!!!!","Alert",JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(btnNewButton_1, "Please Select Task!!!!","Alert",JOptionPane.WARNING_MESSAGE);
				}
		
				btnNewButton.setEnabled(true);
			}
		});
		btnNewButton_1.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setBounds(172, 483, 85, 28);
		contentPane.add(btnNewButton_1);
		
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login();
			dispose();
			}
		});
		btnNewButton_2.setBackground(Color.BLACK);
		btnNewButton_2.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBounds(410, 483, 85, 28);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				DefaultTableModel df=(DefaultTableModel) table.getModel();
				int s=table.getSelectedRow();
				
				if(s!= -1) {
					String n = df.getValueAt(s, 0).toString();
					int id=Integer.parseInt(n);
				try {			
					PreparedStatement pst=con.prepareStatement("delete from to_do where id=?");
					pst.setInt(1,id);
					int rs = pst.executeUpdate();
					if(rs == -1) {
						JOptionPane.showMessageDialog(btnNewButton_3, "Please Select Task!!!!","Alert",JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(btnNewButton_3, "Task deleted succsefully!!");
						textField.setText("");
						textArea.setText("");
					}
					
					
					refresh();
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(btnNewButton_3, "Please Select Task!!!!","Alert",JOptionPane.WARNING_MESSAGE);
				}
			}
				else {
					JOptionPane.showMessageDialog(btnNewButton_3, "Please Select Task!!!!","Alert",JOptionPane.WARNING_MESSAGE);
				}
				
				btnNewButton.setEnabled(true);
			}
			
			});
		btnNewButton_3.setBackground(Color.BLACK);
		btnNewButton_3.setFont(new Font("Rockwell Condensed", Font.BOLD, 18));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.setBounds(300, 483, 85, 28);
		contentPane.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(461, 121, 426, 345);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
			
				DefaultTableModel df=(DefaultTableModel) table.getModel();
				int selected=table.getSelectedRow();
				textField.setText(df.getValueAt(selected, 1).toString());
				textArea.setText(df.getValueAt(selected, 2).toString());
				btnNewButton.setEnabled(false);
				
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Important", "Others"
			}
		));
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(118);
		table.getColumnModel().getColumn(2).setPreferredWidth(176);
		
		refresh();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void refresh() {
		PreparedStatement pst;
		int a;
		DefaultTableModel df1=(DefaultTableModel) table.getModel();
		df1.setRowCount(0);
		try {
			pst=con.prepareStatement("select * from to_do where user_id = ?");
			pst.setString(1, user);
			ResultSet rs=pst.executeQuery();
			ResultSetMetaData rd= (ResultSetMetaData) rs.getMetaData();
			a=rd.getColumnCount();
			while(rs.next())
			{
				Vector v2=new Vector();
				for(int i=1;i<=a;i++) {
					v2.add(rs.getString("id"));
					v2.add(rs.getString("important"));
					v2.add(rs.getString("others"));
				}
				df1.addRow(v2);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Connecting to database......");
			con=DB.dbconnect();
			refresh();

		}
		
		
	}
}
