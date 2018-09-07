package librarian;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Validate {

	//空白チェック
	public static int blankCheck(String text) {
		if (text.equals(" ") || text.isEmpty()) {
			return 1000;
		} else {
			return 0;
		}
	}

	//引数のStringの長さが、int以上ならエラー：1010
		public static int wordCountCheck(String text, int num) {
			if (text.length() > num) {
				return 1010;
			} else {
				return 0;
			}
		}

	//貸出状態のチェック　貸出可：０　貸出中：１
		public static int rentalStatusCheck(Object bookID) {
			String selectSQLfrombookshelf = "select rental_status from librarian.bookshelf where book_id='"
					+ bookID + "'";
			DBConnection con = new DBConnection();
			con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
			con.sendSQLtoDB(selectSQLfrombookshelf);
			ResultSet rs;
			int rentalStatus = 0;

				try {
					rs = con.getPreStatement().executeQuery();

				while (rs.next()) {
					rentalStatus = rs.getInt(1);
				}
				rs.close();
			} catch (SQLException e) {
					e.printStackTrace();
				}

			return rentalStatus;


		}





}