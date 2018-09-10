package librarian;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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

	//引数のStringが、半角英数のみでないならエラー
	public static int onlyHalfSizeCharaCheck(String text) {
		if (!text.matches("^[0-9a-zA-Z]+$")) {
			return 1020;
		}
		return 0;
	}

	//引数のStringが正しい日付型(yyyyMMdd型かつ半角のみ)でないならエラー
	public static int dateCheck(String text) {
		try {
			LocalDate date = LocalDate.parse(text,
					DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT));
			System.out.println(date);
			return 0;
		} catch (DateTimeParseException e) {
			System.out.println("エラー");
			return 1030;
		}

	}

	//ログイン認証
	public static int loginCheck(String ID, String PW) {
		DBConnection con = new DBConnection();
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
		//入力されたIDとPWに紐づくユーザーネームの取得
		String selectSQL = "select user_name from librarian.user_list " +
				"where user_id='" + ID + "' and user_pw='" + PW + "'";
		con.sendSQLtoDB(selectSQL);
		ResultSet rs;
		String userName = "null";
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				userName = rs.getString(1);
			}
			rs.close();
			System.out.println("名前は" + userName);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//IDとPWが無ければuserNameはnullのまま
		if (userName.equals("null")) {
			return 2000;
		} else {
			return 0;
		}
	}

	//ユーザーID重複（存在）チェック
	public static int userIDOverlapCheck(String userID) {
		DBConnection con = new DBConnection();
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
		//入力されたIDを検索条件にしてIDを取得
		String selectSQL = "select user_id from librarian.user_list " +
				"where user_id='" + userID + "'";
		con.sendSQLtoDB(selectSQL);
		ResultSet rs;
		String selectedUserID = "null";
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				selectedUserID = rs.getString(1);
			}
			rs.close();
			System.out.println("名前は" + selectedUserID);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//IDが無ければuserNameはnullのまま
		if (selectedUserID.equals("null")) {
			//ID存在しない
			return 0;
		} else {
			//ID存在する
			return 2010;
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
			errorMessage = "未入力欄があります。";
			break;
		//文字数チェック
		case 1010:
			errorMessage = "入力文字数を確認してください。";
			break;

		case 1020:
			errorMessage = "IDまたはパスワードは半角英数字のみで設定してください。";
			break;

		case 1030:
			errorMessage = "日付は 19900101 のように入力して下さい。";
			break;

		case 1040:
			errorMessage = "表示対象のデータが存在しません。";
			break;

		case 1050:
			errorMessage = "本を選択してください。";
			break;

		case 2000:
			errorMessage = "IDまたはパスワードを確認してください。";
			break;

		case 2010:
			errorMessage = "そのIDは既に使用されています。他のIDを入力して下さい。";
			break;
			
		case 2020:
			errorMessage="入力されたIDのユーザーは存在しません。";
			break;
			
		case 2030:
			errorMessage="この本は返却されています。";
			break;	
			
		case 2040:
			errorMessage="この本は貸出中です。";
			break;	
			
			
		}
		//エラーの番号とメッセージをメッセージ表示メソッドに渡す
		showErrorMessagePanel(errorMessage);
	}

	//メッセージをパネルに表示する
	public static void showErrorMessagePanel(String Message) {
		JFrame frame = new JFrame();

		JLabel errorMessage = new JLabel(Message);
		errorMessage.setForeground(Color.RED);

		JPanel p = new JPanel(new BorderLayout());
		p.add(errorMessage, BorderLayout.CENTER);
		JOptionPane.showMessageDialog(frame, p, "エラー", JOptionPane.ERROR_MESSAGE);
	}

	public static void showMessagePanel(String title, String _message) {
		JFrame frame = new JFrame();

		ImageIcon icon = new ImageIcon("./src/librarian/tosyokan_woman.png");
		MediaTracker tracker = new MediaTracker(frame);
		Image smallImg = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.5), -1,
				Image.SCALE_SMOOTH);
		tracker.addImage(smallImg, 2);
		ImageIcon smallIcon = new ImageIcon(smallImg);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(frame, _message, title,
				JOptionPane.INFORMATION_MESSAGE, smallIcon);

	}

}