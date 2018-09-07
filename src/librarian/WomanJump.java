package librarian;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class WomanJump extends JFrame {

	final int FRAME_WIDTH = 400;
	final int FRAME_HEIGHT = 650;

	private final String IMAGE_FILE_NAME = "tosyokan_woman.png";

	private BufferedImage bufferedImage = null;

	private int changeMove = 1;

	/** 星 位置 */
	private int starX = 100;
	private int starY = 200;

	public static void main(String[] args) {
		new WomanJump();
	}



	public WomanJump() {
		setTitle("お姉さんうれしい");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		// 画像の読み込み
		try (InputStream inputStream = this.getClass().getResourceAsStream(IMAGE_FILE_NAME);) {
			bufferedImage = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* 星の位置初期化
		starX = -bufferedImage.getWidth();
		starY = -bufferedImage.getHeight();
		*/

		// タイマー
		Timer timer = new Timer();
		//MyTimeTaskを0秒後に実行開始し、その後10m秒ごとに実行する
		timer.schedule(new MyTimeTask(), 0, 10);

		// 表示
		setVisible(true);
	}

	public void paint(Graphics g) {
		if (changeMove == 1) {
			//上昇
			g.drawImage(getScreen1(), 0, 0, this);
		} else if (changeMove == 2) {
			//降下
			g.drawImage(getScreen2(), 0, 0, this);

		}

	}

	private Image getScreen1() {
		Image screen = createImage(FRAME_WIDTH, FRAME_HEIGHT);

		//上昇　Y軸２００からダウン  てっぺんで緩やかに
		if (starY >= 180) {
			starY -= 3;
			Graphics g = screen.getGraphics();
			g.drawImage(bufferedImage, starX, starY, this);
		} else if (starY >= 160) {
			starY -= 2;
			Graphics g = screen.getGraphics();
			g.drawImage(bufferedImage, starX, starY, this);
		} else if (starY >= 150) {
			starY--;
			Graphics g = screen.getGraphics();
			g.drawImage(bufferedImage, starX, starY, this);
		} else {

			Graphics g = screen.getGraphics();
			g.drawImage(bufferedImage, starX, starY, this);
			changeMove = 2;
		}
		/*
		  System.out.println("X" + starX);
		   System.out.println("Y" + starY);
		    System.out.println("番号" + changeMove);
		 */

		return screen;
	}

	private Image getScreen2() {
		Image screen = createImage(FRAME_WIDTH, FRAME_HEIGHT);

		// 下降　落ちるときはすっと
		if (starY <= 200) {
			starY += 2;
			Graphics g = screen.getGraphics();
			g.drawImage(bufferedImage, starX, starY, this);
		} else {
			Graphics g = screen.getGraphics();
			g.drawImage(bufferedImage, starX, starY, this);
			changeMove = 1;
		}
		/*
		System.out.println("X" + starX);
		System.out.println("Y" + starY);
		System.out.println("番号" + changeMove);
		*/


		return screen;
	}

	private class MyTimeTask extends TimerTask {

		//定期的に実行する処理
		@Override
		public void run() {
			repaint();
		}

	}

}