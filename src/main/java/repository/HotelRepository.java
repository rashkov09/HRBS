package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.HotelAlreadyExistsException;
import exceptions.HotelNotFoundException;
import model.Hotel;
import model.Room;
import model.enums.RoomStatus;
import util.GsonFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HotelRepository {

	private static final String jsonFilePath = "src/main/resources/hotels.json";
	private final Gson gson = GsonFactory.getInstance();

	public List<Hotel> getAllHotels() {
		List<Hotel> hotels;
		try (Reader reader = new FileReader(jsonFilePath)) {
			Type hotelListType = new TypeToken<List<Hotel>>() {
			}.getType();
			hotels = gson.fromJson(reader, hotelListType);
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return hotels != null ? hotels : new ArrayList<>();
	}

	public boolean addHotel(Hotel hotel) {
		List<Hotel> hotels = getAllHotels();
		hotel.setId(hotels.size() + 1);
		hotels.add(hotel);
		String jsonData = gson.toJson(hotels);
		return save(jsonData);
	}

	public void addRoomToHotel(Room room, Integer hotelId) {
		List<Hotel> hotels = getAllHotels();
		try {
			hotels.stream().filter(hotel -> hotel.getId().equals(hotelId)).findFirst().get().addRoom(room);
			String jsonData = gson.toJson(hotels);
			 save(jsonData);
		} catch (NoSuchElementException e) {
			throw new HotelNotFoundException();
		}
	}

	private boolean save(String data) {
		try (FileWriter writer = new FileWriter(jsonFilePath)) {
			writer.write(data);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Hotel getHotelById(int hotelId) {
		List<Hotel> hotels = getAllHotels();
		Hotel hotel = hotels.stream().filter(h -> h.getId().equals(hotelId)).findFirst().orElse(null);
		if (hotel == null) {
			throw new HotelNotFoundException();
		}
		return hotel;
	}

	public void bookRoom(Integer id, Integer roomNumber) {
		try {
			List<Hotel> hotels = getAllHotels();
			hotels.stream().filter(hotel -> hotel.getId().equals(id)).findFirst().get().getRooms().stream()
			      .filter(room -> room.getRoomNumber().equals(roomNumber)).findFirst().get().setRoomStatus(
				      RoomStatus.BOOKED);
			String jsonData = gson.toJson(hotels);
			save(jsonData);
		} catch (NoSuchElementException e) {
			throw new HotelNotFoundException();
		}
	}

	public void checkHotelNameAvailability(String hotelName, String hotelAddress) {
		List<Hotel> hotels = getAllHotels();
		Hotel hotel = new Hotel();
		hotel.setName(hotelName);
		hotel.setAddress(hotelAddress);
		if (hotels.contains(hotel)) {
			throw new HotelAlreadyExistsException();
		}
	}
}
