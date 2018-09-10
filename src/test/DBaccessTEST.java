package test;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
public class DBaccessTEST extends JFrame {
	

	

	 

	   private JPanel contentPane;
	   private JTable table;

	   /**
	    * Launch the application.
	    */
	   public static void main(String[] args) {
	      EventQueue.invokeLater(new Runnable() {
	         public void run() {
	            try {
	               DBaccessTEST frame = new DBaccessTEST();
	               frame.setVisible(true);
	            } catch (Exception e) {
	               e.printStackTrace();
	            }
	         }
	      });
	   }

	   /**
	    * Create the frame.
	    */
	   public DBaccessTEST() {
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setBounds(100, 100, 450, 300);
	      contentPane = new JPanel();
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	      setContentPane(contentPane);
	      contentPane.setLayout(null);
	      // テーブル
	      JScrollPane scrollPane = new JScrollPane();
	      scrollPane.setBounds(5, 40, 424, 181);
	      contentPane.add(scrollPane);
	      table = new JTable();
	      table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"PID", "NAIYOU"}) {
	    	  public boolean isCellEditable(int row) {
	    		  return false;
	    	  }
	      });
	      table.getColumnModel().getColumn(1).setPreferredWidth(250);
	      scrollPane.setViewportView(table);
	      // 終了
	      JButton btnEnd = new JButton("終了");
	      btnEnd.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            System.exit(0);
	         }
	      });
	      btnEnd.setBounds(338, 231, 91, 21);
	      contentPane.add(btnEnd);
	      // 表示
	      JButton btnShow = new JButton("表示");
	      btnShow.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            setTable();
	         }
	      });
	      btnShow.setBounds(338, 10, 91, 21);
	      contentPane.add(btnShow);
	   }

	   /*
	    * データベースに接続をして、取得結果をテーブルへ表示
	    */
	   private void setTable()
	   {
	      this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	      try {
	         // データベース接続
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:3306?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false",
	               "root","iface-pc");
	         Statement st = conn.createStatement();
	         ResultSet rs = st.executeQuery("SELECT*FROM database1.TBL_HOGE");
	         // テーブルへ設定
	         DefaultTableModel model = (DefaultTableModel)table.getModel();
	         model.setRowCount(0);
	         while(rs.next())
	         {
	            model.addRow(new String[]{rs.getString(1), rs.getString(2)});
	         }
	         // データベース切断
	         rs.close();
	         st.close();
	         conn.close();
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	   }
	}

