package test;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
//ボタンを作るためのクラス
public class CopyMakeButton extends JButton{


	public CopyMakeButton(String buttonName,ActionListener actionListner) {
		super(buttonName);

		final String FONT_NAME="HGP創英角ﾎﾟｯﾌﾟ体";
		final int FONT_STYLE=Font.BOLD;
		final int FONT_SIZE=40;
		setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
		addActionListener(actionListner);


	}

}
