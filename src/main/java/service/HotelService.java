package service;

import model.Hotel;
import model.Room;

public interface HotelService {

	String addHotel(Hotel hotel);

	String getAllHotels();

	String addRoom(Room room, Integer hotelId);

	String viewRoomsByHotel(int hotelId);

	Hotel getHotelById(int hotelId);

	void bookRoom(Integer id, Integer roomNumber);
}
