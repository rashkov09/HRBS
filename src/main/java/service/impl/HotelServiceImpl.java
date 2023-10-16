package service.impl;

import exceptions.HotelNotFoundException;
import model.Hotel;
import model.Room;
import model.enums.RoomStatus;
import model.enums.RoomType;
import repository.HotelRepository;
import service.HotelService;
import util.ConsoleRangeReader;
import util.ConsoleReader;

import java.math.BigDecimal;
import java.util.List;

import static constants.Shared.AVAILABLE_ROOM_NUMBER;
import static constants.Shared.HOTEL_ID;
import static constants.Shared.PREFIX_TEXT;
import static constants.Shared.VERTICAL_BORDER;

public class HotelServiceImpl implements HotelService {

	private final static String SUCCESS_MESSAGE = "Hotel %s added successfully!";
	private final static String FAIl_MESSAGE = "Hotel %s was not added successfully, please try again!";
	private final static String ROOM_ADD_SUCCESS_MESSAGE = "Room number %d added successfully to hotel id %d";
	private final static String ROOM_TYPE_SELECT = "room type:\n1. Single\n2. Double\n3. Deluxe\n4. Suite";
	private final static String PRICE_PER_NIGHT = "price per night:";
	private final static String CANCELLATION_PRICE =  "cancellation price:";
	private final static String NO_ROOMS_IN_HOTEL =  "No rooms in this hotel!";
	private final static int MIN_ROOM_TYPE_INT =  1;
	private final static int MAX_ROOM_TYPE_INT =  4;
	private static final List<String>
		params = List.of("hotel name:", "hotel address", "email address", "phone number");
	private final HotelRepository hotelRepository = new HotelRepository();

	@Override
	public String addHotel() {
	  Hotel hotel = createHotel();
 		if (hotelRepository.addHotel(hotel)) {
			return String.format(SUCCESS_MESSAGE, hotel.getName());
		}
		return String.format(FAIl_MESSAGE, hotel.getName());
	}

	private Hotel createHotel() {
		Hotel hotel = new Hotel();
		for (int i = 0; i < params.size(); i++) {
			System.out.printf(PREFIX_TEXT + params.get(i) + "\n");
			try {
				String input = ConsoleReader.readString();
				switch (i) {
					case 0 -> hotel.setName(input);
					case 1 -> {
						hotelRepository.checkHotelNameAvailability(hotel.getName(),input);
						hotel.setAddress(input);
					}
					case 2 -> hotel.setEmail(input);
					case 3 -> hotel.setPhone(input);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				i--;
			}
		}
			return hotel;
	}

	@Override
	public String getAllHotels() {
		StringBuilder builder = new StringBuilder();
		hotelRepository.getAllHotels().forEach(hotel -> builder.append(hotel.toString()).append(System.lineSeparator()));
		return builder.toString();
	}

	@Override
	public String addRoom() {
		System.out.println(PREFIX_TEXT+HOTEL_ID);
		System.out.println(getAllHotels());
		int hotelId = ConsoleReader.readInt();
		System.out.println(PREFIX_TEXT+AVAILABLE_ROOM_NUMBER);
		int roomNumber = ConsoleReader.readInt();
		System.out.println(PREFIX_TEXT+ROOM_TYPE_SELECT);
		int roomTypeChoice = ConsoleRangeReader.readInt(MIN_ROOM_TYPE_INT, MAX_ROOM_TYPE_INT);
		System.out.println(PREFIX_TEXT+PRICE_PER_NIGHT);
		BigDecimal pricePerNight = ConsoleReader.readBigDecimal();
		System.out.println(PREFIX_TEXT+CANCELLATION_PRICE);
		BigDecimal cancellationPrice = ConsoleReader.readBigDecimal();
		Room room =
			new Room(roomNumber, RoomType.values()[roomTypeChoice - 1], pricePerNight, cancellationPrice,
			         RoomStatus.AVAILABLE);
		try{
		hotelRepository.addRoomToHotel(room, hotelId);
			return String.format(ROOM_ADD_SUCCESS_MESSAGE, room.getRoomNumber(), hotelId);
		}catch (Exception e){
			return String.format(e.getMessage(),hotelId);
		}
	}

	@Override
	public String viewRoomsByHotel(int hotelId) {
		StringBuilder builder = new StringBuilder();
		try {
			Hotel hotel = hotelRepository.getHotelById(hotelId);
			if (hotel.getRooms().isEmpty()) {
				return NO_ROOMS_IN_HOTEL;
			}
			builder.append(hotel.getName())
			       .append(System.lineSeparator()).append(VERTICAL_BORDER).append(System.lineSeparator());
			hotel.getRooms().forEach(room -> builder.append(room.toString()).append(System.lineSeparator()).append(VERTICAL_BORDER).append(System.lineSeparator()));
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
