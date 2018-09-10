package test;

import java.awt.Font;

import javax.swing.JLabel;

public class CopyMakeLabel extends JLabel {
	final String FONT_NAME = "HGP創英角ﾎﾟｯﾌﾟ体";
	final int FONT_STYLE = Font.BOLD;
	int FONT_SIZE;

	public CopyMakeLabel(String message, int fontSize) {
		super(message);
		this.FONT_SIZE = fontSize;
		setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
	}

	public CopyMakeLabel(String message) {
		this(message, 30);
	}

	public CopyMakeLabel() {
		this(" ",30);
	}

}
