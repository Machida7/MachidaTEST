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

public class RunLibrarian extends JFrame {
	private static JPanel cardPanel;
	private static CardLayout cardPanelLayout;
	private static Container librarianContentPane;

	private static AddBookPanel addBookP;

	public static void setAddBookP(AddBookPanel addBookP) {
		RunLibrarian.addBookP = addBookP;
	}

	public static AddBookPanel getAddBookP() {
		return addBookP;
	}



	public RunLibrarian(String _windowName) {
		cardPanel=new JPanel();
		cardPanelLayout=new CardLayout();
		getCardPanel().setLayout(getCardPanelLayout());

		addBookP = new AddBookPanel();

		getCardPanel().add(new LoginPanel(), "LoginPanel");

		getCardPanel().add(addBookP, "AddBookPanel");

		librarianContentPane=getContentPane();
		getLibrarianContentPane().add(getCardPanel(), BorderLayout.CENTER);



		makeWindow(_windowName);
	}

	public static void main(String[] args) {
		new RunLibrarian("ようこそ！");
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

	public static JPanel getCardPanel() {
		return cardPanel;
	}

	public static CardLayout getCardPanelLayout() {
		return cardPanelLayout;
	}



	public static Container getLibrarianContentPane() {
		return librarianContentPane;
	}




}

class JTextFt extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;
	String helpmsg;
	String bakstr = "";

	JTextFt(String msg) {
		helpmsg = msg;
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
		bakstr = getText();
		if (bakstr.equals("")) {
			drawmsg();
		}
	}
}
