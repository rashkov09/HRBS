package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.HotelAlreadyExistsException;
import exception.HotelNotFoundException;
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
		hotels.stream().filter(hotel -> hotel.getId().equals(hotelId)).findFirst().ifPresent(h -> h.addRoom(room));
		String jsonData = gson.toJson(hotels);
		save(jsonData);
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
		List<Hotel> hotels = getAllHotels();
		hotels.stream().filter(hotel -> hotel.getId().equals(id)).findFirst().flatMap(h -> h.getRooms().stream()
		                                                                                    .filter(
			                                                                                    room -> room.getRoomNumber()
			                                                                                                .equals(
				                                                                                                roomNumber))
		                                                                                    .findFirst())
		      .ifPresent(r -> r.setRoomStatus(
			      RoomStatus.BOOKED));
		String jsonData = gson.toJson(hotels);
		save(jsonData);
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

	public boolean removeRoom(int hotelId, int roomNumber) {
		List<Hotel> hotels = getAllHotels();
		hotels.stream().filter(h -> h.getId().equals(hotelId)).findFirst().ifPresent(h ->
			                                                                             h.getRooms()
			                                                                              .removeIf(
				                                                                              room -> room.getRoomNumber()
				                                                                                          .equals(
					                                                                                          roomNumber)));
		for (Hotel hotel : hotels) {
			if (hotel.getId().equals(hotelId)) {
				Room room =
					hotel.getRooms().stream().filter(r -> r.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
				if (room != null) {
					return false;
				}
			}
		}
		String jsonData = gson.toJson(hotels);
		save(jsonData);
		return true;
	}

	public boolean removeHotel(int hotelId) {
		List<Hotel> hotels = getAllHotels();
		hotels.removeIf(hotel -> hotel.getId().equals(hotelId));
		String jsonData = gson.toJson(hotels);
		save(jsonData);
		Hotel hotel = hotels.stream().filter(h -> h.getId().equals(hotelId)).findFirst().orElse(null);
		return hotel == null;
	}

	public boolean updateHotel(Hotel hotel) {
		List<Hotel> hotels = getAllHotels();
		hotels.removeIf(h -> h.getId().equals(hotel.getId()));
		hotels.add(hotel);
		Hotel updatedHotel = hotels.stream().filter(h->h.getId().equals(hotel.getId())).findFirst().orElse(null);
		String jsonData = gson.toJson(hotels);
		save(jsonData);
		return updatedHotel !=null && updatedHotel.getName().equals(hotel.getName()) &&
		       updatedHotel.getAddress().equals(hotel.getAddress()) &&
		       updatedHotel.getEmail().equals(hotel.getEmail()) &&
		       updatedHotel.getPhone().equals(hotel.getPhone());

	}

}
