package util;

import java.time.LocalDate;

public class DateParser {

	public static LocalDate parseFromString(String date){
		String[] data = date.split("-");
		return LocalDate.of(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
	}

}
