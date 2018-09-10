package test;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;




public class CopyLBWindow extends JFrame {
//テスト用
	public CopyLBWindow(JPanel panel) {
		Container contentPane = getContentPane();
		contentPane.add(panel, BorderLayout.CENTER);
		makeWindow("TEST");
	}

	public CopyLBWindow(String _windowName) {
		Container contentPane = getContentPane();
		contentPane.add(new WomanRoomPanel(), BorderLayout.CENTER);
		makeWindow(_windowName);
	}


	public static void main(String[] args) {
		new CopyLBWindow("ようこそ！");
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
