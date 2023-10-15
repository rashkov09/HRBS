package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public  class DateParser {

	public static Date parseDateFromString(String dateString){
		String inputDateFormat = "yyyy-MM-dd";
		Date date = null ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
			date= sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
