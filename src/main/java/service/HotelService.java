package service;

import model.Hotel;

import java.util.List;

public interface HotelService {

	String addHotel();

	String getAllHotels();

	String addRoom();

	String viewRoomsByHotel(int hotelId);

	Hotel getHotelById(int hotelId);

	void bookRoom(Integer id, Integer roomNumber);

	String removeRoom();

	String removeHotel();

	String editHotel();

	String editRoomInHotel();

	void updateHotel(Hotel hotel);

	List<Hotel> getAllHotelsAsList();
}
