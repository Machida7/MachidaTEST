package librarian;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

//パネルを作るクラス
public class LBPanel extends JPanel {
	private GridBagLayout gridBagLay;
	protected GridBagConstraints gridBagCon;

	private ImageIcon woman;
	private final String womanIcon = "./src/librarian/tosyokan_woman.png";
	private JLabel womanLabel;

	private ImageIcon star0;
	private final String srar0Icon = "./src/librarian/star0.png";
	private JLabel star0Label;

	private ImageIcon star1;
	private final String srar1Icon = "./src/librarian/star1.png";
	private JLabel star1Label;

	private ImageIcon star2;
	private final String srar2Icon = "./src/librarian/star2.png";
	private JLabel star2Label;

	private ImageIcon star3;
	private final String srar3Icon = "./src/librarian/star3.png";
	private JLabel star3Label;

	private ImageIcon star4;
	private final String srar4Icon = "./src/librarian/star4.png";
	private JLabel star4Label;

	private ImageIcon star5;
	private final String srar5Icon = "./src/librarian/star5.png";
	private JLabel star5Label;

	protected MakeButton returnHomePanalButton = new MakeButton("前の画面に戻る", new returnHomePanalButtonAction(),
			"HomePanel");

	protected MakeButton borrowBookButton = new MakeButton("借りる", new borrowBookButtonAction());

	/* ページ番号的な
	 * 1　本を探す画面
	 * 2　お姉さんのおすすめ画面
	 * 3  本の返却画面
	 */
	private static int cardNum;

	//GridBagLayoutを呼び出す
	public void prepareGridBag() {
		gridBagLay = new GridBagLayout();
		this.setLayout(gridBagLay);
		gridBagCon = new GridBagConstraints();
	}

	//画像ラベル作成
	public Component makePictureLabel(ImageIcon iconName, String URL, JLabel labelName) {
		iconName = new ImageIcon(URL);
		labelName = new JLabel(iconName);
		return labelName;
	}

	//お姉さんの画像ラベル
	public Component makeWoman() {
		return makePictureLabel(woman, womanIcon, womanLabel);
	}

	//星0ラベル
	public Component makeStar0() {
		return makePictureLabel(star0, srar0Icon, star0Label);
	}

	//星1ラベル
	public Component makeStar1() {
		return makePictureLabel(star1, srar1Icon, star1Label);
	}

	//星2ラベル
	public Component makeStar2() {
		return makePictureLabel(star2, srar2Icon, star2Label);
	}

	//星3ラベル
	public Component makeStar3() {
		return makePictureLabel(star3, srar3Icon, star3Label);
	}

	//星4ラベル
	public Component makeStar4() {
		return makePictureLabel(star4, srar4Icon, star4Label);
	}

	//星5ラベル
	public Component makeStar5() {
		return makePictureLabel(star5, srar5Icon, star5Label);
	}

	//コンポーネントを配置
	public void arrangeComponents(Component component,
			int gridX, int gridY,
			int gridWidth, int gridHeight,
			double weightX, double weightY) {

		gridBagCon.gridx = gridX;
		gridBagCon.gridy = gridY;
		gridBagCon.gridwidth = gridWidth;
		gridBagCon.gridheight = gridHeight;
		gridBagCon.weightx = weightX;
		gridBagCon.weighty = weightY;
		gridBagCon.fill = GridBagConstraints.BOTH;
		gridBagLay.setConstraints(component, gridBagCon);

		this.add(component);
	}

	//表の選択されている行(横,y)を取得
	public static int getselectedTableRow(JTable table) {
		int row = table.getSelectedRow();
		return row;
	}

	//表の選択されている列(縦,ｘ)を取得
	public int getselectedTableColumn(JTable table) {
		int column = table.getSelectedRow();
		return column;
	}

	public static int getCardNum() {
		return cardNum;
	}

	public static void setCardNum(int cardNum) {
		LBPanel.cardNum = cardNum;
	}

}
//以下、各パネル

//ログイン画面
class LoginPanel extends LBPanel {
	private static JTextField IDinputArea;
	private static JPasswordField PWinputArea;

	public static JTextField getIDinputArea() {
		return IDinputArea;
	}

	public static JPasswordField getPWinputArea() {
		return PWinputArea;
	}

	private static ChangePWPanel ChangePWP;

	public static ChangePWPanel getChangePWP() {
		return ChangePWP;
	}

	public LoginPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("<html>ようこそ!<br>会員証はありますか?<html>"), 0, 0, 3, 1, 0, 1);

		arrangeComponents(new MakeLabel("ID   ", 17), 0, 1, 1, 1, 0, 0);

		IDinputArea = new JTextField(20);
		arrangeComponents(IDinputArea, 1, 1, 1, 1, 0, 0);

		arrangeComponents(new MakeLabel("PW   ", 17), 0, 2, 2, 1, 0, 0);

		PWinputArea = new JPasswordField(20);
		PWinputArea.setEchoChar('*');
		arrangeComponents(PWinputArea, 1, 2, 1, 1, 0, 0);

		MakeButton loginButton = new MakeButton("入館", new loginButtonAction(), "HomePanel");
		arrangeComponents(loginButton, 1, 3, 1, 1, 0, 0);

		MakeButton openAddUserWindowButton = new MakeButton("新しく作る", new openAddUserWindowButtonAction());
		arrangeComponents(openAddUserWindowButton, 2, 2, 1, 3, 0, 0);

		MakeLabel forgetPWHereLabel = new MakeLabel("<html>パスワードを忘れた方は<u>こちら<u><html>", 17);
		forgetPWHereLabel.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				Cursor c = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				Component p = (Component) e.getSource();
				p.setCursor(c);
			}

			public void mouseExited(MouseEvent e) {
				Cursor c = Cursor.getDefaultCursor();
				Component p = (Component) e.getSource();
				p.setCursor(c);
			}

			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					ChangePWP = new ChangePWPanel();
					LBWindow.getLibrarianContentPane().add(ChangePWP, BorderLayout.CENTER);
					LBWindow.getCardPanel().setVisible(false);

				}
			}

		});
		arrangeComponents(forgetPWHereLabel, 1, 4, 1, 1, 0, 0);

		arrangeComponents(new MakeTimerLabel(), 1, 5, 2, 1, 0, 0.01);

		arrangeComponents(makeWoman(), 4, 1, 1, 6, 0, 0);

	}

}

//新規ユーザー登録画面
class AddNewUserPanel extends LBPanel {
	private JTextFt newUserNameInputArea;
	private JTextFt newUserIDInputArea;
	private JTextFt newUserPWInputArea;
	private JTextFt newUserBirthdayInputArea;

	public JTextFt getNewUserNameInputArea() {
		return newUserNameInputArea;
	}

	public JTextFt getNewUserIDInputArea() {
		return newUserIDInputArea;
	}

	public JTextFt getNewUserPWInputArea() {
		return newUserPWInputArea;
	}

	public JTextFt getNewUserBirthdayInputArea() {
		return newUserBirthdayInputArea;
	}

	public void focusOK() {
		newUserNameInputArea.setFocusable(true);
		newUserIDInputArea.setFocusable(true);
		newUserPWInputArea.setFocusable(true);
		newUserBirthdayInputArea.setFocusable(true);
	}

	public AddNewUserPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("<html>初めまして、図書館のお姉さんです。"
				+ "<br>あなたのことを教えてくれるかしら<html>"), 0, 0, 2, 1, 0, 1);

		arrangeComponents(new MakeLabel("名前", 17), 0, 1, 1, 1, 0, 0.1);

		newUserNameInputArea = new JTextFt("20文字まで");
		newUserNameInputArea.setFocusable(false);
		newUserNameInputArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				focusOK();
			}

		});
		arrangeComponents(newUserNameInputArea, 1, 1, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("ID", 17), 0, 2, 1, 1, 0, 0.1);

		newUserIDInputArea = new JTextFt("半角英数字16文字まで");
		newUserIDInputArea.setFocusable(false);
		newUserIDInputArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				focusOK();
			}

		});
		arrangeComponents(newUserIDInputArea, 1, 2, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("パスワード", 17), 0, 3, 1, 1, 0, 0.1);

		newUserPWInputArea = new JTextFt("半角英数字16文字まで");
		newUserPWInputArea.setFocusable(false);
		newUserPWInputArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				focusOK();
			}

		});
		arrangeComponents(newUserPWInputArea, 1, 3, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("誕生日", 17), 0, 4, 1, 1, 0, 0.1);

		newUserBirthdayInputArea = new JTextFt("例：19990101");
		newUserBirthdayInputArea.setFocusable(false);
		newUserBirthdayInputArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				focusOK();
			}

		});
		arrangeComponents(newUserBirthdayInputArea, 1, 4, 1, 1, 0, 0.1);

		MakeButton addNewUserButton = new MakeButton("登録", new addNewUserButtonAction());
		arrangeComponents(addNewUserButton, 0, 5, 2, 1, 0, 0.5);

		MakeButton returnLoginPanelFromAddNewUserButton = new MakeButton("ログイン画面に戻る",
				new returnLoginPanelFromAddNewUserButtonAction());
		arrangeComponents(returnLoginPanelFromAddNewUserButton, 0, 6, 2, 1, 0, 0.5);

		arrangeComponents(makeWoman(), 2, 1, 1, 7, 0, 0);
	}
}

//パスワード変更画面
class ChangePWPanel extends LBPanel {
	private JTextField IDchangingPWInputArea;
	private JTextField PWAfterChangeInputArea;

	public JTextField getIDchangingPWInputArea() {
		return IDchangingPWInputArea;
	}

	public JTextField getPWAfterChangeInputArea() {
		return PWAfterChangeInputArea;
	}

	public ChangePWPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("<html>パスワードを忘れてしまったIDと、<br>"
				+ "変更後のパスワードを入力して下さい<html>"), 0, 0, 2, 1, 0, 1);

		arrangeComponents(new MakeLabel("ID", 17), 0, 1, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("新しいパスワード", 17), 0, 2, 1, 1, 0, 0.1);

		IDchangingPWInputArea = new JTextField(20);
		arrangeComponents(IDchangingPWInputArea, 1, 1, 1, 1, 0, 0.1);

		PWAfterChangeInputArea = new JTextField(20);
		arrangeComponents(PWAfterChangeInputArea, 1, 2, 1, 1, 0, 0.1);

		MakeButton PWChangeButton = new MakeButton("変更", new PWChangeButtonAction());
		arrangeComponents(PWChangeButton, 0, 3, 2, 1, 0, 0.5);

		MakeButton returnLoginPanelFromPWChangeButton = new MakeButton("ログイン画面に戻る",
				new returnLoginPanelFromPWChangeButtonAction());
		arrangeComponents(returnLoginPanelFromPWChangeButton, 0, 4, 2, 1, 0, 0.5);

		arrangeComponents(makeWoman(), 2, 1, 1, 5, 0, 0);

	}
}

//ホーム画面
class HomePanel extends LBPanel {
	public HomePanel() {
		prepareGridBag();

		//前回のログインからの日数に応じたお姉さんコメント
		String specialMessage;
		String message;
		long loginDateTo = loginButtonAction.getLoginDateTo().getTime();
		long loginDateFrom = loginButtonAction.getLoginDateFrom().getTime();
		long dayDiff = (loginDateTo - loginDateFrom) / (1000 * 60 * 60 * 24);
		System.out.println("経過日数は" + dayDiff);
		if (dayDiff >= 31) {
			specialMessage = "生きてたかぁ！良かった良かった";
		} else if (dayDiff >= 10) {
			specialMessage = "お久しぶりですね、お元気でしたか？";
		} else if (dayDiff >= 4) {
			specialMessage = "今日は何するんだ？";
		} else if (dayDiff == 1) {
			specialMessage = "今日も来てくれたんですね";
		} else if (dayDiff == 0) {
			specialMessage = "何か忘れものですか？";
		} else {
			specialMessage = "これからよろしくお願いしますね";
		}
		message = "<html>こんにちは、" + loginButtonAction.getLoginUserName() + "さん"
				+ "<br>" + specialMessage + "<html>";
		arrangeComponents(new MakeLabel(message), 0, 0, 2, 1, 1, 1);

		MakeButton openFindBookPanelButton = new MakeButton("本を探す", new openFindBookPanelButtonAction(),
				"FindBookPanel");
		arrangeComponents(openFindBookPanelButton, 0, 1, 1, 1, 0.1, 0.1);

		MakeButton openAddBookPanelButton = new MakeButton("新しい本を追加する", new openAddBookPanelButtonAction(),
				"AddBookPanel");
		arrangeComponents(openAddBookPanelButton, 0, 2, 1, 1, 0.1, 0.1);

		MakeButton openPlayWithWomanPanelButton = new MakeButton("お姉さんと遊ぶ", new openPlayWithWomanPanelButtonAction());
		arrangeComponents(openPlayWithWomanPanelButton, 0, 3, 1, 1, 0.1, 0.1);

		MakeButton openReturnBookPanelButton = new MakeButton("本を返却する", new openReturnBookPanelButtonAction(),
				"ReturnBookPanel");
		arrangeComponents(openReturnBookPanelButton, 1, 1, 1, 1, 0.1, 0.1);

		MakeButton openWomanRecommendationPanelButton = new MakeButton("お姉さんのおすすめ",
				new openWomanRecommendationPanelButtonAction(), "WomanRecommendationPanel");
		arrangeComponents(openWomanRecommendationPanelButton, 1, 2, 1, 1, 0.1, 0.1);

		MakeButton openWomanRoomPanelButton = new MakeButton("お姉さんの部屋", new openWomanRoomPanelButtonAction());
		arrangeComponents(openWomanRoomPanelButton, 1, 3, 1, 1, 0.1, 0.1);

		MakeButton returnLoginPanelFromHomeButton = new MakeButton("退館",
				new returnLoginPanelFromHomeButtonAction(), "LoginPanel");
		arrangeComponents(returnLoginPanelFromHomeButton, 1, 4, 1, 1, 0.1, 0.1);

		Component womanLabel = makeWoman();
		arrangeComponents(womanLabel, 2, 1, 1, 5, 0, 0);
		womanLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				Cursor c = Cursor.getDefaultCursor();
				Component p = (Component) e.getSource();
				p.setCursor(c);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Cursor c = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				Component p = (Component) e.getSource();
				p.setCursor(c);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					new WomanJump();
				}
			}
		});

	}
}

//本追加画面
class AddBookPanel extends LBPanel {
	private static JTextField addBookTitleInputArea;
	private static JTextField addBookAuthorInputArea;
	private static JTextField addBookYearOfIssueInputArea;
	private static JComboBox addBookGenreInputArea;
	private static String[] genreList;

	public static String[] getGenreList() {
		return genreList;
	}

	public static JTextField getAddBookTitleInputArea() {
		return addBookTitleInputArea;
	}

	public static JTextField getAddBookAuthorInputArea() {
		return addBookAuthorInputArea;
	}

	public static JTextField getAddBookYearOfIssueInputArea() {
		return addBookYearOfIssueInputArea;
	}

	public static JComboBox getAddBookGenreInputArea() {
		return addBookGenreInputArea;
	}

	public AddBookPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("<html>新しい本があるのね。"
				+ "<br>どんな本ですか？<html>"), 0, 0, 2, 1, 1, 1);

		String[] labelName = { "タイトル", "著者名", "発行年", "ジャンル" };
		int labelFontSize = 17;
		int i = 1;
		for (String str : labelName) {
			arrangeComponents(new MakeLabel(str, labelFontSize), 0, i, 1, 1, 0, 0.1);
			i++;
		}
		addBookTitleInputArea = new JTextField(30);
		addBookAuthorInputArea = new JTextField(30);
		addBookYearOfIssueInputArea = new JTextField(30);

		//ジャンル全てをリストに格納
		DBConnection con = new DBConnection();
		con.dbConnection(con.getAdministrator_ID(), con.getAdministrator_PW());
		String getGenreSQL = "SELECT book_genre FROM librarian.bookshelf";
		con.sendSQLtoDB(getGenreSQL);
		ResultSet rs;

		ArrayList<String> preGenreList = new ArrayList<>();
		preGenreList.add("");
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				preGenreList.add(rs.getString(1));
			}
			//重複しているジャンルを除去
			ArrayList<String> preGenreList2 = new ArrayList<>(new HashSet<>(preGenreList));
			//精製されたリストを配列に変換
			genreList = preGenreList2.toArray(new String[preGenreList2.size()]);

			rs.close();
			con.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		addBookGenreInputArea = new JComboBox(genreList);
		addBookGenreInputArea.setPreferredSize(new Dimension(350, 28));
		addBookGenreInputArea.setEditable(true);

		Component[] addBookComp = { addBookTitleInputArea, addBookAuthorInputArea,
				addBookYearOfIssueInputArea, addBookGenreInputArea };
		int j = 1;
		for (Component comp : addBookComp) {
			arrangeComponents(comp, 1, j, 1, 1, 0, 0.1);
			j++;
		}

		MakeButton addBookToDBButton = new MakeButton("追加する", new addBookToDBButtonAction());
		arrangeComponents(addBookToDBButton, 0, 5, 2, 1, 0, 0.5);

		arrangeComponents(returnHomePanalButton, 0, 6, 2, 1, 0, 0.5);

		arrangeComponents(makeWoman(), 2, 1, 1, 7, 0, 0);

	}
}

//本返却画面
class ReturnBookPanel extends LBPanel {
	private static JTable borrowedBookDisplayTable;

	public static JTable getBorrowedBookDisplayTable() {
		return borrowedBookDisplayTable;
	}

	private JScrollPane borrowedBookDisplayTableScrollPane;

	public ReturnBookPanel() {
		prepareGridBag();
		//読んだ冊数に応じてお姉さんのメッセージ
		String message = null;
		//読んだ冊数の取得
		DBConnection con = new DBConnection();
		String selectSqlNumOfRead = "select number_of_read from librarian.user_list "
				+ "where user_id='" + con.getLoginUser_ID() + "'";
		con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());
		con.sendSQLtoDB(selectSqlNumOfRead);
		ResultSet rs;
		int numOfRead = 0;
		try {
			rs = con.getPreStatement().executeQuery();
			while (rs.next()) {
				numOfRead = rs.getInt(1);
			}
			if (numOfRead >= 1) {
				message = "<html>" + numOfRead + "冊読みました<html>";
				if (numOfRead >= 10) {
					message += "<br>すごいですね！<html>";
				} else if (numOfRead >= 5) {
					message = "<br>読書はいいことです<html>";
				} else if (numOfRead >= 3) {
					message = "<br>たくさん読んでね！<html>";
				} else if (numOfRead >= 1) {
					message = "<br>本を読む人は好きですYO<html>";
				}
			} else if (numOfRead == 0) {
				message = "<html>本を読め<br>おすすめ見ろ<html>";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		arrangeComponents(new MakeLabel(message), 0, 0, 2, 1, 1, 1);

		borrowedBookDisplayTable = new JTable();
		borrowedBookDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "借りた日", "返した日", "レビュー", "" }) {
			public boolean isCellEditable(int row, int column) {
				if (column == 4) {
					return true;
				} else {
					return false;
				}
			}
		});
		borrowedBookDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowedBookDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		borrowedBookDisplayTable.getColumn("タイトル").setPreferredWidth(750);
		borrowedBookDisplayTable.getColumn("レビュー").setPreferredWidth(100);
		borrowedBookDisplayTable.getColumn("").setPreferredWidth(0);
		borrowedBookDisplayTable.setRowHeight(50);
		borrowedBookDisplayTable.setAutoCreateRowSorter(true);
		JTableHeader tableHeader = borrowedBookDisplayTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		TableColumnModel tcm = borrowedBookDisplayTable.getColumnModel();
		//文字列を折り返す
		TableColumn col1 = tcm.getColumn(1);
		col1.setCellRenderer(new TextAreaCellRenderer());

		borrowedBookDisplayTableScrollPane = new JScrollPane();
		borrowedBookDisplayTableScrollPane.setViewportView(borrowedBookDisplayTable);
		borrowedBookDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));
		arrangeComponents(borrowedBookDisplayTableScrollPane, 0, 1, 2, 1, 0, 1);

		MakeButton returnBookButton = new MakeButton("返却する", new returnBookButtonAction());
		arrangeComponents(returnBookButton, 1, 2, 1, 1, 1, 1);

		arrangeComponents(returnHomePanalButton, 1, 3, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 2, 1, 1, 3, 0, 0);

	}
}

//本の検索画面
class FindBookPanel extends LBPanel {
	private static JTextField freeWordSearchInputArea;

	public static JTextField getFreeWordSearchInputArea() {
		return freeWordSearchInputArea;
	}

	private static JTable bookListDisplayTable;
	private JScrollPane bookListDisplayTableScrollPane;

	public FindBookPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("借りたい本を選んで"
				+ "借りるボタンを押してくださいね"), 0, 0, 3, 1, 1, 1);

		freeWordSearchInputArea = new JTextField(30);
		arrangeComponents(freeWordSearchInputArea, 0, 1, 1, 1, 1, 0);

		MakeButton freeWordSearchButton = new MakeButton("検索", new freeWordSearchButtonAction());
		arrangeComponents(freeWordSearchButton, 0, 2, 1, 1, 0, 0);

		MakeButton allBookListDisplayButton = new MakeButton("全ての本を表示", new allBookListDisplayButtonAction());
		arrangeComponents(allBookListDisplayButton, 1, 1, 1, 2, 0, 0);

		bookListDisplayTable = new JTable();
		bookListDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "登録日", "みんなの評価", "レビュー" }) {
			//セルを選択不可にする
			public boolean isCellEditable(int row, int column) {
				if (column == 7) {
					return true;
				} else {
					return false;
				}
			}
		});
		bookListDisplayTable.setRowHeight(50);
		bookListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookListDisplayTable.getColumn("No").setPreferredWidth(50);
		bookListDisplayTable.getColumn("タイトル").setPreferredWidth(500);
		bookListDisplayTable.getColumn("著者名").setPreferredWidth(100);
		bookListDisplayTable.getColumn("ジャンル").setPreferredWidth(100);
		bookListDisplayTable.getColumn("みんなの評価").setPreferredWidth(100);
		bookListDisplayTable.getColumn("レビュー").setPreferredWidth(100);
		bookListDisplayTable.setAutoCreateRowSorter(true);
		bookListDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader tableHeader = bookListDisplayTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		bookListDisplayTableScrollPane = new JScrollPane();
		bookListDisplayTableScrollPane.setViewportView(bookListDisplayTable);
		bookListDisplayTableScrollPane.setPreferredSize(new Dimension(1100, 200));
		arrangeComponents(bookListDisplayTableScrollPane, 0, 3, 2, 1, 0, 1);

		arrangeComponents(borrowBookButton, 0, 4, 2, 1, 1, 1);

		arrangeComponents(returnHomePanalButton, 0, 5, 2, 1, 1, 1);

		arrangeComponents(makeWoman(), 3, 1, 1, 6, 0, 0);

	}

	public static JTable getBookListDisplayTable() {
		return bookListDisplayTable;
	}
}

//お姉さんのおすすめ画面
class WomanRecommendationPanel extends LBPanel {
	private static JTable RecommendationDisplayTable;

	public static JTable getRecommendationDisplayTable() {
		return RecommendationDisplayTable;
	}

	private JScrollPane RecommendationDisplayTableScrollPane;

	//おすすめの表示状態　評価高い：0　似た本：１
	private static int recomNum;

	public WomanRecommendationPanel() {
		prepareGridBag();

		String message = "<html>" + loginButtonAction.getLoginUserName() + "さん" +
				"<br>こんな本はいかがですか?<html>";

		arrangeComponents(new MakeLabel(message), 0, 0, 1, 1, 1, 1);

		MakeButton otherRecommendationDisplayButton = new MakeButton("他のおすすめ",
				new otherRecommendationDisplayButtonAction());
		arrangeComponents(otherRecommendationDisplayButton, 0, 1, 1, 1, 0, 1);

		RecommendationDisplayTable = new JTable();
		RecommendationDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "みんなの評価", "レビュー" }) {
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				} else {
					return false;
				}
			}
		});
		RecommendationDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RecommendationDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		RecommendationDisplayTable.setRowHeight(50);
		RecommendationDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RecommendationDisplayTable.getColumn("No").setPreferredWidth(50);
		RecommendationDisplayTable.getColumn("タイトル").setPreferredWidth(500);
		RecommendationDisplayTable.getColumn("著者名").setPreferredWidth(100);
		RecommendationDisplayTable.getColumn("ジャンル").setPreferredWidth(100);
		RecommendationDisplayTable.getColumn("みんなの評価").setPreferredWidth(100);
		RecommendationDisplayTable.getColumn("レビュー").setPreferredWidth(100);
		RecommendationDisplayTable.setAutoCreateRowSorter(true);
		JTableHeader tableHeader = RecommendationDisplayTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		RecommendationDisplayTableScrollPane = new JScrollPane();
		RecommendationDisplayTableScrollPane.setViewportView(RecommendationDisplayTable);
		RecommendationDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));

		arrangeComponents(RecommendationDisplayTableScrollPane, 0, 2, 1, 1, 0, 1);

		arrangeComponents(borrowBookButton, 0, 3, 1, 1, 0, 1);

		arrangeComponents(returnHomePanalButton, 0, 4, 1, 1, 0, 1);

		arrangeComponents(makeWoman(), 3, 1, 1, 5, 0, 0);

	}

	public static int getRecomNum() {
		return recomNum;
	}

	public static void setRecomNum(int recomNum) {
		WomanRecommendationPanel.recomNum = recomNum;
	}
}

//レビューを見る画面
class DisplayReviewPanel extends LBPanel {
	private static JTable ReviewDisplayTable;

	public static JTable getReviewDisplayTable() {
		return ReviewDisplayTable;
	}

	private JScrollPane ReviewDisplayTableScrollPane;

	public DisplayReviewPanel() {
		prepareGridBag();

		String message = "<html>『" + OpenDisplayReviewPanelButton.getReviewBookTitle() + "』" +
				"は<br>こんな感想を頂いてます<html>";

		arrangeComponents(new MakeLabel(message), 0, 0, 1, 1, 1, 1);

		ReviewDisplayTable = new JTable();
		ReviewDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "評価", "感想" }) {
			//セルを選択不可にする
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		ReviewDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ReviewDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ReviewDisplayTable.setRowHeight(100);
		ReviewDisplayTable.getColumn("評価").setPreferredWidth(100);
		ReviewDisplayTable.getColumn("感想").setPreferredWidth(984);
		ReviewDisplayTable.setAutoCreateRowSorter(true);

		JTableHeader tableHeader = ReviewDisplayTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		ReviewDisplayTableScrollPane = new JScrollPane();
		ReviewDisplayTableScrollPane.setViewportView(ReviewDisplayTable);
		ReviewDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 400));

		arrangeComponents(ReviewDisplayTableScrollPane, 0, 1, 1, 1, 0, 1);

		MakeButton openWriteReviewPanelButton = new MakeButton("レビューを書く", new openWriteReviewPanelButtonAction(),
				"WriteReviewPanel");
		arrangeComponents(openWriteReviewPanelButton, 0, 2, 1, 1, 1, 1);

		MakeButton returnPreviousPanelButton = new MakeButton("前の画面に戻る", new returnPreviousPanelButtonAction());
		arrangeComponents(returnPreviousPanelButton, 0, 3, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 2, 1, 1, 4, 0, 0);

	}
}

//レビューを書く画面
//以前書いた場合は、それを表示させたい（まだ）
class WriteReviewPanel extends LBPanel implements MouseListener {
	private static JTextArea reviewInputArea;

	public static JTextArea getReviewInputArea() {
		return reviewInputArea;
	}

	private Component star0;
	private Component star1;
	private Component star2;
	private Component star3;
	private Component star4;
	private Component star5;

	private static int starRating;

	public static int getStarRating() {
		return starRating;
	}

	public static void setStarRating(int starRating) {
		WriteReviewPanel.starRating = starRating;
	}

	public WriteReviewPanel() {
		prepareGridBag();

		String message = "<html>『" + OpenDisplayReviewPanelButton.getReviewBookTitle() + "』"
				+ "<br>感想を聞かせてくださるのね<br>楽しみにしていますね<html>";
		arrangeComponents(new MakeLabel(message), 0, 0, 1, 1, 1, 1);

		//星ラベルをあらかじめ作成し、マウスリスナーをセット
		star0 = makeStar0();
		star1 = makeStar1();
		star2 = makeStar2();
		star3 = makeStar3();
		star4 = makeStar4();
		star5 = makeStar5();
		star0.addMouseListener(this);
		star1.addMouseListener(this);
		star2.addMouseListener(this);
		star3.addMouseListener(this);
		star4.addMouseListener(this);
		star5.addMouseListener(this);
		//全星ラベルを重ねて配置し、星０ラベル以外不可視にする
		arrangeComponents(star0, 0, 1, 1, 1, 1, 1);
		arrangeComponents(star1, 0, 1, 1, 1, 1, 1);
		arrangeComponents(star2, 0, 1, 1, 1, 1, 1);
		arrangeComponents(star3, 0, 1, 1, 1, 1, 1);
		arrangeComponents(star4, 0, 1, 1, 1, 1, 1);
		arrangeComponents(star5, 0, 1, 1, 1, 1, 1);
		star1.setVisible(false);
		star2.setVisible(false);
		star3.setVisible(false);
		star4.setVisible(false);
		star5.setVisible(false);

		reviewInputArea = new JTextArea(6, 80);
		reviewInputArea.setLineWrap(true);
		arrangeComponents(reviewInputArea, 0, 2, 1, 1, 0, 0);

		MakeButton postReviewButton = new MakeButton("レビューを投稿する", new postReviewButtonAction());
		arrangeComponents(postReviewButton, 0, 3, 1, 1, 1, 1);

		MakeButton returnDisplayReviewPanelButton = new MakeButton("戻る", new returnDisplayReviewPanelButtonAction());
		arrangeComponents(returnDisplayReviewPanelButton, 0, 4, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 1, 1, 1, 5, 0, 0);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		System.out.println("x=" + point.x);
		System.out.println("y=" + point.y);
		int x = point.x;
		int y = point.y;
		int Ymin = 53;
		int Ymax = 119;

		if (y > Ymin && y < Ymax) {

			if (x > 358 && x <= 433) {
				star0.setVisible(false);
				star1.setVisible(true);
				star2.setVisible(false);
				star3.setVisible(false);
				star4.setVisible(false);
				star5.setVisible(false);

				starRating = 1;

			} else if (x > 433 && x <= 508) {
				star0.setVisible(false);
				star1.setVisible(false);
				star2.setVisible(true);
				star3.setVisible(false);
				star4.setVisible(false);
				star5.setVisible(false);

				starRating = 2;

			} else if (x > 508 && x <= 586) {
				star0.setVisible(false);
				star1.setVisible(false);
				star2.setVisible(false);
				star3.setVisible(true);
				star4.setVisible(false);
				star5.setVisible(false);

				starRating = 3;

			} else if (x > 586 && x <= 663) {
				star0.setVisible(false);
				star1.setVisible(false);
				star2.setVisible(false);
				star3.setVisible(false);
				star4.setVisible(true);
				star5.setVisible(false);

				starRating = 4;

			} else if (x > 663 && x <= 740) {
				star0.setVisible(false);
				star1.setVisible(false);
				star2.setVisible(false);
				star3.setVisible(false);
				star4.setVisible(false);
				star5.setVisible(true);

				starRating = 5;
			}
		} else {
			star0.setVisible(true);
			star1.setVisible(false);
			star2.setVisible(false);
			star3.setVisible(false);
			star4.setVisible(false);
			star5.setVisible(false);

			starRating = 0;
		}

		System.out.println("星" + starRating + "つ");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}

//お姉さんの部屋（管理者用画面）
class WomanRoomPanel extends LBPanel {
	private static JTable allUserListDisplayTable;
	private JScrollPane allUserListDisplayTableScrollPane;
	private static JTable allBookListDisplayTable;
	private JScrollPane allBookListDisplayTableScrollPane;

	public static JTable getAllBookListDisplayTable() {
		return allBookListDisplayTable;
	}

	public static JTable getAllUserListDisplayTable() {
		return allUserListDisplayTable;
	}

	public WomanRoomPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("ユーザー一覧", 17), 0, 0, 1, 1, 0, 0);

		allUserListDisplayTable = new JTable();
		allUserListDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "ユーザーネーム", "ユーザーID", "ユーザーパスワード", "ユーザー登録日", "最終ログイン日" }));
		allUserListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		allUserListDisplayTableScrollPane = new JScrollPane();
		allUserListDisplayTableScrollPane.setViewportView(allUserListDisplayTable);
		allUserListDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));
		arrangeComponents(allUserListDisplayTableScrollPane, 0, 1, 3, 1, 0, 0);

		MakeButton deleteUserButton = new MakeButton("ユーザー削除", new deleteUserButtonAction());
		arrangeComponents(deleteUserButton, 0, 2, 1, 1, 0, 0);

		MakeButton changeUserNameButton = new MakeButton("ユーザーネーム変更", new changeUserNameButtonAction());
		arrangeComponents(changeUserNameButton, 1, 2, 1, 1, 0, 0);

		MakeButton changeUserPWButton = new MakeButton("ユーザーパスワード変更", new changeUserPWButtonAction());
		arrangeComponents(changeUserPWButton, 2, 2, 1, 1, 0, 0);

		arrangeComponents(new MakeLabel("書籍一覧", 17), 0, 3, 1, 1, 0, 0);

		allBookListDisplayTable = new JTable();
		allBookListDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "本の追加日" }));
		allBookListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader tableHeader = allBookListDisplayTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		allBookListDisplayTableScrollPane = new JScrollPane();
		allBookListDisplayTableScrollPane.setViewportView(allBookListDisplayTable);
		allBookListDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));
		arrangeComponents(allBookListDisplayTableScrollPane, 0, 4, 3, 1, 0, 0);

		MakeButton updateBookInfomationButton = new MakeButton("本の情報更新", new updateBookInfomationButtonAction());
		arrangeComponents(updateBookInfomationButton, 0, 5, 1, 1, 0, 0);

		MakeButton deleteBookButton = new MakeButton("本を削除", new deleteBookButtonAction());
		arrangeComponents(deleteBookButton, 1, 5, 1, 1, 0, 0);

		MakeButton returnHomePanalFromWomanRoomButton = new MakeButton("前の画面に戻る",
				new returnHomePanalFromWomanRoomButtonAction());
		arrangeComponents(returnHomePanalFromWomanRoomButton, 2, 5, 1, 1, 0, 0);
	}
}
