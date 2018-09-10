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

	//ゲーム難易度
	//体力
	private int womanHP = 10;
	private int playerHP = 10;
	//移動スピード(ms)
	private int womanMoveSpeed = 750;
	//攻撃頻度　
	private int womanAttackFrequency = 3;

	private int damageWomanCount = 0;
	private int damagePlayerCount = 0;
	private int womanMovecount = 0;

	private Timer timer;
	private JLabel womanMessage;
	private JLabel womanHPBarLabel;
	private JLabel playerHPBarLabel;
	private String womanHPBar = "";
	private String playerHPBar = "";
	private JLabel womanLabel;
	private String yarukiWoman = "./src/librarian/yaruki_moeru_woman.png";
	private String loseWoman = "./src/librarian/yaruki_moetsuki_woman.png";
	private String librarianWoman = "./src/librarian/tosyokan_woman.png";
	private String atacckWoman="./src/librarian/pose_sugoi_okoru_woman.png";
	private String damagedWoman="./src/librarian/tachiagaru_woman.png";
	private String noDamageWoman="./src/librarian/ahiruguchi.png";


	public static void main(String args[]) {
		WomanCatchGame game= new WomanCatchGame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public  ImageIcon makeSmallIcon(String icon) {
		ImageIcon imageIcon = new ImageIcon(icon);
		MediaTracker tracker = new MediaTracker(this);
		Image smallImg = imageIcon.getImage().getScaledInstance((int) (imageIcon.getIconWidth() * 0.5), -1,
				Image.SCALE_SMOOTH);
		tracker.addImage(smallImg, 2);
		ImageIcon smallIcon = new ImageIcon(smallImg);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return smallIcon;

	}

	public WomanCatchGame() {
		setTitle("相手をしてあげよう");
		setBounds(100, 100, 1400, 700);

		
		

		JPanel p = new JPanel();

		womanLabel = new JLabel(makeSmallIcon(yarukiWoman));
		timer = new Timer();
		timer.schedule(new WomanMoveTask(), 0, womanMoveSpeed);

		// 座標指定
		p.setLayout(null);
		womanLabel.setBounds(600, 200, 200, 250);

		// リスナーを登録
		MyMouseListener listener = new MyMouseListener();
		womanLabel.addMouseListener(listener);

		womanMessage = new JLabel();
		womanMessage.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 30));
		womanMessage.setText("私に勝てるかな（頭を狙え！）");
		womanMessage.setBounds(500, 50, 700, 50);

		womanHPBarLabel = new JLabel();
		womanHPBarLabel.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 20));

		for (int i = 0; i < womanHP; i++) {
			womanHPBar = womanHPBar + "■";
		}
		womanHPBarLabel.setText("お姉さんのHP " + womanHPBar);
		womanHPBarLabel.setBounds(50, 0, 1400, 50);

		playerHPBarLabel = new JLabel();
		playerHPBarLabel.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 20));

		for (int i = 0; i < playerHP; i++) {
			playerHPBar = playerHPBar + "■";
		}
		playerHPBarLabel.setText("自分のHP " + playerHPBar);
		playerHPBarLabel.setBounds(50, 600, 1400, 50);

		p.add(womanLabel);
		p.add(womanMessage);
		p.add(womanHPBarLabel);
		p.add(playerHPBarLabel);

		Container contentPane = getContentPane();
		contentPane.add(p, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private class MyMouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {

			Point point = e.getPoint();
			int clickX = point.x;
			int clickY = point.y;

			//当たり判定　顔
			int Xmin = 59;
			int Xmax = 135;
			int Ymin = 55;
			int Ymax = 140;

			if (womanHP != 0 && playerHP != 0) {

				if (clickX >= Xmin && clickX <= Xmax && clickY >= Ymin && clickY <= Ymax) {


					damageWomanCount++;
					womanHP--;
					womanLabel.setIcon(makeSmallIcon(damagedWoman));
					womanMessage.setText("ぐっやるな");

					womanHPBar = "";
					for (int i = 0; i < damageWomanCount; i++) {
						womanHPBar = womanHPBar + "□";
					}
					for (int j = 0; j < womanHP; j++) {
						womanHPBar = womanHPBar + "■";
					}
					womanHPBarLabel.setText("お姉さんのHP " + womanHPBar);

				} else {
					womanLabel.setIcon(makeSmallIcon(noDamageWoman));
					womanMessage.setText("効かぬ　(頭を狙え！)");

				}
			}
			if (womanHP == 0) {
				womanLabel.setIcon(makeSmallIcon(loseWoman));
				womanLabel.setLocation(600, 200);
				womanMessage.setText("私の負けだ... (YOU WIN!)");
				timer.cancel();

			}

			//System.out.println("X:"+clickX+" Y:"+clickY);

		}

	}

	class WomanMoveTask extends TimerTask {
		int ranX;
		int ranY;

		@Override
		public void run() {
			if (damageWomanCount != 0) {
				womanLabel.setIcon(makeSmallIcon(yarukiWoman));

				ranX = (int) (Math.random() * 1200);
				ranY = (int) (Math.random() * 450);
				womanLabel.setLocation(ranX, ranY);



				womanMovecount++;
				if (womanMovecount % womanAttackFrequency == 0) {
					womanMessage.setText("くらえ！");
					womanLabel.setIcon(makeSmallIcon(atacckWoman));

					damagePlayerCount++;
					playerHP--;
					playerHPBar = "";
					for (int i = 0; i < damagePlayerCount; i++) {
						playerHPBar = playerHPBar + "□";
					}
					for (int j = 0; j < playerHP; j++) {
						playerHPBar = playerHPBar + "■";
					}
					playerHPBarLabel.setText("自分のHP " + playerHPBar);
				}

				if (playerHP == 0) {
					womanLabel.setIcon(makeSmallIcon(librarianWoman));
					womanLabel.setLocation(600, 200);
					womanMessage.setText("さて、仕事に戻りますね　(YOU LOSE...)");
					timer.cancel();
				}
			}

		}

	}
}