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

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

//各ボタンのアクションの抽象クラス
public abstract class AbstractButtonAction implements ActionListener {
	public abstract void buttonAction();

	public void changePanel(ActionEvent event) {
		String cmd = event.getActionCommand();
		LBWindow.layout.show(LBWindow.cardPanel, cmd);
	}
}

//入館ボタン
class loginButtonAction extends AbstractButtonAction {
	private static Date loginDateFrom;
	private static Date loginDateTo;
	private static String loginUserName;

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

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();
		String userID = LoginPanel.getIDinputArea().getText();
		String userPW = new String(LoginPanel.getPWinputArea().getPassword());

		con.setLoginUser_ID(userID);
		con.setLoginUser_PW(userPW);

		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		System.out.println("IDは" + con.getLoginUser_ID());
		System.out.println("PWは" + con.getLoginUser_PW());

		//ユーザーネームの取得
		String selectUserNameSQL = "select user_name from librarian.user_list "
				+ "where user_id='" + userID + "'";
		con.sendSQLtoDB(selectUserNameSQL);
		ResultSet rs;
		String userName = null;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				userName = rs.getString(1);
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		setLoginUserName(userName);

		//前回のログイン日の取得
		String selectLoginDate = "select last_login_date from librarian.user_list "
				+ "where user_id='" + userID + "'";
		con.sendSQLtoDB(selectLoginDate);
		ResultSet rs1;
		String preLoginDateFrom = null;
		try {
			rs1 = con.getPreStatement().executeQuery();
			while (rs1.next()) {
				preLoginDateFrom = rs1.getString(1);
			}
			rs1.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("前回のログイン日は" + preLoginDateFrom);

		//前回のログイン日 String→Dateに変換してセット
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			setLoginDateFrom(sdf.parse(preLoginDateFrom));
		} catch (ParseException e1) {
			e1.printStackTrace();
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
			e1.printStackTrace();
		}
		String updateLoginDateSQL = "update librarian.user_list set last_login_date='" +
				userLoginDate + "' where user_id='" + con.getLoginUser_ID() + "'";
		con.sendSQLtoDB(updateLoginDateSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.connectionClose();

		LBWindow.cardPanel.add(new HomePanel(), "HomePanel");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
		changePanel(e);

		LoginPanel.getIDinputArea().setText("");
		LoginPanel.getPWinputArea().setText("");
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
		LBWindow.contentPane.add(addNewUserP, BorderLayout.CENTER);
		LBWindow.cardPanel.setVisible(false);
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
		String newUserID;
		newUserID = openAddUserWindowButtonAction.getAddNewUserP().getNewUserIDInputArea().getText();
		String newUserPW;
		newUserPW = openAddUserWindowButtonAction.getAddNewUserP().getNewUserPWInputArea().getText();

		//コンポジション
		DBConnection con = new DBConnection();
		//CREATE USER
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
		String createUserSqlStatement = "CREATE USER '" + newUserID + "'@localhost identified by '" + newUserPW + "'";
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		//user_listに新規ユーザーを追加
		String newUserName;
		newUserName = openAddUserWindowButtonAction.getAddNewUserP().getNewUserNameInputArea().getText();
		String newUserBirthday;
		newUserBirthday = openAddUserWindowButtonAction.getAddNewUserP().getNewUserBirthdayInputArea().getText();

		/*
		Date UserBirthday = null;
		//誕生日のString→Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
		try {
			UserBirthday = sdf.parse(openAddUserWindowButtonAction.addNewUserP.getNewUserBirthdayInputArea().getText());
		} catch (ParseException e) {
			e.printStackTrace();
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
				+ "user_pw,birthday,user_added_date,last_login_date)values('" + newUserName + "','" + newUserID +
				"','" + newUserPW + "','" + newUserBirthday + "','" + userAddedDate + "','99991231')";
		con.sendSQLtoDB(addUser_listSQL);
		try {
			int num = con.getPreStatement().executeUpdate();

			//権限付与
			String grantSQL = "grant insert,select,update,select on librarian.* to '"
					+ newUserID + "'@'localhost'";
			con.sendSQLtoDB(grantSQL);
			int num1 = con.getPreStatement().executeUpdate();
			System.out.println(newUserName + "に権限が付与されました");

			con.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//パネルを表示

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
		LBWindow.contentPane.remove(openAddUserWindowButtonAction.getAddNewUserP());
		LBWindow.cardPanel.setVisible(true);
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
		//MySQL上のユーザーのPWを変更
		String changePWSQL = "alter user '" + getIDchangingPW
				+ "'@'localhost' identified by '" + getPWAfterChange + "'";
		DBConnection con = new DBConnection();
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());

		con.sendSQLtoDB(changePWSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
			System.out.println(getIDchangingPW + "のPWは" + getPWAfterChange + "に変更されました。");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//user_list上のuser_pwを変更
		String changeUser_pwSQL = "update librarian.user_list set user_pw='" + getPWAfterChange + "' where user_id='"
				+ getIDchangingPW + "'";
		con.sendSQLtoDB(changeUser_pwSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
			System.out.println("user_listのpwを変更しました。");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.connectionClose();
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
		LBWindow.contentPane.remove(LoginPanel.getChangePWP());
		LBWindow.cardPanel.setVisible(true);
	}
}

//本を探すボタン
class openFindBookPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
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
		changePanel(e);
	}

	@Override
	public void buttonAction() {
	}
}

//本を返却するボタン
class openReturnBookPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
	}

	@Override
	public void buttonAction() {
	}
}

//お姉さんのおすすめボタン
class openWomanRecommendationPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		changePanel(e);
	}

	@Override
	public void buttonAction() {
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
		}
	}

	@Override
	public void buttonAction() {
		//画面切り替え
		womanRoomP = new WomanRoomPanel();
		LBWindow.contentPane.add(womanRoomP, BorderLayout.CENTER);
		LBWindow.cardPanel.setVisible(false);

		//ユーザーリストを表に表示

		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
		String selectUser_listSQL = "select user_number,user_name,user_id,user_pw,user_added_date,last_login_date from librarian.user_list";
		con.sendSQLtoDB(selectUser_listSQL);
		DefaultTableModel model = (DefaultTableModel) WomanRoomPanel.getAllUserListDisplayTable().getModel();
		model.setRowCount(0);
		ResultSet rs;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
						rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) });
			}

			//全ての本を表に表示
			String selectbookshelfSQL = "select book_id,book_title,book_author,"
					+ "book_publication date,book_genre,book_added_date from librarian.bookshelf";
			con.sendSQLtoDB(selectbookshelfSQL);
			DefaultTableModel model1 = (DefaultTableModel) WomanRoomPanel.getAllBookListDisplayTable().getModel();
			model1.setRowCount(0);
			ResultSet rs1;

			rs1 = con.getPreStatement().executeQuery();
			while (rs1.next()) {
				model1.addRow(new String[] { String.format("%05d", rs1.getInt(1)),
						rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6) });
			}
			rs.close();
			rs1.close();

			con.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();

		}
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
		LBWindow.contentPane.remove(openWomanRoomPanelButtonAction.womanRoomP);
		LBWindow.cardPanel.setVisible(true);
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
	}
}

//DBに本を追加するボタン
class addBookToDBButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();

		//プルダウンを更新するために、パネルを差し替える
		LBWindow.cardPanel.remove(LBWindow.getAddBookP());
		LBWindow.setAddBookP(new AddBookPanel());
		LBWindow.cardPanel.add(LBWindow.getAddBookP(), "AddBookPanel");
		LBWindow.layout.show(LBWindow.cardPanel, "AddBookPanel");
	}

	@Override
	public void buttonAction() {
		String addBookTitle = AddBookPanel.getAddBookTitleInputArea().getText();
		String addBookAuthor = AddBookPanel.getAddBookAuthorInputArea().getText();
		String addBookYearOfIssue = AddBookPanel.getAddBookYearOfIssueInputArea().getText();
		String addBookGenre = (String) AddBookPanel.getAddBookGenreInputArea().getSelectedItem();

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
				+ "book_publication,book_genre,book_added_date)values('" + addBookTitle + "','" + addBookAuthor +
				"','" + addBookYearOfIssue + "','" + addBookGenre + "','" + bookAddedDate + "')";

		DBConnection con = new DBConnection();
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		System.out.println(con.getLoginUser_ID());
		System.out.println(con.getLoginUser_PW());

		con.sendSQLtoDB(addBookshelfSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.connectionClose();



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

	}

	@Override
	public void buttonAction() {
	}
}

//フリーワード検索ボタン
class freeWordSearchButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
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
				+ "book_publication date,book_genre,book_added_date,number_of_star from librarian.bookshelf";
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(selectAllBookSQL);
		DefaultTableModel model = (DefaultTableModel) FindBookPanel.getBookListDisplayTable().getModel();

		//レビューを見るボタンを表につける
		OpenDisplayReviewPanelButton renderer = new OpenDisplayReviewPanelButton(
				FindBookPanel.getBookListDisplayTable(), model);
		TableColumn column7 = FindBookPanel.getBookListDisplayTable().getColumnModel().getColumn(7);
		column7.setCellEditor(renderer);
		column7.setCellRenderer(renderer);

		model.setRowCount(0);
		ResultSet rs;
		try {
			rs = con.getPreStatement().executeQuery();
			int i = 0;
			while (rs.next()) {
				model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
						rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7) });
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		con.connectionClose();
	}
}

//本を借りるボタン
class borrowBookButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
	}
}

//他のおすすめ表示ボタン
class otherRecommendationDisplayButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
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
		writeReviewP=new WriteReviewPanel();
		LBWindow.contentPane.add(writeReviewP, BorderLayout.CENTER);
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
		LBWindow.contentPane.remove(OpenDisplayReviewPanelButton.getDisplayReviewP());
		LBWindow.cardPanel.setVisible(true);
		}
}

//レビューを投稿するボタン
class postReviewButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void buttonAction() {
	}
}

//レビュー画面に戻るボタン
class returnDisplayReviewPanelButtonAction extends AbstractButtonAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		LBWindow.contentPane.remove(openWriteReviewPanelButtonAction.getWriteReviewP());
		OpenDisplayReviewPanelButton.getDisplayReviewP().setVisible(true);
	}
}

//ユーザー削除ボタン
class deleteUserButtonAction extends AbstractButtonAction {
	LBPanel lbp = new LBPanel();

	@Override
	public void actionPerformed(ActionEvent e) {
		buttonAction();
	}

	@Override
	public void buttonAction() {
		int row = lbp.getselectedTableRow(WomanRoomPanel.getAllUserListDisplayTable());
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
			e.printStackTrace();
		}
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
	}

	@Override
	public void buttonAction() {
	}
}
