package test;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * JTableにボタンを表示、クリックで行を削除するサンプル
 * JTableにボタンを表示する方法
 * ・getTableCellRendererComponentでJButtonを返すセルレンダラーを作りましょう。
 * ・ただし、このボタンはクリックできません。
 * 
 * ボタンをクリックする方法
 * ・DefaultCellEditorを継承して、JButtonを受け取るコンストラクタを追加します。
 * ・コンストラクタの内容は、DefaultCellEditorのコンストラクタを参考に実装しました。
 * ・DefaultCellEditorには、デフォルトコンストラクタがないせいか、継承して新しいコンストラクタを作ってもスーパークラスのコンストラクタを呼ばないとエラーが出ました。
 * ・仕方ないのでスーパークラスのコンストラクタを呼んでますが、酷いソースになってしまいました。
 */
public class JTableOnButton extends JFrame {
	JTable tbl;
	DefaultTableModel model;

	public static void main(String[] args) {
		new JTableOnButton();
	}

	public JTableOnButton() {
		setTitle("JTableにボタンを表示、ボタン押したら行削除");
		setBounds(200, 100, 400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// C:\のファイル一覧を取得
		File path = new File("C:\\");
		File[] files = path.listFiles();

		// モデルを生成
		model = new DefaultTableModel();
		// カラム名ほ設定
		model.setColumnIdentifiers(new String[] { "ファイル名", "ボタン" });

		// ファイル名、サイズ、日付の分だけ全部Fileの配列にして行を追加
		for (File file : files) {
			model.addRow(new Object[] { file, file });
		}

		// テーブルモデルからJTableを作成
		tbl = new JTable(model);

		// それぞれのカラムにレンダラーを設定
		tbl.getColumnModel().getColumn(0)
				.setCellRenderer(new FileNameTableCellRenderer());
		tbl.getColumnModel().getColumn(1)
				.setCellRenderer(new ButtonCellRenderer());
		// ボタン用のセルエディターを設定
		// レンダラーで表示するボタンはダミーで、ボタンのクリックイベントはセルエディターの方で受け取ります。
		tbl.getColumnModel().getColumn(1)
				.setCellEditor(new ButtonCellEditor(new JButton()));
		// JTableをフレームに追加
		add(new JScrollPane(tbl));

		setVisible(true);
	}

	// ファイル名セルレンダラー
	class FileNameTableCellRenderer extends DefaultTableCellRenderer {
		FileSystemView fs = FileSystemView.getFileSystemView();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);

			if (value instanceof File) {
				File f = (File) value;
				// ファイル名とアイコン
				setText(fs.getSystemDisplayName(f));
				setIcon(fs.getSystemIcon(f));
			}

			return this;
		}
	}

	// ボタンセルレンダラー
	class ButtonCellRenderer extends DefaultTableCellRenderer {
		FileSystemView fs = FileSystemView.getFileSystemView();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (value instanceof File) {
				File f = (File) value;
				return new JButton(fs.getSystemDisplayName(f));
			}
			return new JButton(value.toString());
		}
	}

	// ボタンセルエディター
	class ButtonCellEditor extends DefaultCellEditor {
		FileSystemView fs = FileSystemView.getFileSystemView();
		JButton button2;
		File f;

		public ButtonCellEditor(JButton button) {
			super(new JTextField());
			editorComponent = button;
			this.button2 = button;
			this.clickCountToStart = 1;
			delegate = new EditorDelegate() {
				public void setValue(Object value) {
					if (value instanceof File) {
						f = (File) value;
						button2.setText(fs.getSystemDisplayName(f));
					} else {
						button2.setText((value != null) ? value.toString() : "");
					}
				}

				public Object getCellEditorValue() {
					return button2.getText();
				}

				// ボタンを押したら行を削除
				@Override
				public void actionPerformed(ActionEvent e) {
					super.actionPerformed(e);

					Vector<Vector<Object>> v = model.getDataVector();
					for (int i = 0; i < v.size(); i++) {
						Vector<Object> v2 = v.get(i);
						if (v2.contains(f)) {
							model.removeRow(i);
							break;
						}
					}
				}
			};
			button.addActionListener(delegate);
		}

	}
}
