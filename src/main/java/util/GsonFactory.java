package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class GsonFactory {

	public static Gson getInstance() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());

		return gsonBuilder.create();
	}
}
