package librarian;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

//ボタンを作るためのクラス
public class MakeButton extends JButton {
	final String FONT_NAME = "HGP創英角ﾎﾟｯﾌﾟ体";
	final int FONT_STYLE = Font.BOLD;
	final int FONT_SIZE = 40;

	public MakeButton(String buttonName,ActionListener actionListner, String setCommand) {
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
class OpenDisplayReviewPanelButton extends AbstractCellEditor implements TableCellEditor,TableCellRenderer{

	private JButton button;

	public OpenDisplayReviewPanelButton(JTable table,DefaultTableModel model) {
		this.button=new JButton("レビューを見る");
		button.addActionListener(new ActionListener() {
			//ボタン押下時のアクション
			@Override
			public void actionPerformed(ActionEvent e) {
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
