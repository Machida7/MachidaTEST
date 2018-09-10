package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import librarian.DBConnection;

public class DBselectTEST {
	
	
	
	public static void main(String[] args) {
		DBConnection con=new DBConnection();
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
		String getGenreSQL = "SELECT book_genre FROM librarian.bookshelf";
		con.sendSQLtoDB(getGenreSQL);
		ResultSet rs;
		List<String> preList=new ArrayList<>();
		
		try {
			rs = con.getPreStatement().executeQuery();

			while (rs.next()) {
				preList.add(rs.getString(1));
			}

			rs.close();
			con.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(preList);
		System.out.println(new HashSet<>(preList));
		
	}

}
