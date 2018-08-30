package librarian;

import java.awt.Font;

import javax.swing.JLabel;

public class MakeLabel extends JLabel {
	final String FONT_NAME = "HGP創英角ﾎﾟｯﾌﾟ体";
	final int FONT_STYLE = Font.BOLD;
	int FONT_SIZE;

	public MakeLabel(String message, int fontSize) {
		super(message);
		this.FONT_SIZE = fontSize;
		setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
	}

	public MakeLabel(String message) {
		this(message, 30);
	}

	public MakeLabel() {
		this(" ",30);
	}

}
