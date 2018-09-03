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
import javax.swing.table.TableColumnModel;

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
		LBWindow.cardPanel.add(new ReturnBookPanel(),"ReturnBookPanel");
		LBWindow.cardPanel.add(new FindBookPanel(),"FindBookPanel");
		LBWindow.cardPanel.add(new WomanRecommendationPanel(),"WomanRecommendationPanel");


		
		

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
				+ "user_pw,birthday,user_added_date,last_login_date,number_of_read"
				+ ")values('" + newUserName + "','" + newUserID +
				"','" + newUserPW + "','" + newUserBirthday + "','" + userAddedDate + "','99991231',0)";
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
		buttonAction();
		changePanel(e);
	}

	@Override
	public void buttonAction() {
		DBConnection con = new DBConnection();
		String selectRecoBookSQL = "select book_id,book_title,book_author,"
				+ "book_publication date,book_genre,number_of_reviewer,number_of_star from librarian.bookshelf";
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(selectRecoBookSQL);
		DefaultTableModel model = (DefaultTableModel) WomanRecommendationPanel.getRecommendationDisplayTable().getModel();

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
		ResultSet rs;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				if(Integer.parseInt(rs.getString(6))!=0) {
				int star =Integer.parseInt(rs.getString(7)) / Integer.parseInt(rs.getString(6)) ;
				System.out.println("平均評価="+star+"つ");
				if (star >=4) {
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
			e.printStackTrace();
		}

		con.connectionClose();
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
				+ "book_publication date,book_genre,book_added_date,number_of_reviewer,number_of_star from librarian.bookshelf";
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
				int NOR = Integer.parseInt(rs.getString(7));
				System.out.println("評価人数="+NOR+"人");
				if (NOR == 0) {
					model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), "まだ評価はないよ" });
				} else {

					model.addRow(new String[] { String.format("%05d", rs.getInt(1)),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), String.valueOf(Integer.parseInt(rs.getString(8)) / NOR) });
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
		buttonAction();
	}

	//以前記入した場合は、内容を更新するようにしたい
	@Override
	public void buttonAction() {
		String impressions = WriteReviewPanel.getReviewInputArea().getText();
		//星評価と感想入力　両方しないとダメ
		if (WriteReviewPanel.getStarRating() != 0 && Validate.blankCheck(impressions) == 0
				&& Validate.wordCountCheck(impressions, 400)==0) {
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
				e.printStackTrace();
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
				e.printStackTrace();
			}

			con.connectionClose();
			WriteReviewPanel.setStarRating(0);

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
		LBWindow.contentPane.remove(openWriteReviewPanelButtonAction.getWriteReviewP());
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
			e.printStackTrace();
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
		System.out.println("IDは" + con.getLoginUser_ID());
		System.out.println("PWは" + con.getLoginUser_PW());
		con.sendSQLtoDB(deleteBookSQL);
		try {
			int num = con.getPreStatement().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.selectBook_listAtwlp();

		con.connectionClose();

	}
}
