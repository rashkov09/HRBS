package service.impl;

import exceptions.HotelNotFoundException;
import model.Hotel;
import model.Room;
import repository.HotelRepository;
import service.HotelService;

import java.util.List;

import static constants.Borders.VERTICAL_BORDER;

public class HotelServiceImpl implements HotelService {

	private final static String SUCCESS_MESSAGE = "Hotel %s added successfully!";
	private final static String FAIl_MESSAGE = "Hotel %s was not added successfully, please try again!";
	private final static String ROOM_ADD_SUCCESS_MESSAGE = "Room number %d added successfully to hotel id %d";
	private final HotelRepository hotelRepository = new HotelRepository();

	@Override
	public String addHotel(Hotel hotel) {
		if (hotelRepository.addHotel(hotel)) {
			return String.format(SUCCESS_MESSAGE, hotel.getName());
		}
		return String.format(FAIl_MESSAGE, hotel.getName());
	}

	@Override
	public String getAllHotels() {
		StringBuilder builder = new StringBuilder();
		hotelRepository.getAllHotels().forEach(hotel -> {
			builder.append(hotel.toString()).append(System.lineSeparator());
		});
		return builder.toString();
	}

	@Override
	public String addRoom(Room room, Integer hotelId) {
		if (hotelRepository.addRoomToHotel(room, hotelId)) {
			return String.format(ROOM_ADD_SUCCESS_MESSAGE, room.getRoomNumber(), hotelId);
		}
		return "Failed";
	}

	@Override
	public String viewRoomsByHotel(int hotelId) {
		StringBuilder builder = new StringBuilder();
		try {
			Hotel hotel = hotelRepository.getHotelById(hotelId);
			if (hotel.getRooms().isEmpty()) {
				return "No rooms in this hotel";
			}
			builder.append(hotel.getName())
			       .append(System.lineSeparator()).append(VERTICAL_BORDER).append(System.lineSeparator());
			hotel.getRooms().forEach(room -> {
				builder.append(room.toString()).append(System.lineSeparator()).append(VERTICAL_BORDER).append(System.lineSeparator());
			});
			return builder.toString();
		} catch (HotelNotFoundException e) {
			return String.format(e.getMessage(), hotelId);
		}
	}

	@Override
	public Hotel getHotelById(int hotelId) {
		try {
			return hotelRepository.getHotelById(hotelId);
		} catch (HotelNotFoundException e){
			System.out.printf(e.getMessage()+"\n",hotelId);
			return null;
		}
	}

	@Override
	public void bookRoom(Integer id, Integer roomNumber) {
		hotelRepository.bookRoom(id,roomNumber);
	}
}
