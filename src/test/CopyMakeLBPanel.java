package test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
import javax.swing.table.DefaultTableModel;



//パネルを作るクラス
public class CopyMakeLBPanel extends JPanel {
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

	protected CopyMakeButton returnLoginPanelButton = new CopyMakeButton("前の画面に戻る", new returnLoginPanelButtonAction());

	protected CopyMakeButton returnHomePanalButton = new CopyMakeButton("前の画面に戻る", new returnHomePanalButtonAction());

	protected CopyMakeButton borrowBookButton = new CopyMakeButton("借りる", new borrowBookButtonAction());

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
		gridBagLay.setConstraints(component, gridBagCon);

		this.add(component);
	}
}

//以下、各パネル

//ログイン画面
class LoginPanel extends CopyMakeLBPanel {
	private JTextField IDinputArea;
	private JPasswordField PWinputArea;

	public LoginPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("<html>ようこそ!<br>会員証はありますか?<html>"), 0, 0, 2, 1, 0, 1);

		IDinputArea = new JTextField(20);
		arrangeComponents(IDinputArea, 0, 1, 1, 1, 0, 0);

		PWinputArea = new JPasswordField(20);
		PWinputArea.setEchoChar('*');
		arrangeComponents(PWinputArea, 0, 2, 1, 1, 0, 0);

		CopyMakeButton loginButton = new CopyMakeButton("入館", new loginButtonAction());
		arrangeComponents(loginButton, 0, 3, 1, 1, 0, 0);

		CopyMakeButton openAddUserWindowButton = new CopyMakeButton("新しく作る", new openAddUserWindowButtonAction());
		arrangeComponents(openAddUserWindowButton, 1, 1, 1, 3, 0, 0);

		CopyMakeLabel forgetPWHereLabel = new CopyMakeLabel("パスワードを忘れた方はこちら", 17);
		arrangeComponents(forgetPWHereLabel, 0, 4, 1, 1, 0, 0);

		arrangeComponents(makeWoman(), 3, 1, 1, 5, 0, 0);

	}

}

//新規ユーザー登録画面
class AddNewUserPanel extends CopyMakeLBPanel {
	private JTextField newUserNameInputArea;
	private JTextField newUserIDInputArea;
	private JTextField newUserPWInputArea;
	private JTextField newUserBirthdayInputArea;

	public AddNewUserPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("<html>初めまして、図書館のお姉さんです。"
				+ "<br>あなたのことを教えてくれるかしら<html>"), 0, 0, 2, 1, 0, 1);

		arrangeComponents(new CopyMakeLabel("名前", 17), 0, 1, 1, 1, 0, 0.1);

		newUserNameInputArea = new JTextField(20);
		arrangeComponents(newUserNameInputArea, 1, 1, 1, 1, 0, 0.1);

		arrangeComponents(new CopyMakeLabel("ID", 17), 0, 2, 1, 1, 0, 0.1);

		newUserIDInputArea = new JTextField(20);
		arrangeComponents(newUserIDInputArea, 1, 2, 1, 1, 0, 0.1);

		arrangeComponents(new CopyMakeLabel("パスワード", 17), 0, 3, 1, 1, 0, 0.1);

		newUserPWInputArea = new JTextField(20);
		arrangeComponents(newUserPWInputArea, 1, 3, 1, 1, 0, 0.1);

		arrangeComponents(new CopyMakeLabel("誕生日", 17), 0, 4, 1, 1, 0, 0.1);

		newUserBirthdayInputArea = new JTextField(20);
		arrangeComponents(newUserBirthdayInputArea, 1, 4, 1, 1, 0, 0.1);

		CopyMakeButton addNewUserButton = new CopyMakeButton("登録", new addNewUserButtonAction());
		arrangeComponents(addNewUserButton, 0, 5, 2, 1, 0, 0.5);

		arrangeComponents(returnLoginPanelButton, 0, 6, 2, 1, 0, 0.5);

		arrangeComponents(makeWoman(), 2, 1, 1, 7, 0, 0);
	}
}

//パスワード変更画面
class ChangePWPanel extends CopyMakeLBPanel {
	private JTextField IDchangingPWInputArea;
	private JTextField PWAfterChangeInputArea;

	public ChangePWPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("<html>パスワードを忘れてしまったIDと、<br>"
				+ "変更後のパスワードを入力して下さい<html>"), 0, 0, 2, 1, 0, 1);

		arrangeComponents(new CopyMakeLabel("ID", 17), 0, 1, 1, 1, 0, 0.1);

		arrangeComponents(new CopyMakeLabel("新しいパスワード", 17), 0, 2, 1, 1, 0, 0.1);

		IDchangingPWInputArea = new JTextField(20);
		arrangeComponents(IDchangingPWInputArea, 1, 1, 1, 1, 0, 0.1);

		PWAfterChangeInputArea = new JTextField(20);
		arrangeComponents(PWAfterChangeInputArea, 1, 2, 1, 1, 0, 0.1);

		CopyMakeButton PWChangeButton = new CopyMakeButton("変更", new PWChangeButtonAction());
		arrangeComponents(PWChangeButton, 0, 3, 2, 1, 0, 0.5);

		arrangeComponents(returnLoginPanelButton, 0, 4, 2, 1, 0, 0.5);

		arrangeComponents(makeWoman(), 2, 1, 1, 5, 0, 0);

	}
}

//ホーム画面
class HomePanel extends CopyMakeLBPanel {
	public HomePanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("<html>こんにちは、○○さん"
				+ "<br>また来てくれたのですね。"
				+ "<br>今日は何をしますか？<html>"), 0, 0, 2, 1, 1, 1);

		CopyMakeButton openFindBookPanelButton = new CopyMakeButton("本を探す", new openFindBookPanelButtonAction());
		arrangeComponents(openFindBookPanelButton, 0, 1, 1, 1, 0.1, 0.1);

		CopyMakeButton openAddBookPanelButton = new CopyMakeButton("新しい本を追加する", new openAddBookPanelButtonAction());
		arrangeComponents(openAddBookPanelButton, 0, 2, 1, 1, 0.1, 0.1);

		CopyMakeButton openPlayWithWomanPanelButton = new CopyMakeButton("お姉さんと遊ぶ", new openPlayWithWomanPanelButtonAction());
		arrangeComponents(openPlayWithWomanPanelButton, 0, 3, 1, 1, 0.1, 0.1);

		CopyMakeButton openReturnBookPanelButton = new CopyMakeButton("本を返却する", new openReturnBookPanelButtonAction());
		arrangeComponents(openReturnBookPanelButton, 1, 1, 1, 1, 0.1, 0.1);

		CopyMakeButton openWomanRecommendationPanelButton = new CopyMakeButton("お姉さんのおすすめ",
				new openWomanRecommendationPanelButtonAction());
		arrangeComponents(openWomanRecommendationPanelButton, 1, 2, 1, 1, 0.1, 0.1);

		CopyMakeButton openWomanRoomPanelButton = new CopyMakeButton("お姉さんの部屋", new openWomanRoomPanelButtonAction());
		arrangeComponents(openWomanRoomPanelButton, 1, 3, 1, 1, 0.1, 0.1);

		arrangeComponents(returnLoginPanelButton, 1, 4, 1, 1, 0.1, 0.1);

		arrangeComponents(makeWoman(), 2, 1, 1, 5, 0, 0);

	}
}

//本追加画面
class AddBookPanel extends CopyMakeLBPanel {
	private JTextField addBookTitleInputArea;
	private JTextField addBookAuthorInputArea;
	private JTextField addBookYearOfIssueInputArea;
	private JComboBox addBookGenreInputArea;

	public AddBookPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("<html>新しい本があるのね。"
				+ "<br>どんな本ですか？<html>"), 0, 0, 2, 1, 1, 1);

		String[] labelName = { "タイトル", "著者名", "発行年", "ジャンル" };
		int labelFontSize = 17;
		int i = 1;
		for (String str : labelName) {
			arrangeComponents(new CopyMakeLabel(str, labelFontSize), 0, i, 1, 1, 0, 0.1);
			i++;
		}

		addBookTitleInputArea = new JTextField(30);

		addBookAuthorInputArea = new JTextField(30);

		addBookYearOfIssueInputArea = new JTextField(30);

		String[] genreList = { "", "Java", "SF" };
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

		CopyMakeButton addBookToDBButton = new CopyMakeButton("追加する", new addBookToDBButtonAction());
		arrangeComponents(addBookToDBButton, 0, 5, 2, 1, 0, 0.5);

		arrangeComponents(returnHomePanalButton, 0, 6, 2, 1, 0, 0.5);

		arrangeComponents(makeWoman(), 2, 1, 1, 7, 0, 0);

	}
}

//本返却画面
class ReturnBookPanel extends CopyMakeLBPanel {
	private JTable borrowedBookDisplayTable;
	private JScrollPane borrowedBookDisplayTableScrollPane;

	public ReturnBookPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("Message"), 0, 0, 2, 1, 1, 1);

		borrowedBookDisplayTable = new JTable();
		borrowedBookDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "借りた日", "返した日", "タイトル", "著者名", "レビュー" }));
		borrowedBookDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowedBookDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		borrowedBookDisplayTableScrollPane = new JScrollPane();
		borrowedBookDisplayTableScrollPane.setViewportView(borrowedBookDisplayTable);
		arrangeComponents(borrowedBookDisplayTableScrollPane, 1, 1, 2, 1, 0, 1);

		CopyMakeButton returnBookButton = new CopyMakeButton("返却する", new returnBookButtonAction());
		arrangeComponents(returnBookButton, 1, 2, 1, 1, 1, 1);

		arrangeComponents(returnHomePanalButton, 1, 3, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 2, 1, 1, 3, 0, 0);

	}
}

//本の検索画面
class FindBookPanel extends CopyMakeLBPanel {
	private JTextField freeWordSearchInputArea;
	private JTable bookListDisplayTable;
	private JScrollPane bookListDisplayTableScrollPane;

	public FindBookPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("借りたい本を選んで"
				+ "借りるボタンを押してくださいね"), 0, 0, 3, 1, 1, 1);

		freeWordSearchInputArea = new JTextField(30);
		arrangeComponents(freeWordSearchInputArea, 0, 1, 1, 1, 1, 0);

		CopyMakeButton freeWordSearchButton = new CopyMakeButton("検索", new freeWordSearchButtonAction());
		arrangeComponents(freeWordSearchButton, 0, 2, 1, 1, 0, 0);

		CopyMakeButton allBookListDisplayButton = new CopyMakeButton("全ての本を表示", new allBookListDisplayButtonAction());
		arrangeComponents(allBookListDisplayButton, 1, 1, 1, 2, 0, 0);

		bookListDisplayTable = new JTable();
		bookListDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "みんなの評価", "レビュー" }));
		bookListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookListDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		bookListDisplayTableScrollPane = new JScrollPane();
		bookListDisplayTableScrollPane.setViewportView(bookListDisplayTable);
		arrangeComponents(bookListDisplayTableScrollPane, 0, 3, 2, 1, 0, 0);

		arrangeComponents(borrowBookButton, 0, 4, 2, 1, 1, 1);

		arrangeComponents(returnHomePanalButton, 0, 5, 2, 1, 1, 1);

		arrangeComponents(makeWoman(), 3, 1, 1, 6, 0, 0);

	}
}

//お姉さんのおすすめ画面
class WomanRecommendationPanel extends CopyMakeLBPanel {
	private JTable RecommendationDisplayTable;
	private JScrollPane RecommendationDisplayTableScrollPane;

	public WomanRecommendationPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("message"), 0, 0, 1, 1, 1, 1);

		CopyMakeButton otherRecommendationDisplayButton = new CopyMakeButton("他のおすすめ",
				new otherRecommendationDisplayButtonAction());
		arrangeComponents(otherRecommendationDisplayButton, 0, 1, 1, 1, 0, 1);

		RecommendationDisplayTable = new JTable();
		RecommendationDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル", "著者名", "発行日", "ジャンル", "みんなの評価", "レビュー" }));
		RecommendationDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RecommendationDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		RecommendationDisplayTableScrollPane = new JScrollPane();
		RecommendationDisplayTableScrollPane.setViewportView(RecommendationDisplayTable);
		arrangeComponents(RecommendationDisplayTableScrollPane, 0, 2, 1, 1, 0, 0);

		arrangeComponents(borrowBookButton, 0, 3, 1, 1, 0, 1);

		arrangeComponents(returnHomePanalButton, 0, 4, 1, 1, 0, 1);

		arrangeComponents(makeWoman(), 3, 1, 1, 5, 0, 0);

	}
}

//レビューを見る画面
class DisplayReviewPanel extends CopyMakeLBPanel {
	private JTable ReviewDisplayTable;
	private JScrollPane ReviewDisplayTableScrollPane;

	public DisplayReviewPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("message"), 0, 0, 1, 1, 1, 1);

		ReviewDisplayTable = new JTable();
		ReviewDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "評価", "感想" }));
		ReviewDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ReviewDisplayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		ReviewDisplayTableScrollPane = new JScrollPane();
		ReviewDisplayTableScrollPane.setViewportView(ReviewDisplayTable);
		arrangeComponents(ReviewDisplayTableScrollPane, 0, 1, 1, 1, 0, 0);

		CopyMakeButton openWriteReviewPanelButton = new CopyMakeButton("レビューを書く", new openWriteReviewPanelButtonAction());
		arrangeComponents(openWriteReviewPanelButton, 0, 2, 1, 1, 1, 1);

		CopyMakeButton returnPreviousPanelButton = new CopyMakeButton("前の画面に戻る", new returnPreviousPanelButtonAction());
		arrangeComponents(returnPreviousPanelButton, 0, 3, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 2, 1, 1, 4, 0, 0);

	}
}

//レビューを書く画面
class WriteReviewPanel extends CopyMakeLBPanel {
	private JTextArea reviewInputArea;

	public WriteReviewPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("message"), 0, 0, 1, 1, 1, 1);

		arrangeComponents(makeStar0(), 0, 1, 1, 1, 1, 1);

		reviewInputArea=new JTextArea(6,80);
		reviewInputArea.setLineWrap(true);
		arrangeComponents(reviewInputArea, 0, 2, 1, 1, 0, 0);

		CopyMakeButton postReviewButton =new CopyMakeButton("レビューを投稿する", new postReviewButtonAction());
		arrangeComponents(postReviewButton, 0, 3, 1, 1, 1, 1);

		CopyMakeButton returnDisplayReviewPanelButton =new CopyMakeButton("やめる",new returnDisplayReviewPanelButtonAction());
		arrangeComponents(returnDisplayReviewPanelButton, 0, 4, 1, 1, 1, 1);

		arrangeComponents(makeWoman(), 1, 1, 1, 5, 0, 0);
	}
}

class WomanRoomPanel extends CopyMakeLBPanel{
	private JTable allUserListDisplayTable;
	private JScrollPane allUserListDisplayTableScrollPane;
	private JTable allBookListDisplayTable;
	private JScrollPane allBookListDisplayTableScrollPane;

	public WomanRoomPanel() {
		prepareGridBag();

		arrangeComponents(new CopyMakeLabel("ユーザー一覧", 17), 0, 0, 1, 1, 0, 0);

		allUserListDisplayTable = new JTable();
		allUserListDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "ユーザーネーム","ユーザーID","ユーザーパスワード" }));
		allUserListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		allUserListDisplayTableScrollPane = new JScrollPane();
		allUserListDisplayTableScrollPane.setViewportView(allUserListDisplayTable);
		allUserListDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));
		arrangeComponents(allUserListDisplayTableScrollPane, 0, 1, 3, 1, 0, 0);

		CopyMakeButton deleteUserButton=new CopyMakeButton("ユーザー削除", new deleteUserButtonAction());
		arrangeComponents(deleteUserButton, 0, 2, 1, 1, 0, 0);

		CopyMakeButton changeUserNameButton=new CopyMakeButton("ユーザーネーム変更", new changeUserNameButtonAction());
		arrangeComponents(changeUserNameButton, 1, 2, 1, 1, 0, 0);

		CopyMakeButton changeUserPWButton=new CopyMakeButton("ユーザーパスワード変更", new changeUserPWButtonAction());
		arrangeComponents(changeUserPWButton, 2, 2, 1, 1, 0, 0);



		arrangeComponents(new CopyMakeLabel("書籍一覧", 17), 0, 3, 1, 1, 0, 0);

		allBookListDisplayTable = new JTable();
		allBookListDisplayTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "No", "タイトル","著者名","発行日","ジャンル" }));
		allBookListDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		allBookListDisplayTableScrollPane = new JScrollPane();
		allBookListDisplayTableScrollPane.setViewportView(allBookListDisplayTable);
		allBookListDisplayTableScrollPane.setPreferredSize(new Dimension(1000, 200));
		arrangeComponents(allBookListDisplayTableScrollPane, 0, 4, 3, 1, 0, 0);

		CopyMakeButton updateBookInfomationButton=new CopyMakeButton("本の情報更新",new updateBookInfomationButtonAction());
		arrangeComponents(updateBookInfomationButton, 0, 5, 1, 1, 0, 0);

		CopyMakeButton deleteBookButton=new CopyMakeButton("本を削除",new deleteBookButtonAction());
		arrangeComponents(deleteBookButton, 1, 5, 1, 1, 0, 0);

		arrangeComponents(returnHomePanalButton, 2, 5, 1, 1, 0, 0);


	}
}
