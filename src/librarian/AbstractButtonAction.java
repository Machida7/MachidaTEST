package librarian;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

//各ボタンのアクションの抽象クラス
public abstract class AbstractButtonAction implements ActionListener {
	public abstract void buttonAction();

	public void changePanel(ActionEvent event) {
		String cmd = event.getActionCommand();
		RunLibrarian.getCardPanelLayout().show(RunLibrarian.getCardPanel(), cmd);
	}
}

//入館ボタン
class loginButtonAction extends AbstractButtonAction {
	private static Date loginDateFrom;
	private static Date loginDateTo;
	private static String loginUserName;

	private static HomePanel homeP;
	private static ReturnBookPanel returnBookP;
	private static FindBookPanel findBookP;
	private static WomanRecommendationPanel womanRecommendationP;

	public static HomePanel getHomeP() {
		return homeP;
	}

	public static void setHomeP(HomePanel homeP) {
		loginButtonAction.homeP = homeP;
	}

	public static ReturnBookPanel getReturnBookP() {
		return returnBookP;
	}

	public static void setReturnBookP(ReturnBookPanel returnBookP) {
		loginButtonAction.returnBookP = returnBookP;
	}

	public static FindBookPanel getFindBookP() {
		return findBookP;
	}

	public static void setFindBookP(FindBookPanel findBookP) {
		loginButtonAction.findBookP = findBookP;
	}

	public static WomanRecommendationPanel getWomanRecommendationP() {
		return womanRecommendationP;
	}

	public static void setWomanRecommendationP(WomanRecommendationPanel womanRecommendationP) {
		loginButtonAction.womanRecommendationP = womanRecommendationP;
	}

	public static String getLoginUserName() {
		return loginUserName;
	}

	public static void setLoginUserName(String loginUserName) {
		loginButtonAction.loginUserName = loginUserName;
	}

	public static Date getLoginDateFrom() {
		return loginDateFrom;
	}

	public static void setLoginDateFrom(Date loginDateFrom) {
		loginButtonAction.loginDateFrom = loginDateFrom;
	}

	public static Date getLoginDateTo() {
		return loginDateTo;
	}

	public static void setLoginDateTo(Date loginDateTo) {
		loginButtonAction.loginDateTo = loginDateTo;
	}

	public void buttonAction(String userID, String userPW) {

		DBConnection con = new DBConnection();
		con.setLoginUser_ID(userID);
		con.setLoginUser_PW(userPW);

		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		System.out.println("IDは" + con.getLoginUser_ID());
		System.out.println("PWは" + con.getLoginUser_PW());

		//ユーザーネームと最終ログイン日の取得
		String selectSQL = "select user_name,last_login_date from librarian.user_list "
				+ "where user_id='" + userID + "'";
		con.sendSQLtoDB(selectSQL);
		ResultSet rs;
		String userName = null;
		String preLoginDateFrom = null;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				userName = rs.getString(1);

				preLoginDateFrom = rs.getString(2);
			}
			rs.close();
		} catch (SQLException e2) {
			System.out.println("e2");
			System.out.println(e2.getMessage());
		}
		setLoginUserName(userName);

		System.out.println("前回のログイン日は" + preLoginDateFrom);

		//前回のログイン日 String→Dateに変換してセット
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			setLoginDateFrom(sdf.parse(preLoginDateFrom));
		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
		}
		System.out.println("セットした前ログ日" + loginButtonAction.getLoginDateFrom());

		//入館日の記録
		Calendar preUserLoginDate;
		preUserLoginDate = Calendar.getInstance();
		String userLoginDate = sdf.format(preUserLoginDate.getTime());
		System.out.println("新ログイン日は" + userLoginDate);
		try {
			setLoginDateTo(sdf.parse(userLoginDate));
		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
		}
		String updateLoginDateSQL = "update librarian.user_list set last_login_date='" +
				userLoginDate + "' where user_id='" + con.getLoginUser_ID() + "'";
		con.sendSQLtoDB(updateLoginDateSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		con.connectionClose();

		homeP = new HomePanel();
		returnBookP = new ReturnBookPanel();
		findBookP = new FindBookPanel();
		womanRecommendationP = new WomanRecommendationPanel();

		RunLibrarian.getCardPanel().add(homeP, "HomePanel");
		RunLibrarian.getCardPanel().add(returnBookP, "ReturnBookPanel");
		RunLibrarian.getCardPanel().add(findBookP, "FindBookPanel");
		RunLibrarian.getCardPanel().add(womanRecommendationP, "WomanRecommendationPanel");
		userID = null;
		userPW = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String userID = LoginPanel.getIDinputArea().getText();
		String userPW = new String(LoginPanel.getPWinputArea().getPassword());

		if (Validate.blankCheck(userID) == 1000 || Validate.blankCheck(userPW) == 1000) {
			System.out.println("入力して！");
			Validate.outputErrorMessage(1000);
		} else if (Validate.loginCheck(userID, userPW) == 2000) {
			Validate.outputErrorMessage(2000);
		} else {
			buttonAction(userID, userPW);
			changePanel(e);
		}
		LoginPanel.getIDinputArea().setText("");
		LoginPanel.getPWinputArea().setText("");

	}

	@Override
	public void buttonAction() {
		// TODO 自動生成されたメソッド・スタブ

	}
}

//新しく作るボタン
class openAddUserWindowButtonAction extends AbstractButtonAction {
	private static AddNewUserPanel addNewUserP;

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		addNewUserP = new AddNewUserPanel();
		RunLibrarian.getLibrarianContentPane().add(addNewUserP, BorderLayout.CENTER);
		RunLibrarian.getCardPanel().setVisible(false);
	}

	public static AddNewUserPanel getAddNewUserP() {
		return addNewUserP;
	}
}

//新規ユーザー登録ボタン
class addNewUserButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		String newUserID = openAddUserWindowButtonAction.getAddNewUserP().getNewUserIDInputArea().getText();
		String newUserPW = openAddUserWindowButtonAction.getAddNewUserP().getNewUserPWInputArea().getText();
		String newUserName = openAddUserWindowButtonAction.getAddNewUserP().getNewUserNameInputArea().getText();
		String newUserBirthday = openAddUserWindowButtonAction.getAddNewUserP().getNewUserBirthdayInputArea().getText();

		//空白チェック
		if (newUserID.equals("20文字まで") || newUserPW.equals("半角英数字16文字まで") ||
				newUserName.equals("半角英数字16文字まで") || newUserBirthday.equals("例：19990101")
				|| Validate.blankCheck(newUserID) == 1000 || Validate.blankCheck(newUserPW) == 1000
				|| Validate.blankCheck(newUserName) == 1000 || Validate.blankCheck(newUserBirthday) == 1000) {
			Validate.outputErrorMessage(1000);
			//文字数チェック
		} else if (Validate.wordCountCheck(newUserID, 16) == 1010 || Validate.wordCountCheck(newUserPW, 16) == 1010
				|| Validate.wordCountCheck(newUserName, 20) == 1010
				|| Validate.wordCountCheck(newUserBirthday, 8) == 1010) {
			Validate.outputErrorMessage(1010);
			//半角英数字のみチェック
		} else if (Validate.onlyHalfSizeCharaCheck(newUserID) == 1020
				|| Validate.onlyHalfSizeCharaCheck(newUserPW) == 1020) {
			Validate.outputErrorMessage(1020);
			//正しい日付かチェック
		} else if (Validate.dateCheck(newUserBirthday) == 1030) {
			Validate.outputErrorMessage(1030);
			//ユーザーID重複チェック
		} else if (Validate.userIDOverlapCheck(newUserID) == 2010) {
			Validate.outputErrorMessage(2010);

		} else {

			//コンポジション
			DBConnection con = new DBConnection();
			//CREATE USER
			con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
			String createUserSqlStatement = "CREATE USER '" + newUserID + "'@localhost identified by '" + newUserPW
					+ "'";
			con.sendSQLtoDB(createUserSqlStatement);
			try {
				boolean num = con.getPreStatement().execute();
				System.out.println("ユーザー作成完了　ID:" + newUserID + "  PW:" + newUserPW);

				/*ユーザー作成成功したか確認
				String selectUser = "select user,host from mysql.user";
				con.sendSQLtoDB(selectUser);
				ResultSet rs = con.preStatement.executeQuery();
				String user = null;
				while (rs.next()) {
					user = rs.getString("user");
					System.out.println(user);
				}
				rs.close();
				ここまで確認
				*/

				//user_listに新規ユーザーを追加

				/*
				Date UserBirthday = null;
				//誕生日のString→Date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
				try {
					UserBirthday = sdf.parse(openAddUserWindowButtonAction.addNewUserP.getNewUserBirthdayInputArea().getText());
				} catch (ParseException e) {
								System.out.println(e.getMessage());
				}
				//誕生日のDate型→String型
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyymmdd");
				String birthday = sdf1.format(UserBirthday);
				System.out.println("誕生日は"+birthday);
				*/

				//確認用
				System.out.println("Nameは" + newUserName);
				System.out.println("IDは" + newUserID);
				System.out.println("PWは" + newUserPW);
				System.out.println("誕生日は" + newUserBirthday);
				//ここまで確認用

				//ユーザー作成日
				Calendar preUserAddedDate;
				preUserAddedDate = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
				String userAddedDate = sdf1.format(preUserAddedDate.getTime());
				//DBに登録
				String addUser_listSQL = "insert into librarian.user_list(user_name,user_id,"
						+ "user_pw,birthday,user_added_date,last_login_date,number_of_read"
						+ ")values('" + newUserName + "','" + newUserID +
						"','" + newUserPW + "','" + newUserBirthday + "','" + userAddedDate + "','99991231',0)";
				con.sendSQLtoDB(addUser_listSQL);

				int num1 = con.getPreStatement().executeUpdate();

				//権限付与
				String grantSQL = "grant insert,select,update,select on librarian.* to '"
						+ newUserID + "'@'localhost'";
				con.sendSQLtoDB(grantSQL);
				int num2 = con.getPreStatement().executeUpdate();
				System.out.println(newUserName + "に権限が付与されました");

				con.connectionClose();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			//パネルを表示

		}
	}
}

//新規ユーザー登録画面からログイン画面に戻るボタン
class returnLoginPanelFromAddNewUserButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		RunLibrarian.getLibrarianContentPane().remove(openAddUserWindowButtonAction.getAddNewUserP());
		RunLibrarian.getCardPanel().setVisible(true);
	}
}

//PW変更ボタン
class PWChangeButtonAction extends AbstractButtonAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();

	}

	@Override
	public void buttonAction() {
		//入力されたuser_idを取得
		String getIDchangingPW = LoginPanel.getChangePWP().getIDchangingPWInputArea().getText();
		//入力された変更後のPWを取得
		String getPWAfterChange = LoginPanel.getChangePWP().getPWAfterChangeInputArea().getText();
		System.out.println(getIDchangingPW);
		System.out.println(getPWAfterChange);

		//空白チェック
		if (Validate.blankCheck(getIDchangingPW) == 1000 || Validate.blankCheck(getPWAfterChange) == 1000) {
			Validate.outputErrorMessage(1000);
			//ユーザーID存在チェック
		} else if (Validate.userIDOverlapCheck(getIDchangingPW) == 0) {
			Validate.outputErrorMessage(2020);
			//PW文字数チェック
		} else if (Validate.wordCountCheck(getPWAfterChange, 16) == 1010) {
			Validate.outputErrorMessage(1010);
			//PW半角のみチェック
		} else if (Validate.onlyHalfSizeCharaCheck(getPWAfterChange) == 1020) {
			Validate.outputErrorMessage(1020);

		} else {

			//MySQL上のユーザーのPWを変更
			String changePWSQL = "alter user '" + getIDchangingPW
					+ "'@'localhost' identified by '" + getPWAfterChange + "'";
			DBConnection con = new DBConnection();
			con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());

			con.sendSQLtoDB(changePWSQL);
			try {
				int num = con.getPreStatement().executeUpdate();
				System.out.println(getIDchangingPW + "のPWは" + getPWAfterChange + "に変更されました。");

				//user_list上のuser_pwを変更
				String changeUser_pwSQL = "update librarian.user_list set user_pw='" + getPWAfterChange
						+ "' where user_id='"
						+ getIDchangingPW + "'";
				con.sendSQLtoDB(changeUser_pwSQL);
				int num1 = con.getPreStatement().executeUpdate();
				System.out.println("user_listのpwを変更しました。");
				Validate.showMessagePanel("パスワード変更完了", "パスワードを変更しました");

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			con.connectionClose();
		}
	}
}

//PW変更画面からログイン画面に戻るボタン
class returnLoginPanelFromPWChangeButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		RunLibrarian.getLibrarianContentPane().remove(LoginPanel.getChangePWP());
		RunLibrarian.getCardPanel().setVisible(true);
	}
}

//本を探すボタン
class openFindBookPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
		LBPanel.setCardNum(1);
		System.out.println("ページ番号は" + LBPanel.getCardNum());
	}

	@Override
	public void buttonAction() {
	}
}

//本を追加するボタン
class openAddBookPanelButtonAction extends AbstractButtonAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
	}

	@Override
	public void buttonAction() {
	}
}

//お姉さんと遊ぶボタン
class openPlayWithWomanPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();

	}

	@Override
	public void buttonAction() {
		new WomanCatchGame();
	}
}

//本を返却するボタン
class openReturnBookPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
		changePanel(e);
		LBPanel.setCardNum(3);
		System.out.println("ページ番号は" + LBPanel.getCardNum());
	}

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();
		con.selectBorrowBook();
	}

}

//お姉さんのおすすめボタン
class openWomanRecommendationPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
		changePanel(e);
		//レビューを見るボタンのアクションに影響
		LBPanel.setCardNum(2);
		//他のおすすめ表示ボタンのアクションに影響
		WomanRecommendationPanel.setRecomNum(0);
		System.out.println("ページ番号は" + LBPanel.getCardNum());
	}

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();
		String selectRecoBookSQL = "select book_id,book_title,book_author,"
				+ "book_publication date,book_genre,number_of_reviewer,number_of_star from librarian.bookshelf";
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(selectRecoBookSQL);
		DefaultTableModel model = (DefaultTableModel) WomanRecommendationPanel.getRecommendationDisplayTable()
				.getModel();

		TableColumnModel tcm = WomanRecommendationPanel.getRecommendationDisplayTable().getColumnModel();

		//"みんなの評価"列に星評価を表示
		TableColumn col5 = tcm.getColumn(5);
		col5.setCellRenderer(new StarCellRenderer());

		//レビューを見るボタンを表につける
		OpenDisplayReviewPanelButton renderer = new OpenDisplayReviewPanelButton(
				FindBookPanel.getBookListDisplayTable(), model);
		TableColumn column6 = tcm.getColumn(6);
		column6.setCellEditor(renderer);
		column6.setCellRenderer(renderer);

		//評価が高いものを表に表示
		model.setRowCount(0);
		ResultSet rs;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				if (Integer.parseInt(rs.getString(6)) != 0) {
					int star = Integer.parseInt(rs.getString(7)) / Integer.parseInt(rs.getString(6));
					System.out.println("平均評価=" + star + "つ");
					if (star >= 4) {
						model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
								String.valueOf(star) });
					}
				}
			}
			//文字列を折り返す
			TableColumn col1 = tcm.getColumn(1);
			col1.setCellRenderer(new TextAreaCellRenderer());

			TableColumn col2 = tcm.getColumn(2);
			col2.setCellRenderer(new TextAreaCellRenderer());

			TableColumn col4 = tcm.getColumn(4);
			col4.setCellRenderer(new TextAreaCellRenderer());

			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		con.connectionClose();
	}

}

//他のおすすめ表示ボタン
class otherRecommendationDisplayButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		openWomanRecommendationPanelButtonAction action = new openWomanRecommendationPanelButtonAction();

		if (WomanRecommendationPanel.getRecomNum() == 0) {
			buttonAction();
			WomanRecommendationPanel.setRecomNum(1);
			System.out.println("ページ番号は" + LBPanel.getCardNum());

		} else if (WomanRecommendationPanel.getRecomNum() == 1) {
			action.buttonAction();
			WomanRecommendationPanel.setRecomNum(0);
			System.out.println("ページ番号は" + LBPanel.getCardNum());
		}

	}

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();

		//最後に借りた本のIDの取得
		String selectSqlLastBookID = "select last_borrowed_book from librarian.user_list where user_id='"
				+ con.getLoginUser_ID() + "'";
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(selectSqlLastBookID);
		ResultSet rs;
		int lastBookID = 0;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				lastBookID = rs.getInt(1);
			}

			//最後に借りた本のタイトル、著者名、ジャンルを取得（検索条件にする）
			String selectSqlLastBook = "select book_author,book_genre from librarian.bookshelf where "
					+ "book_id='" + lastBookID + "'";
			con.sendSQLtoDB(selectSqlLastBook);
			ResultSet rs1;
			String lastBookAuthor = null;
			String lastBookGenre = null;
			rs1 = con.getPreStatement().executeQuery();
			while (rs1.next()) {
				lastBookAuthor = rs1.getString(1);
				lastBookGenre = rs1.getString(2);
			}
			System.out.println("最後に借りたのは" + lastBookID + lastBookAuthor + lastBookGenre);

			//最後に借りた本と同じ作者、ジャンルの本を取得
			String selectSqlSimilarBook = "select book_id,book_title,book_author,book_publication,"
					+ "book_genre,number_of_reviewer,number_of_star from librarian.bookshelf "
					+ "where (book_author='" + lastBookAuthor + "' or book_genre='" + lastBookGenre + "') and "
					+ "book_id !='" + lastBookID + "'";
			con.sendSQLtoDB(selectSqlSimilarBook);
			DefaultTableModel model = (DefaultTableModel) WomanRecommendationPanel.getRecommendationDisplayTable()
					.getModel();

			TableColumnModel tcm = WomanRecommendationPanel.getRecommendationDisplayTable().getColumnModel();

			//"みんなの評価"列に星評価を表示
			TableColumn col5 = tcm.getColumn(5);
			col5.setCellRenderer(new StarCellRenderer());

			//レビューを見るボタンを表につける
			OpenDisplayReviewPanelButton renderer = new OpenDisplayReviewPanelButton(
					FindBookPanel.getBookListDisplayTable(), model);
			TableColumn column6 = tcm.getColumn(6);
			column6.setCellEditor(renderer);
			column6.setCellRenderer(renderer);

			model.setRowCount(0);
			ResultSet rs2;
			rs2 = con.getPreStatement().executeQuery();
			while (rs2.next()) {
				int numberOfReviewer = Integer.parseInt(rs2.getString(6));
				System.out.println("評価人数=" + numberOfReviewer + "人");
				if (numberOfReviewer == 0) {
					model.addRow(new String[] { String.format("%05d", rs2.getInt(1)),
							rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5),
							"0" });
				} else {

					model.addRow(new String[] { String.format("%05d", rs2.getInt(1)),
							rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5),
							String.valueOf(Integer.parseInt(rs2.getString(7)) / numberOfReviewer) });
				}
			}
			TableColumn col1 = tcm.getColumn(1);
			col1.setCellRenderer(new TextAreaCellRenderer());

			TableColumn col2 = tcm.getColumn(2);
			col2.setCellRenderer(new TextAreaCellRenderer());

			TableColumn col4 = tcm.getColumn(4);
			col4.setCellRenderer(new TextAreaCellRenderer());

			rs.close();
			rs1.close();
			rs2.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		con.connectionClose();

	}
}

//お姉さんの部屋ボタン
class openWomanRoomPanelButtonAction extends AbstractButtonAction {
	static WomanRoomPanel womanRoomP;
	private DBConnection con;

	@Override
	public void actionPerformed(ActionEvent e) {
		con = new DBConnection();
		//管理者かどうか判断
		if (con.getLoginUser_ID().equals(con.getAdministrator_ID())) {
			//管理者なら
			buttonAction();
		} else {
			//管理者でないなら
			System.out.println("あなたはダメです");
			Validate.showMessagePanel("秘密", "決して覗いてはいけません");
		}
	}

	@Override
	public void buttonAction() {
		//画面切り替え
		womanRoomP = new WomanRoomPanel();
		RunLibrarian.getLibrarianContentPane().add(womanRoomP, BorderLayout.CENTER);
		RunLibrarian.getCardPanel().setVisible(false);
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());

		//ユーザーリストを表に表示
		con.selectUser_listAtwlp();

		//全ての本を表に表示
		con.selectBook_listAtwlp();

		con.connectionClose();

	}
}

//お姉さんの部屋画面からホーム画面に戻るボタン
class returnHomePanalFromWomanRoomButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		RunLibrarian.getLibrarianContentPane().remove(openWomanRoomPanelButtonAction.womanRoomP);
		RunLibrarian.getCardPanel().setVisible(true);
	}
}

//ホーム画面からログイン画面に戻るボタン
class returnLoginPanelFromHomeButtonAction extends AbstractButtonAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
	}

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();
		con.setLoginUser_ID(null);
		con.setLoginUser_PW(null);
	}
}

//DBに本を追加するボタン
class addBookToDBButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		String addBookTitle = AddBookPanel.getAddBookTitleInputArea().getText();
		String addBookAuthor = AddBookPanel.getAddBookAuthorInputArea().getText();
		String addBookYearOfIssue = AddBookPanel.getAddBookYearOfIssueInputArea().getText();
		String addBookGenre = (String) AddBookPanel.getAddBookGenreInputArea().getSelectedItem();
		//空白チェック
		if (Validate.blankCheck(addBookTitle) == 1000 || Validate.blankCheck(addBookAuthor) == 1000
				|| Validate.blankCheck(addBookYearOfIssue) == 1000 || Validate.blankCheck(addBookGenre) == 1000) {
			Validate.outputErrorMessage(1000);
			//文字数チェック
		} else if (Validate.wordCountCheck(addBookGenre, 20) == 1010
				|| Validate.wordCountCheck(addBookTitle, 400) == 1010
				|| Validate.wordCountCheck(addBookAuthor, 650) == 1010) {
			Validate.outputErrorMessage(1010);
			//正しい日付かチェック
		} else if (Validate.dateCheck(addBookYearOfIssue) == 1030) {
			Validate.outputErrorMessage(1030);

		} else {

			Calendar preBookAddedDate;
			preBookAddedDate = Calendar.getInstance();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			String bookAddedDate = sdf1.format(preBookAddedDate.getTime());

			System.out.println("本のタイトルは" + addBookTitle);
			System.out.println("本の著者は" + addBookAuthor);
			System.out.println("本の発行年は" + addBookYearOfIssue);
			System.out.println("本のジャンルは" + addBookGenre);
			System.out.println("本の追加日は" + bookAddedDate);

			String addBookshelfSQL = "insert into librarian.bookshelf(book_title,book_author,"
					+ "book_publication,book_genre,book_added_date,number_of_rental,number_of_reviewer,number_of_star)"
					+ "values('" + addBookTitle + "','" + addBookAuthor +
					"','" + addBookYearOfIssue + "','" + addBookGenre + "','" + bookAddedDate + "',0,0,0)";

			DBConnection con = new DBConnection();
			con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
			System.out.println(con.getLoginUser_ID());
			System.out.println(con.getLoginUser_PW());

			con.sendSQLtoDB(addBookshelfSQL);
			try {
				int num = con.getPreStatement().executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			con.connectionClose();

			//プルダウンを更新するために、パネルを差し替える
			RunLibrarian.getCardPanel().remove(RunLibrarian.getAddBookP());
			RunLibrarian.setAddBookP(new AddBookPanel());
			RunLibrarian.getCardPanel().add(RunLibrarian.getAddBookP(), "AddBookPanel");
			RunLibrarian.getCardPanelLayout().show(RunLibrarian.getCardPanel(), "AddBookPanel");

		}
	}

}

//ホーム画面に戻るボタン
class returnHomePanalButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
	}

	@Override
	public void buttonAction() {

	}
}

//返却ボタン
class returnBookButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();

	}

	@Override
	public void buttonAction() {
		int row = ReturnBookPanel.getBorrowedBookDisplayTable().getSelectedRow();
		System.out.println(row + "行目");
		//本が選択されているかチェック
		if (row < 0) {
			Validate.outputErrorMessage(1050);
		} else {

			Object bookid = ReturnBookPanel.getBorrowedBookDisplayTable().getValueAt(row, 0);
			Object b_bookid = ReturnBookPanel.getBorrowedBookDisplayTable().getValueAt(row, 5);

			System.out.println("借りた本のID=" + bookid + ",BBookID=" + b_bookid);
			//貸出状態の確認
			if (Validate.rentalStatusCheck(bookid) == 0) {
				Validate.outputErrorMessage(2030);
				System.out.println("返却済みです");
			} else if (Validate.rentalStatusCheck(bookid) == 1) {

				DBConnection con = new DBConnection();
				con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());

				try {
					//bookshelfに対しての処理
					//貸出状態、借りている人の変更
					String updateSQLtoBookshelf = "update librarian.bookshelf set rental_status=0,borrowed_now=null "
							+ "where book_id='" + bookid + "'";
					con.sendSQLtoDB(updateSQLtoBookshelf);
					int num = con.getPreStatement().executeUpdate();
					System.out.println("bookshelfへの返却処理完了");

					//user_listに対しての処理
					//読んだ本の冊数の取得→+1
					String selectNORead = "select number_of_read from librarian.user_list where user_id='"
							+ con.getLoginUser_ID() + "'";
					con.sendSQLtoDB(selectNORead);
					ResultSet rs = con.getPreStatement().executeQuery();
					int NunOfRead = 0;
					while (rs.next()) {
						NunOfRead = rs.getInt(1);
					}
					NunOfRead++;
					//update 読んだ本の冊数
					String updateSQLtoUser_list = "update librarian.user_list set number_of_read='" + NunOfRead + "' "
							+ "where user_id='" + con.getLoginUser_ID() + "'";
					con.sendSQLtoDB(updateSQLtoUser_list);
					int num1 = con.getPreStatement().executeUpdate();
					System.out.println(NunOfRead + "冊目   user_listに対する返却処理完了");

					//borrowed_bookに対しての処理
					//返却日の取得
					Calendar preReturnDate;
					preReturnDate = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String returnDate = sdf.format(preReturnDate.getTime());
					//update 本を返した日
					String updateSQLtoBorrowed_book = "update librarian.borrowed_book set return_date='" + returnDate
							+ "' "
							+ "where b_book_id='" + b_bookid + "'";
					con.sendSQLtoDB(updateSQLtoBorrowed_book);
					int num2 = con.getPreStatement().executeUpdate();
					System.out.println(returnDate + "に返却しました");

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				con.connectionClose();

				RunLibrarian.getCardPanel().remove(loginButtonAction.getReturnBookP());
				loginButtonAction.setReturnBookP(new ReturnBookPanel());
				RunLibrarian.getCardPanel().add(loginButtonAction.getReturnBookP(), "ReturnBookPanel");
				RunLibrarian.getCardPanelLayout().show(RunLibrarian.getCardPanel(), "ReturnBookPanel");
				con.selectBorrowBook();
			}
		}
	}
}

//フリーワード検索ボタン
class freeWordSearchButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();

	}

	@Override
	public void buttonAction() {
		//入力された文字列の取得
		String searchWord = FindBookPanel.getFreeWordSearchInputArea().getText();
		if (Validate.blankCheck(searchWord) == 1000) {
			Validate.outputErrorMessage(1000);
		} else {
			String selectSqlFindBook = "select book_id,book_title,book_author,"
					+ "book_publication,book_genre,book_added_date,number_of_reviewer,number_of_star "
					+ "from librarian.bookshelf where concat(book_id,book_title,book_author,book_publication,"
					+ "book_genre,book_added_date) like '%" + searchWord + "%'";
			System.out.println(selectSqlFindBook);

			DBConnection con = new DBConnection();
			con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
			con.sendSQLtoDB(selectSqlFindBook);
			DefaultTableModel model = (DefaultTableModel) FindBookPanel.getBookListDisplayTable().getModel();

			TableColumnModel tcm = FindBookPanel.getBookListDisplayTable().getColumnModel();

			//"みんなの評価"列に星評価を表示
			TableColumn col6 = tcm.getColumn(6);
			col6.setCellRenderer(new StarCellRenderer());

			//レビューを見るボタンを表につける
			OpenDisplayReviewPanelButton renderer = new OpenDisplayReviewPanelButton(
					FindBookPanel.getBookListDisplayTable(), model);
			TableColumn column7 = tcm.getColumn(7);
			column7.setCellEditor(renderer);
			column7.setCellRenderer(renderer);

			model.setRowCount(0);
			ResultSet rs;
			try {
				rs = con.getPreStatement().executeQuery();

				while (rs.next()) {
					int numberOfReviewer = Integer.parseInt(rs.getString(7));
					System.out.println("評価人数=" + numberOfReviewer + "人");
					if (numberOfReviewer == 0) {
						model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getString(6), "0" });
					} else {

						model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getString(6),
								String.valueOf(Integer.parseInt(rs.getString(8)) / numberOfReviewer) });
					}
				}
				//文字列を折り返す
				TableColumn col1 = tcm.getColumn(1);
				col1.setCellRenderer(new TextAreaCellRenderer());

				TableColumn col2 = tcm.getColumn(2);
				col2.setCellRenderer(new TextAreaCellRenderer());

				TableColumn col4 = tcm.getColumn(4);
				col4.setCellRenderer(new TextAreaCellRenderer());

				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			con.connectionClose();

		}

	}
}

//全ての本を表示ボタン
class allBookListDisplayButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();
		String selectAllBookSQL = "select book_id,book_title,book_author,"
				+ "book_publication,book_genre,book_added_date,number_of_reviewer,number_of_star from librarian.bookshelf";
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(selectAllBookSQL);
		DefaultTableModel model = (DefaultTableModel) FindBookPanel.getBookListDisplayTable().getModel();

		TableColumnModel tcm = FindBookPanel.getBookListDisplayTable().getColumnModel();

		//"みんなの評価"列に星評価を表示
		TableColumn col6 = tcm.getColumn(6);
		col6.setCellRenderer(new StarCellRenderer());

		//レビューを見るボタンを表につける
		OpenDisplayReviewPanelButton renderer = new OpenDisplayReviewPanelButton(
				FindBookPanel.getBookListDisplayTable(), model);
		TableColumn column7 = tcm.getColumn(7);
		column7.setCellEditor(renderer);
		column7.setCellRenderer(renderer);

		model.setRowCount(0);
		ResultSet rs;
		try {
			rs = con.getPreStatement().executeQuery();

			while (rs.next()) {
				int numberOfReviewer = Integer.parseInt(rs.getString(7));
				System.out.println("評価人数=" + numberOfReviewer + "人");
				if (numberOfReviewer == 0) {
					model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), "0" });
				} else {

					model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), String.valueOf(Integer.parseInt(rs.getString(8)) / numberOfReviewer) });
				}
			}
			//文字列を折り返す
			TableColumn col1 = tcm.getColumn(1);
			col1.setCellRenderer(new TextAreaCellRenderer());

			TableColumn col2 = tcm.getColumn(2);
			col2.setCellRenderer(new TextAreaCellRenderer());

			TableColumn col4 = tcm.getColumn(4);
			col4.setCellRenderer(new TextAreaCellRenderer());

			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		con.connectionClose();
	}
}

//本を借りるボタン
class borrowBookButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		int row;
		Object bookid = null;
		String bookTitle = null;
		JTable table = null;
		//ページ確認
		if (LBPanel.getCardNum() == 1) {
			table = FindBookPanel.getBookListDisplayTable();
		} else if (LBPanel.getCardNum() == 2) {
			table = WomanRecommendationPanel.getRecommendationDisplayTable();
		}

		row = table.getSelectedRow();
		//選択チェック
		if (row < 0) {
			Validate.outputErrorMessage(1050);
		} else {
			//選択された行のbook_idを取得
			bookid = table.getValueAt(row, 0);
			//選択された行のbook_titleを取得
			bookTitle = (String) table.getValueAt(row, 1);
		}

		System.out.println("bookid=" + bookid);
		System.out.println("bookTitle=" + bookTitle);
		//貸出状態チェック
		if (Validate.rentalStatusCheck(bookid) == 1) {
			Validate.outputErrorMessage(2040);
		} else if (Validate.rentalStatusCheck(bookid) == 0) {
			DBConnection con = new DBConnection();
			con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());

			//bookshelfに対する処理
			//借りられた数の取得→+1する
			String selectSQLfrombookshelf = "select number_of_rental from librarian.bookshelf where book_id='"
					+ bookid + "'";
			con.sendSQLtoDB(selectSQLfrombookshelf);
			ResultSet rs;
			int numberOfRental = 0;
			try {
				rs = con.getPreStatement().executeQuery();
				while (rs.next()) {
					numberOfRental = rs.getInt(1);
				}

				numberOfRental++;
				//bookshelfのupdate 貸出状態、借りているユーザーID、レンタル数の変更
				String updateSQLtobookshelf = "update librarian.bookshelf set rental_status=1, borrowed_now='"
						+ con.getLoginUser_ID() + "',number_of_rental='" + numberOfRental + "' where book_id='" + bookid
						+ "'";
				con.sendSQLtoDB(updateSQLtobookshelf);
				int num = con.getPreStatement().executeUpdate();
				System.out.println("貸出状態変更完了");
				System.out.println("借りられた回数は" + numberOfRental);

				//user_listに対する処理
				//最後に借りた本の変更
				String updateSQLtouser_list = "update librarian.user_list set last_borrowed_book='" + bookid + "'"
						+ " where user_id='" + con.getLoginUser_ID() + "'";
				con.sendSQLtoDB(updateSQLtouser_list);
				int num1 = con.getPreStatement().executeUpdate();
				System.out.println("最後に借りた本の変更完了");

				//borrowed_bookに対する処理
				//貸出日　今日の日付の取得
				Calendar preBorrowedDate;
				preBorrowedDate = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String borrowedDate = sdf.format(preBorrowedDate.getTime());
				System.out.println("借りた日は" + borrowedDate);

				//insert　借りた日、借りた本のIDとタイトル、借りた人のIDの登録
				String insertSQLtoborrowed_book = "insert into librarian.borrowed_book(borrowed_date,return_date,"
						+ "borrowed_book_id,borrowed_book_title,borrower,post_review_id)values('"
						+ borrowedDate + "','99991231','" + bookid + "','" + bookTitle + "','" + con.getLoginUser_ID()
						+ "',0)";
				con.sendSQLtoDB(insertSQLtoborrowed_book);
				int num2 = con.getPreStatement().executeUpdate();

				System.out.println("貸出手続きすべて完了");

				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			con.connectionClose();
		}
	}
}

//レビューを書くボタン
class openWriteReviewPanelButtonAction extends AbstractButtonAction {
	private static WriteReviewPanel writeReviewP;

	public static WriteReviewPanel getWriteReviewP() {
		return writeReviewP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		writeReviewP = new WriteReviewPanel();
		RunLibrarian.getLibrarianContentPane().add(writeReviewP, BorderLayout.CENTER);
		OpenDisplayReviewPanelButton.getDisplayReviewP().setVisible(false);
	}
}

//レビュー画面から直前の画面に戻るボタン
class returnPreviousPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();

	}

	@Override
	public void buttonAction() {
		RunLibrarian.getLibrarianContentPane().remove(OpenDisplayReviewPanelButton.getDisplayReviewP());
		RunLibrarian.getCardPanel().setVisible(true);
	}
}

//レビューを投稿するボタン
class postReviewButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	//以前記入した場合は、内容を更新するようにしたい
	@Override
	public void buttonAction() {
		String impressions = WriteReviewPanel.getReviewInputArea().getText();
		//星評価と感想入力　両方しないとダメ 文字数チェック
		if (WriteReviewPanel.getStarRating() != 0 && Validate.blankCheck(impressions) == 0
				&& Validate.wordCountCheck(impressions, 400) == 0) {
			DBConnection con = new DBConnection();
			//DBのレビューリストに追加
			String postReviewSQL = "insert into librarian.review_list(reviewer_name,reviewed_book,star_rating,impressions,reviewed_bookid)"
					+ "values('" + loginButtonAction.getLoginUserName() + "','"
					+ OpenDisplayReviewPanelButton.getReviewBookTitle() + "','"
					+ WriteReviewPanel.getStarRating() + "','" + impressions + "','"
					+ OpenDisplayReviewPanelButton.getReviewBookID() + "')";
			con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
			con.sendSQLtoDB(postReviewSQL);
			try {
				int num = con.getPreStatement().executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("レビューを投稿しました");

			//bookshelfの評価人数(NOR)と星の数(NOS)を変更する　selectしてupdate
			//まずはselect
			String selectNowNORandNowNOS = "select number_of_reviewer,number_of_star from librarian.bookshelf "
					+ "where book_id ='" + OpenDisplayReviewPanelButton.getReviewBookID() + "'";
			con.sendSQLtoDB(selectNowNORandNowNOS);
			ResultSet rs;
			int nowNOR = 0;
			int nowNOS = 0;
			try {
				rs = con.getPreStatement().executeQuery();
				while (rs.next()) {
					nowNOR = rs.getInt(1);
					nowNOS = rs.getInt(2);
				}

				System.out.println("今の評価人数は" + nowNOR + "人");
				System.out.println("今の星評価数は" + nowNOS + "つ");
				if (nowNOR > 0) {
					System.out.println("今の平均評価は" + nowNOS / nowNOR + "デス");
				} else {
					System.out.println("誰もレビューを投稿していません");
				}

				//updateする
				int newNOR = nowNOR + 1;
				int newNOS = nowNOS + WriteReviewPanel.getStarRating();

				System.out.println("new評価人数は" + newNOR + "人");
				System.out.println("new星評価数は" + newNOS + "つ");
				System.out.println("new平均評価は" + newNOS / newNOR + "デス");
				System.out.println("new平均評価は" + (int) newNOS / newNOR + "デス");

				String updateNORandNOS = "update librarian.bookshelf set number_of_reviewer='"
						+ newNOR + "',number_of_star='" + newNOS + "' where book_id ='"
						+ OpenDisplayReviewPanelButton.getReviewBookID() + "'";

				con.sendSQLtoDB(updateNORandNOS);
				int num = con.getPreStatement().executeUpdate();

				System.out.println("評価更新完了");

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

			con.connectionClose();
			WriteReviewPanel.setStarRating(0);
		//文字数チェック
		}else if(Validate.wordCountCheck(impressions, 400)==1010) {
			Validate.outputErrorMessage(1010);
		//空欄がある
		}else {
			Validate.outputErrorMessage(1000);
		}
	}
}

//レビュー画面に戻るボタン
class returnDisplayReviewPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
		DBConnection con = new DBConnection();
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.selectReview();
		con.connectionClose();
	}

	@Override
	public void buttonAction() {
		RunLibrarian.getLibrarianContentPane().remove(openWriteReviewPanelButtonAction.getWriteReviewP());
		OpenDisplayReviewPanelButton.getDisplayReviewP().setVisible(true);
	}
}

//ユーザー削除ボタン
class deleteUserButtonAction extends AbstractButtonAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		int row = LBPanel.getselectedTableRow(WomanRoomPanel.getAllUserListDisplayTable());
		//表の選択されたuser_numberを取得
		Object user_number = WomanRoomPanel.getAllUserListDisplayTable().getValueAt(row, 0);
		//user_list上からuserを削除
		String deleteUserSQL = "DELETE from librarian.user_list WHERE user_number=" + user_number;
		DBConnection con = new DBConnection();
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		System.out.println("IDは" + con.getLoginUser_ID());
		System.out.println("PWは" + con.getLoginUser_PW());
		con.sendSQLtoDB(deleteUserSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
			//表の選択されたuser_idを取得
			Object dropUserID = WomanRoomPanel.getAllUserListDisplayTable().getValueAt(row, 2);
			//DB上のuserを削除
			String dropUserSQL = "drop user'" + dropUserID + "'@'localhost'";
			System.out.println(dropUserSQL);
			con.sendSQLtoDB(dropUserSQL);
			int num1 = con.getPreStatement().executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		con.selectUser_listAtwlp();

		con.connectionClose();

	}
}

//ユーザーネーム変更ボタン
class changeUserNameButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
	}
}

//ユーザーPW変更ボタン
class changeUserPWButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
	}
}

//本の情報更新ボタン
class updateBookInfomationButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
	}
}

//本の削除ボタン
class deleteBookButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		Object bookID = WomanRoomPanel.getAllBookListDisplayTable()
				.getValueAt(LBPanel.getselectedTableRow(WomanRoomPanel.getAllBookListDisplayTable()), 0);
		String deleteBookSQL = "DELETE from librarian.bookshelf WHERE book_id=" + bookID;
		DBConnection con = new DBConnection();
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(deleteBookSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
			System.out.println(bookID + "を削除しました。");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		con.selectBook_listAtwlp();

		con.connectionClose();

	}
}
