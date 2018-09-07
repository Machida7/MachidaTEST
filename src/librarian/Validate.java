package librarian;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

		
		//生じたエラーのintを引数として、それに応じてメッセージがerrorMessageに代入される
		public static void outputErrorMessage(int errorNumber) {
			String errorMessage = null;

			switch (errorNumber) {
			//空白チェック
			case 1000:
				errorMessage = "未入力欄があります";
				break;
			//文字数チェック
			case 1010:
				errorMessage = "";
				break;

			case 1020:
				errorMessage = "入力フィールドに半角文字が入力されています。";
				break;

			case 1030:
				errorMessage = "更新対象のデータが存在しません。";
				break;

			case 1040:
				errorMessage = "表示対象のデータが存在しません。";
				break;

			case 1050:
				errorMessage = "データを選択してください。";
				break;
			}
			//エラーの番号とメッセージをメッセージ表示メソッドに渡す
			showErrorMessagePanel(errorMessage);
		}
		
		
		//メッセージをパネルに表示する
		public static void showErrorMessagePanel(String Message) {
			JFrame frame = new JFrame();

			frame.setTitle("ERROR");
			JLabel errorMessage = new JLabel(Message);
			errorMessage.setForeground(Color.RED);

			JPanel p = new JPanel(new BorderLayout());
			p.add(errorMessage, BorderLayout.CENTER);
			JOptionPane.showMessageDialog(frame, p);
		}

		public static void showMessagePanel(String title,String _message) {
			JFrame frame = new JFrame();
		
			 ImageIcon icon = new ImageIcon("./src/librarian/tosyokan_woman.png");
			 
			JOptionPane.showMessageDialog(frame, _message, title,
				      JOptionPane.INFORMATION_MESSAGE, icon);
			
		}
		
		
		
		
		

}