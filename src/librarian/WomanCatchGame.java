package librarian;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WomanCatchGame extends JFrame {
	int touchWoman = 0;
	String resultMessage;
	JLabel result;
	private JLabel label;

	/*public static void main(String args[]) {
		new WomanMoveTEST("タイトル");
	}
	*/



	public WomanCatchGame(String title) {
		setTitle(title);
		setBounds(100, 100, 1400, 700);

		//単独で実行する際はコメント解除
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel p = new JPanel();

		ImageIcon icon1 = new ImageIcon("./src/librarian/tosyokan_woman.png");



		MediaTracker tracker = new MediaTracker(this);
		Image smallImg = icon1.getImage().getScaledInstance((int) (icon1.getIconWidth() * 0.5), -1,
				Image.SCALE_SMOOTH);

		tracker.addImage(smallImg, 2);

		ImageIcon smallIcon = new ImageIcon(smallImg);

		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		label = new JLabel(smallIcon);
		Timer timer = new Timer();

		timer.schedule(new WomanMoveTask(), 0, 850);

		// 座標指定
		p.setLayout(null);
		label.setBounds(0, 0, 100, 250);

		// リスナーを登録
		MyMouseListener listener = new MyMouseListener();
		label.addMouseListener(listener);

		result = new JLabel();
		result.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 20));
		result.setText("捕まえてごらん（頭を狙え！）");
		result.setBounds(600, 0, 500, 100);

		p.add(label);
		p.add(result);

		Container contentPane = getContentPane();
		contentPane.add(p, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private class MyMouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {

			Point point = e.getPoint();
			int clickX = point.x;
			int clickY = point.y;

			int Xmin = 16;
			int Xmax = 80;
			int Ymin = 14;
			int Ymax = 87;

			if (clickX >= Xmin && clickX <= Xmax && clickY >= Ymin && clickY <= Ymax) {
				touchWoman++;
				result.setText(touchWoman + "HIT！");

			} else {
				result.setText("違う！頭だ！");

			}

		}

	}

	class WomanMoveTask extends TimerTask {
		int ranX;
		int ranY;

		@Override
		public void run() {

			int ranX = (int) (Math.random() * 1300);
			int ranY = (int) (Math.random() * 600);

			label.setLocation(ranX, ranY);
		}

	}
}