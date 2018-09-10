package test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FiveStarTEST extends JFrame implements MouseListener {
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

	public Component makePictureLabel(ImageIcon iconName, String URL, JLabel labelName) {
		iconName = new ImageIcon(URL);
		labelName = new JLabel(iconName);
		return labelName;
	}

	private JPanel p;

	public FiveStarTEST() {
		makeWindow("FiveStar");

		star0 = new ImageIcon(srar0Icon);
		star0Label = new JLabel(star0);
		star1 = new ImageIcon(srar1Icon);
		star1Label = new JLabel(star1);
		star2 = new ImageIcon(srar2Icon);
		star2Label = new JLabel(star2);
		star3 = new ImageIcon(srar3Icon);
		star3Label = new JLabel(star3);
		star4 = new ImageIcon(srar4Icon);
		star4Label = new JLabel(star4);
		star5 = new ImageIcon(srar5Icon);
		star5Label = new JLabel(star5);

		p = new JPanel();
		p.add(star0Label, BorderLayout.CENTER);
		p.add(star1Label, BorderLayout.CENTER);
		p.add(star2Label, BorderLayout.CENTER);
		p.add(star3Label, BorderLayout.CENTER);
		p.add(star4Label, BorderLayout.CENTER);
		p.add(star5Label, BorderLayout.CENTER);
		star1Label.setVisible(false);
		star2Label.setVisible(false);
		star3Label.setVisible(false);
		star4Label.setVisible(false);
		star5Label.setVisible(false);

		star0Label.addMouseListener(this);
		star1Label.addMouseListener(this);
		star2Label.addMouseListener(this);
		star3Label.addMouseListener(this);
		star4Label.addMouseListener(this);
		star5Label.addMouseListener(this);

		Container contentPane = getContentPane();
		contentPane.add(p, BorderLayout.CENTER);
		makeWindow("FiveStar");

	}

	public static void main(String[] args) {
		FiveStarTEST frame = new FiveStarTEST();

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

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		System.out.println("x=" + point.x);
		System.out.println("y=" + point.y);
		int x = point.x;
		int y = point.y;

		if (x > 6 && x <= 82 && y > 7 && y < 71) {
			star0Label.setVisible(false);
			star1Label.setVisible(true);
			star2Label.setVisible(false);
			star3Label.setVisible(false);
			star4Label.setVisible(false);
			star5Label.setVisible(false);

		} else if (x > 82 && x <= 157 && y > 7 && y < 71) {
			star0Label.setVisible(false);
			star1Label.setVisible(false);
			star2Label.setVisible(true);
			star3Label.setVisible(false);
			star4Label.setVisible(false);
			star5Label.setVisible(false);
		} else if (x > 157 && x <= 234 && y > 7 && y < 71) {
			star0Label.setVisible(false);
			star1Label.setVisible(false);
			star2Label.setVisible(false);
			star3Label.setVisible(true);
			star4Label.setVisible(false);
			star5Label.setVisible(false);
		} else if (x > 234 && x <= 314 && y > 7 && y < 71) {
			star0Label.setVisible(false);
			star1Label.setVisible(false);
			star2Label.setVisible(false);
			star3Label.setVisible(false);
			star4Label.setVisible(true);
			star5Label.setVisible(false);
		} else if (x > 314 && x <= 389 && y > 7 && y < 71) {
			star0Label.setVisible(false);
			star1Label.setVisible(false);
			star2Label.setVisible(false);
			star3Label.setVisible(false);
			star4Label.setVisible(false);
			star5Label.setVisible(true);
		} else {
			star0Label.setVisible(true);
			star1Label.setVisible(false);
			star2Label.setVisible(false);
			star3Label.setVisible(false);
			star4Label.setVisible(false);
			star5Label.setVisible(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/*星パネルに乗った瞬間の座標を取得する為うまくいかず
	 * 星一つ一つパネルにすれば実装可能
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	/*　
	 Point point = e.getPoint();
	int x = point.x;
	int y = point.y;
	System.out.println("x=" + x);
	System.out.println("y=" + y);

	if (x > 6 && x <= 82 && y > 7 && y < 71) {
		star0Label.setVisible(false);
		star1Label.setVisible(true);
		star2Label.setVisible(false);
		star3Label.setVisible(false);
		star4Label.setVisible(false);
		star5Label.setVisible(false);

	} else if (x > 82 && x <= 157 && y > 7 && y < 71) {
		star0Label.setVisible(false);
		star1Label.setVisible(false);
		star2Label.setVisible(true);
		star3Label.setVisible(false);
		star4Label.setVisible(false);
		star5Label.setVisible(false);
	} else if (x > 157 && x <= 234 && y > 7 && y < 71) {
		star0Label.setVisible(false);
		star1Label.setVisible(false);
		star2Label.setVisible(false);
		star3Label.setVisible(true);
		star4Label.setVisible(false);
		star5Label.setVisible(false);
	} else if (x > 234 && x <= 314 && y > 7 && y < 71) {
		star0Label.setVisible(false);
		star1Label.setVisible(false);
		star2Label.setVisible(false);
		star3Label.setVisible(false);
		star4Label.setVisible(true);
		star5Label.setVisible(false);
	} else if (x > 314 && x <= 389 && y > 7 && y < 71) {
		star0Label.setVisible(false);
		star1Label.setVisible(false);
		star2Label.setVisible(false);
		star3Label.setVisible(false);
		star4Label.setVisible(false);
		star5Label.setVisible(true);
	} else {
		star0Label.setVisible(true);
		star1Label.setVisible(false);
		star2Label.setVisible(false);
		star3Label.setVisible(false);
		star4Label.setVisible(false);
		star5Label.setVisible(false);
	}
	*/
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
