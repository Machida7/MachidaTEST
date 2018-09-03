package librarian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

//データベースにアクセスするためのクラス
public class DBConnection {
	private Connection conn = null;
	private PreparedStatement preStatement = null;

	private static ResultSet rs = null;
	private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306?"
			+ "characterEncoding=UTF-8&serverTimezone=JST&useSSL=false"
			+ "&allowPublicKeyRetrieval=true";
	private final String administrator_ID = "root";
	private final String administrator_PW = "iface-pc";
	private static String loginUser_ID;
	private static String loginUser_PW;


	public void setLoginUser_ID(String user_ID) {
		DBConnection.loginUser_ID = user_ID;
	}

	public String getLoginUser_ID() {
		return DBConnection.loginUser_ID;
	}

	public void setLoginUser_PW(String user_PW) {
		DBConnection.loginUser_PW = user_PW;
	}

	public String getLoginUser_PW() {
		return DBConnection.loginUser_PW;
	}

	public PreparedStatement getPreStatement() {
		return preStatement;
	}

	public String getAdministrator_ID() {
		return administrator_ID;
	}

	public String getAdministrator_PW() {
		return administrator_PW;
	}

	//引数のID(user),PWでMySQLに接続する
	public void dbConnection(String ID, String PW) {
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, ID, PW);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	//引数のSQL文を実行する
	public void sendSQLtoDB(String _sqlStatement) {
		try {
			preStatement = conn.prepareStatement(_sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//DBとの接続を終了する
	public void connectionClose() {
		try {
			preStatement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//お姉さんの部屋において、表にユーザーリストを表示する
	public void selectUser_listAtwlp() {
	String selectUser_listSQL = "select user_number,user_name,user_id,user_pw,user_added_date,last_login_date from librarian.user_list";
	sendSQLtoDB(selectUser_listSQL);
	DefaultTableModel model = (DefaultTableModel) WomanRoomPanel.getAllUserListDisplayTable().getModel();
	model.setRowCount(0);
	ResultSet rs;
	try {
		rs = getPreStatement().executeQuery();
		while (rs.next()) {
			model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
					rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) });
		}
		rs.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}

	}


	//お姉さんの部屋において、表に本のリストを表示する
	public void selectBook_listAtwlp() {
		String selectbookshelfSQL = "select book_id,book_title,book_author,"
				+ "book_publication date,book_genre,book_added_date from librarian.bookshelf";
		sendSQLtoDB(selectbookshelfSQL);
		DefaultTableModel model = (DefaultTableModel) WomanRoomPanel.getAllBookListDisplayTable().getModel();
		model.setRowCount(0);
		ResultSet rs;

		try {
			rs = getPreStatement().executeQuery();
		while (rs.next()) {
			model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
					rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) });
		}
		rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
	}

	}

	//レビューを見る画面において、星評価と感想を表示する
	public void selectReview() {
		String selectReviewSQL = "select star_rating,impressions from librarian.review_list "
				+ "where reviewed_bookid='" + OpenDisplayReviewPanelButton.getReviewBookID() + "'";

		sendSQLtoDB(selectReviewSQL);
		DefaultTableModel model = (DefaultTableModel) DisplayReviewPanel.getReviewDisplayTable().getModel();
		model.setRowCount(0);
		ResultSet rs;
		try {
			rs = getPreStatement().executeQuery();
			while (rs.next()) {
				model.addRow(new String[] { rs.getString(1), rs.getString(2) });
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
