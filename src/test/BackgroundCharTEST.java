package test;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class BackgroundCharTEST {
	public BackgroundCharTEST() {
	
	
	}
	
	
	
	public static void main(String[] args) {
		
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
}
