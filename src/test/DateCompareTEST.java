package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompareTEST {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dateTo = null;
		Date dateFrom = null;

		String preDateFrom = "19940727";
		String preDateTo = "19940727";

		try {
			dateFrom = sdf.parse(preDateFrom);
			dateTo = sdf.parse(preDateTo);
			
			System.out.println("dateFrom="+dateFrom);
			System.out.println("dateTo="+dateTo);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		long dateTimeTo=dateTo.getTime();
		long dateTimeFrom=dateFrom.getTime();
		
		System.out.println("dateTimeTo="+dateTimeTo);
		System.out.println("dateTimeFrom="+dateTimeFrom);
		
		long dayDiff=(dateTimeTo-dateTimeFrom)/(1000*60*60*24);
		System.out.println("日数の差は"+dayDiff+"日");
		
		

	}

}
