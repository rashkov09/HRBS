package service.impl;

import model.Hotel;
import model.Room;
import repository.HotelRepository;
import service.HotelService;

import java.util.List;

public class HotelServiceImpl implements HotelService {
	private final static String SUCCESS_MESSAGE = "Hotel %s added successfully!";
	private final static String FAIl_MESSAGE = "Hotel %s was not added successfully, please try again!";
	private final static String ROOM_ADD_SUCCESS_MESSAGE = "Room number %d added successfully to hotel id %d";
	private final HotelRepository hotelRepository = new HotelRepository();
	@Override
	public String addHotel(Hotel hotel) {
		if (hotelRepository.addHotel(hotel)){
			return String.format(SUCCESS_MESSAGE,hotel.getName());
		}
		return String.format(FAIl_MESSAGE,hotel.getName());
	}

	@Override
	public String getAllHotels() {
		StringBuilder builder = new StringBuilder();
		hotelRepository.getAllHotels().forEach(hotel -> {
			builder.append(hotel.toString());
			               builder.append("\n");
		});
			return builder.toString();
	}

	@Override
	public String addRoom(Room room, Integer hotelId) {
		if (hotelRepository.addRoomToHotel(room,hotelId)){
			return String.format(ROOM_ADD_SUCCESS_MESSAGE,room.getRoomNumber(),hotelId);
		}
		return "Failed";
	}
}
