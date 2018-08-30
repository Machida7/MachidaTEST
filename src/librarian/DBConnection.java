package librarian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
