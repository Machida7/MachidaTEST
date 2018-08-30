package librarian;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		gridBagCon.fill = GridBagConstraints.HORIZONTAL;
		gridBagLay.setConstraints(component, gridBagCon);

		this.add(component);
	}

	//表の選択されている行(横,y)を取得
	public int getselectedTableRow(JTable table) {
		int row = table.getSelectedRow();
		return row;
	}

	//表の選択されている列(縦,ｘ)を取得
	public int getselectedTableColumn(JTable table) {
		int column = table.getSelectedRow();
		return column;
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

		arrangeComponents(new MakeLabel("<html>ようこそ!<br>会員証はありますか?<html>"), 0, 0, 2, 1, 0, 1);

		IDinputArea = new JTextField(20);
		arrangeComponents(IDinputArea, 0, 1, 1, 1, 0, 0);

		PWinputArea = new JPasswordField(20);
		PWinputArea.setEchoChar('*');
		arrangeComponents(PWinputArea, 0, 2, 1, 1, 0, 0);

		MakeButton loginButton = new MakeButton("入館", new loginButtonAction(), "HomePanel");
		arrangeComponents(loginButton, 0, 3, 1, 1, 0, 0);

		MakeButton openAddUserWindowButton = new MakeButton("新しく作る", new openAddUserWindowButtonAction());
		arrangeComponents(openAddUserWindowButton, 1, 1, 1, 3, 0, 0);

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
					LBWindow.contentPane.add(ChangePWP, BorderLayout.CENTER);
					LBWindow.cardPanel.setVisible(false);

				}
			}

		});
		arrangeComponents(forgetPWHereLabel, 0, 4, 1, 1, 0, 0);

		arrangeComponents(makeWoman(), 3, 1, 1, 5, 0, 0);

	}

}

//新規ユーザー登録画面
class AddNewUserPanel extends LBPanel {
	private JTextField newUserNameInputArea;
	private JTextField newUserIDInputArea;
	private JTextField newUserPWInputArea;
	private JTextField newUserBirthdayInputArea;

	public JTextField getNewUserNameInputArea() {
		return newUserNameInputArea;
	}

	public JTextField getNewUserIDInputArea() {
		return newUserIDInputArea;
	}

	public JTextField getNewUserPWInputArea() {
		return newUserPWInputArea;
	}

	public JTextField getNewUserBirthdayInputArea() {
		return newUserBirthdayInputArea;
	}

	public AddNewUserPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("<html>初めまして、図書館のお姉さんです。"
				+ "<br>あなたのことを教えてくれるかしら<html>"), 0, 0, 2, 1, 0, 1);

		arrangeComponents(new MakeLabel("名前", 17), 0, 1, 1, 1, 0, 0.1);

		newUserNameInputArea = new JTextField(20);
		arrangeComponents(newUserNameInputArea, 1, 1, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("ID", 17), 0, 2, 1, 1, 0, 0.1);

		newUserIDInputArea = new JTextField(20);
		arrangeComponents(newUserIDInputArea, 1, 2, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("パスワード", 17), 0, 3, 1, 1, 0, 0.1);

		newUserPWInputArea = new JTextField(20);
		arrangeComponents(newUserPWInputArea, 1, 3, 1, 1, 0, 0.1);

		arrangeComponents(new MakeLabel("誕生日", 17), 0, 4, 1, 1, 0, 0.1);

		newUserBirthdayInputArea = new JTextField(20);
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
		} else if (dayDiff >= 1) {
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

		arrangeComponents(makeWoman(), 2, 1, 1, 5, 0, 0);

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
	private JTable borrowedBookDisplayTable;
	private JScrollPane borrowedBookDisplayTableScrollPane;

	public ReturnBookPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("Message"), 0, 0, 2, 1, 1, 1);

		borrowedBookDisplayTable = new JTable();
		borrowedBookDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "借りた日", "返した日", "タイトル", "著者名", "レビュー" }));
		borrowedBookDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowedBookDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
	private JTextField freeWordSearchInputArea;
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
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "登録日", "みんなの評価", "レビュー" }));
		bookListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		bookListDisplayTableScrollPane = new JScrollPane();
		bookListDisplayTableScrollPane.setViewportView(bookListDisplayTable);
		bookListDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));
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
	private JTable RecommendationDisplayTable;
	private JScrollPane RecommendationDisplayTableScrollPane;

	public WomanRecommendationPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("message"), 0, 0, 1, 1, 1, 1);

		MakeButton otherRecommendationDisplayButton = new MakeButton("他のおすすめ",
				new otherRecommendationDisplayButtonAction());
		arrangeComponents(otherRecommendationDisplayButton, 0, 1, 1, 1, 0, 1);

		RecommendationDisplayTable = new JTable();
		RecommendationDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "みんなの評価", "レビュー" }));
		RecommendationDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RecommendationDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		RecommendationDisplayTableScrollPane = new JScrollPane();
		RecommendationDisplayTableScrollPane.setViewportView(RecommendationDisplayTable);
		RecommendationDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));

		arrangeComponents(RecommendationDisplayTableScrollPane, 0, 2, 1, 1, 0, 1);

		arrangeComponents(borrowBookButton, 0, 3, 1, 1, 0, 1);

		arrangeComponents(returnHomePanalButton, 0, 4, 1, 1, 0, 1);

		arrangeComponents(makeWoman(), 3, 1, 1, 5, 0, 0);

	}
}

//レビューを見る画面
class DisplayReviewPanel extends LBPanel {
	private JTable ReviewDisplayTable;
	private JScrollPane ReviewDisplayTableScrollPane;

	public DisplayReviewPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("message"), 0, 0, 1, 1, 1, 1);

		ReviewDisplayTable = new JTable();
		ReviewDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "評価", "感想" }));
		ReviewDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ReviewDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		ReviewDisplayTableScrollPane = new JScrollPane();
		ReviewDisplayTableScrollPane.setViewportView(ReviewDisplayTable);
		ReviewDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));

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
class WriteReviewPanel extends LBPanel {
	private JTextArea reviewInputArea;

	public WriteReviewPanel() {
		prepareGridBag();

		arrangeComponents(new MakeLabel("message"), 0, 0, 1, 1, 1, 1);

		arrangeComponents(makeStar0(), 0, 1, 1, 1, 1, 1);

		reviewInputArea = new JTextArea(6, 80);
		reviewInputArea.setLineWrap(true);
		arrangeComponents(reviewInputArea, 0, 2, 1, 1, 0, 0);

		MakeButton postReviewButton = new MakeButton("レビューを投稿する", new postReviewButtonAction());
		arrangeComponents(postReviewButton, 0, 3, 1, 1, 1, 1);

		MakeButton returnDisplayReviewPanelButton = new MakeButton("やめる", new returnDisplayReviewPanelButtonAction());
		arrangeComponents(returnDisplayReviewPanelButton, 0, 4, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 1, 1, 1, 5, 0, 0);
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
