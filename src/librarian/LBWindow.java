package librarian;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LBWindow extends JFrame {
	 static JPanel cardPanel;
	 static CardLayout layout;
	 static Container contentPane;

	 private static AddBookPanel addBookP;

public static void setAddBookP(AddBookPanel addBookP) {
		LBWindow.addBookP = addBookP;
	}

public static AddBookPanel getAddBookP() {
		return addBookP;
	}

	//テスト用
	public LBWindow(JPanel panel) {
		 contentPane = getContentPane();
		contentPane.add(panel, BorderLayout.CENTER);
		makeWindow("TEST");
	}

	public LBWindow(String _windowName) {
		cardPanel=new JPanel();
		layout=new CardLayout();
		cardPanel.setLayout(layout);

		addBookP=new AddBookPanel();

		cardPanel.add(new LoginPanel(), "LoginPanel");

		cardPanel.add(addBookP,"AddBookPanel");


		 contentPane = getContentPane();
		contentPane.add(cardPanel, BorderLayout.CENTER);
		makeWindow(_windowName);
	}


	public static void main(String[] args) {
		new LBWindow("ようこそ！");
	}

	public void makeWindow(String _windowName) {
		setTitle(_windowName);
		final int FRAME_WIDTH = 1400;
		final int FRAME_HEIGHT = 700;
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

}

class JTextFt extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;
	String helpmsg;
	String bakstr="";
	JTextFt(String msg){
		helpmsg=msg;
		addFocusListener(this);
		drawmsg();
	}
	void drawmsg() {
		setForeground(Color.LIGHT_GRAY);
		setText(helpmsg);
	}
	@Override
	public void focusGained(FocusEvent arg0) {
		setForeground(Color.BLACK);
		setText(bakstr);
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		bakstr=getText();
		if (bakstr.equals("")) {
			drawmsg();
		}
	}
}
