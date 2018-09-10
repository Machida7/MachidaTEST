package test;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractCellEditor;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import librarian.DBConnection;

public class JLonJTTEST extends AbstractCellEditor implements TableCellRenderer {

static String text="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";


	public JLonJTTEST(final JTable table,final DefaultTableModel model) {
		
		

	}

	public static void main(String[] args) {
		 show();
	}

	private static void show() {
		 String[] names = {"No", "タイトル", "著者名", "発行日", "ジャンル","登録日", "みんなの評価", "レビュー"};
	        Object[][] data = {{ "sphere", "blue",""}, { "cone", "red",""}};
	        DBConnection con = new DBConnection();
			String selectAllBookSQL="select book_id,book_title,book_author,"
					+ "book_publication date,book_genre,book_added_date,number_of_star from librarian.bookshelf";
			con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
			con.sendSQLtoDB(selectAllBookSQL);

	        DefaultTableModel model = new DefaultTableModel(data, names);

	        model.setRowCount(0);
			ResultSet rs;

			try {
				rs = con.getPreStatement().executeQuery();
				while (rs.next()) {
					model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6),text  });
				}
				rs.close();
			}catch (SQLException e) {
					e.printStackTrace();
		}
	con.connectionClose();

	        JTable table = new JTable(model);
	        table.setRowHeight(100);
	        JScrollPane sp=new JScrollPane(table);
	        



	        TableColumnModel tcm=table.getColumnModel();
	        TableColumn col7=tcm.getColumn(6);
	        col7.setCellRenderer(new TextAreaCellRenderer1());



	        JFrame frame=new JFrame();
	        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        frame.getContentPane().add(sp);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);




	}

	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO 自動生成されたメソッド・スタブ
		return table;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}


class TextAreaCellRenderer1 extends JTextArea implements TableCellRenderer {
	  TextAreaCellRenderer1() {
	    setLineWrap(true);
	  }
	  @Override public Component getTableCellRendererComponent(
	        JTable table, Object value, boolean isSelected, boolean hasFocus,
	        int row, int column) {
	    
	    setText((value == null) ? "" : value.toString());
	    return this;
	  }
	}
