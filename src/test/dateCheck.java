package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class dateCheck {

	public static void main(String[] args) {
		try {
		LocalDate date = LocalDate.parse("20190808",
				DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT));
		System.out.println(date);
		}catch(DateTimeParseException e) {
			System.out.println("era-");
		}
		
		}

}
