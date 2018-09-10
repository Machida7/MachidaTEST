package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import librarian.DBConnection;

public class LoginCheckTEST {

	public static void main(String[] args) {
		String userID = "114";
		String userPW = "1144514";

		DBConnection con = new DBConnection();
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());

		String selectSQL = "select user_name,last_login_date from librarian.user_list " +
				"where user_id='" + userID + "' and user_pw='"+userPW+"'";
		con.sendSQLtoDB(selectSQL);
		ResultSet rs;
		String userName = "null";
	
			try {
				rs = con.getPreStatement().executeQuery();
			
			while (rs.next()) {
				userName = rs.getString(1);

			}
			rs.close();
			
			System.out.println("名前は"+userName);
			
		} catch (SQLException e) {
				e.printStackTrace();
			}

	}

}
