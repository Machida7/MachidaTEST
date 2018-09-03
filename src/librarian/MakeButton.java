package librarian;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

//ボタンを作るためのクラス
public class MakeButton extends JButton {
	final String FONT_NAME = "HGP創英角ﾎﾟｯﾌﾟ体";
	final int FONT_STYLE = Font.BOLD;
	final int FONT_SIZE = 40;

	public MakeButton(String buttonName, ActionListener actionListner, String setCommand) {
		super(buttonName);

		setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
		addActionListener(actionListner);
		setActionCommand(setCommand);

	}

	public MakeButton(String buttonName, ActionListener actionListner) {
		super(buttonName);

		setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
		addActionListener(actionListner);

	}
}

//レビューを見るボタンを表につける
class OpenDisplayReviewPanelButton extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private JButton button;
	private static DisplayReviewPanel displayReviewP;
	private static String reviewBookTitle;
	private static Object reviewBookID;

	public static Object getReviewBookID() {
		return reviewBookID;
	}

	public static void setReviewBookID(Object reviewBookID) {
		OpenDisplayReviewPanelButton.reviewBookID = reviewBookID;
	}

	public static String getReviewBookTitle() {
		return reviewBookTitle;
	}

	public static DisplayReviewPanel getDisplayReviewP() {
		return displayReviewP;
	}

	public static void setReviewBookTitle(String reviewBookTitle) {
		OpenDisplayReviewPanelButton.reviewBookTitle = reviewBookTitle;
	}

	public OpenDisplayReviewPanelButton(JTable table, DefaultTableModel model) {
		this.button = new JButton("<html>レビュー<br>見る<html>");
		String cmd = "DisplayReviewPanel";
		button.setActionCommand(cmd);
		button.addActionListener(new ActionListener() {
			//ボタン押下時のアクション
			@Override
			public void actionPerformed(ActionEvent e) {
				//選択された行の本のタイトルを取得
				int row = FindBookPanel.getBookListDisplayTable().getSelectedRow();
				String bookTitle = (String) FindBookPanel.getBookListDisplayTable().getValueAt(row, 1);
				System.out.println(bookTitle + "のレビューを見る");
				setReviewBookTitle(bookTitle);
				
				Object RBookID=FindBookPanel.getBookListDisplayTable().getValueAt(row, 0);
				setReviewBookID(RBookID);
				System.out.println("book_id="+RBookID+"のレビューを見る");

				displayReviewP = new DisplayReviewPanel();

				LBWindow.contentPane.add(displayReviewP, BorderLayout.CENTER);
				LBWindow.cardPanel.setVisible(false);
				DBConnection con = new DBConnection();
				con.dbConnection(con.getLoginUser_ID(), con.getLoginUser_PW());

				//表に星評価と感想を表示する
				con.selectReview();

				con.connectionClose();

				TableColumnModel tcm = DisplayReviewPanel.getReviewDisplayTable().getColumnModel();

				TableColumn col0 = tcm.getColumn(0);
				col0.setCellRenderer(new StarCellRenderer());

				TableColumn col1 = tcm.getColumn(1);
				col1.setCellRenderer(new TextAreaCellRenderer());

			}
		});

	}

	@Override
	public Object getCellEditorValue() {
		// TODO 自動生成されたメソッド・スタブ
		return button.getText();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO 自動生成されたメソッド・スタブ
		return button;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO 自動生成されたメソッド・スタブ
		return button;
	}

}

//星評価において、数字を星に変換する
class StarCellRenderer extends JTextArea implements TableCellRenderer {
	public StarCellRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value.equals("1")) {
			setText("★☆☆☆☆");
		} else if (value.equals("2")) {
			setText("★★☆☆☆");
		} else if (value.equals("3")) {
			setText("★★★☆☆");
		} else if (value.equals("4")) {
			setText("★★★★☆");
		} else if (value.equals("5")) {
			setText("★★★★★");
		}else {
			setText((String) value);
		}

		return this;
	}

}

//レビュー画面の感想のセルにおいて、文字を折り返すようにする
class TextAreaCellRenderer extends JTextArea implements TableCellRenderer {
	TextAreaCellRenderer() {
		setLineWrap(true);
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText((value == null) ? "" : value.toString());
		return this;
	}
}
