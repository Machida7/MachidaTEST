package librarian;

import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MakeTimerLabel extends JLabel {
	private DateFormat format;

	public MakeTimerLabel() {
		this.setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 17));
		format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Timer timer = new Timer();
		//1000ms=1秒ごとに実行
		timer.schedule(new TimerLabelTask(), 0, 1000);
	}

	public void setTime() {
		Calendar calender = Calendar.getInstance();
		this.setText(format.format(calender.getTime()));
	}

	class TimerLabelTask extends TimerTask {
		@Override
		public void run() {
			setTime();
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("時計だよ");
		f.add(new MakeTimerLabel());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 400);
		f.setVisible(true);

	}

}
