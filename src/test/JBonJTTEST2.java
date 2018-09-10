package test;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import librarian.DBConnection;

public class JBonJTTEST2 extends AbstractCellEditor implements TableCellEditor,TableCellRenderer {


	private final JButton button;

	public JBonJTTEST2(final JTable table,final DefaultTableModel model) {
		this.button=new JButton("ボタン");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
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
							rs.getString(6),rs.getString(7)  });
				}
				rs.close();
			}catch (SQLException e) {
					e.printStackTrace();
		}
	con.connectionClose();

	        JTable table = new JTable(model);
	        JScrollPane sp=new JScrollPane(table);
	        JBonJTTEST2 renderer = new JBonJTTEST2(table, model);
	        TableColumn column0=table.getColumnModel().getColumn(7);
	        column0.setCellEditor(renderer);
	        column0.setCellRenderer(renderer);




	        JFrame frame=new JFrame();
	        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        frame.getContentPane().add(sp);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);




	}

	@Override
	public Object getCellEditorValue() {
		return button.getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO 自動生成されたメソッド・スタブ
		return button;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO 自動生成されたメソッド・スタブ
		return button;
	}

}
