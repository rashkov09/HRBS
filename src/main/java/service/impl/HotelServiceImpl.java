package service.impl;

import exception.HotelNotFoundException;
import exception.RoomNotFoundException;
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

import static constant.Shared.AVAILABLE_ROOM_NUMBER;
import static constant.Shared.EDIT_SELECT;
import static constant.Shared.HOTEL_ID;
import static constant.Shared.PREFIX_TEXT;
import static constant.Shared.VERTICAL_BORDER;

public class HotelServiceImpl implements HotelService {

	private final static String SUCCESS_MESSAGE = "Hotel %s added successfully!";
	private final static String FAIl_MESSAGE = "Hotel %s was not added successfully, please try again!";
	private final static String ROOM_ADD_SUCCESS_MESSAGE = "Room number %d added successfully to hotel id %d";
	private final static String ROOM_TYPE_SELECT = "room type:\n1. Single\n2. Double\n3. Deluxe\n4. Suite";
	private final static String ROOM_STATUS_SELECT = "room status:\n1. BOOKED \n2. AVAILABLE";
	private final static String PRICE_PER_NIGHT = "price per night:";
	private final static String CANCELLATION_PRICE = "cancellation price:";
	private final static String NO_ROOMS_IN_HOTEL = "No rooms in this hotel!";
	private final static String REMOVE_ROOM_SUCCESS = "Room removed successfully!";
	private final static String REMOVE_HOTEL_SUCCESS = "Hotel removed successfully!";
	private final static String HOTEL_UPDATED_SUCCESS = "Hotel edited successfully!";
	private final static String ROOM_UPDATE_SUCCESS = "Room updated successfully!";
	private final static String REMOVE_ROOM_FAIL = "Removing of room failed!";
	private final static String REMOVE_HOTEL_FAIL = "Removing of hotel failed!";
	private final static String HOTEL_UPDATED_FAIL = "Hotel edit failed!";
	private final static String HOTEL_UPDATE_FAIL = "Room edit failed!";
	private final static String ROOM_EXISTS_ERROR = "Room number already exists!";
	private final static String HOTEL_EDIT_PARAMS = """
                                                  1. Hotel name
                                                  2. Hotel Address
                                                  3. Email address
                                                  4. Phone number
	                                                                                               
                                                  5. Cancel
                                                  0. SAVE
                                                  """;
	private final static String ROOM_EDIT_PARAMS = """
                                                 1. Room number
                                                 2. Room type
                                                 3. Room price per night
                                                 4. Room cancellation fee
                                                 5. Room status
	                                                                                             
                                                 6. CANCEL
                                                 0. SAVE
                                                 """;

	private final static int MIN_ROOM_TYPE_INT = 1;
	private final static int MIN_ROOM_STATUS_INT = 1;
	private final static int MIN_HOTEL_EDIT_INT = 0;
	private final static int MIN_ROOM_EDIT_INT = 0;
	private final static int MAX_ROOM_TYPE_INT = 4;
	private final static int MAX_ROOM_STATUS_INT = 2;
	private final static int MAX_HOTEL_EDIT_INT = 5;
	private final static int MAX_ROOM_EDIT_INT = 6;
	private static final List<String>
		HOTEL_PARAMS = List.of("hotel name:", "hotel address", "email address", "phone number");
	private static final List<String>
		ROOM_PARAMS = List.of("room number:", "room type", "room price per night", "room cancellation fee", "room status");

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
		for (int i = 0; i < HOTEL_PARAMS.size(); i++) {
			System.out.printf(PREFIX_TEXT + HOTEL_PARAMS.get(i) + "\n");
			try {
				String input = ConsoleReader.readString();
				switch (i) {
					case 0 -> hotel.setName(input);
					case 1 -> {
						hotelRepository.checkHotelNameAvailability(hotel.getName(), input);
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
		int hotelId = getHotelId();
		try {
			Hotel hotel = hotelRepository.getHotelById(hotelId);
			System.out.println(PREFIX_TEXT + AVAILABLE_ROOM_NUMBER);
			int roomNumber = ConsoleReader.readInt();
			if (isRoomNumberAvailable(roomNumber, hotel)) {
				System.out.println(PREFIX_TEXT + ROOM_TYPE_SELECT);
				int roomTypeChoice = ConsoleRangeReader.readInt(MIN_ROOM_TYPE_INT, MAX_ROOM_TYPE_INT);
				System.out.println(PREFIX_TEXT + PRICE_PER_NIGHT);
				BigDecimal pricePerNight = ConsoleReader.readBigDecimal();
				System.out.println(PREFIX_TEXT + CANCELLATION_PRICE);
				BigDecimal cancellationPrice = ConsoleReader.readBigDecimal();
				Room room =
					new Room(roomNumber, RoomType.values()[roomTypeChoice - 1], pricePerNight, cancellationPrice,
					         RoomStatus.AVAILABLE);
				hotelRepository.addRoomToHotel(room, hotelId);
				return String.format(ROOM_ADD_SUCCESS_MESSAGE, room.getRoomNumber(), hotelId);
			}
			return ROOM_EXISTS_ERROR;
		} catch (Exception e) {
			return String.format(e.getMessage(), hotelId);
		}
	}

	private boolean isRoomNumberAvailable(int roomNumber, Hotel hotel) {
		return hotel.getRooms().stream().noneMatch(r -> r.getRoomNumber().equals(roomNumber));
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
			hotel.getRooms().forEach(
				room -> builder.append(room.toString()).append(System.lineSeparator()).append(VERTICAL_BORDER)
				               .append(System.lineSeparator()));
			return builder.toString();
		} catch (HotelNotFoundException e) {
			return String.format(e.getMessage(), hotelId);
		}
	}

	@Override
	public Hotel getHotelById(int hotelId) {
		try {
			return hotelRepository.getHotelById(hotelId);
		} catch (HotelNotFoundException e) {
			System.out.printf(e.getMessage() + "\n", hotelId);
			return null;
		}
	}

	@Override
	public void bookRoom(Integer id, Integer roomNumber) {
		hotelRepository.bookRoom(id, roomNumber);
	}

	@Override
	public String removeRoom() {
		int hotelId = getHotelId();
		hotelRepository.getHotelById(hotelId).getRooms().forEach(System.out::println);
		System.out.println(PREFIX_TEXT + AVAILABLE_ROOM_NUMBER);
		int roomNumber = ConsoleReader.readInt();
		if (hotelRepository.removeRoom(hotelId, roomNumber)) {
			return REMOVE_ROOM_SUCCESS;
		}
		return REMOVE_ROOM_FAIL;
	}

	@Override
	public String removeHotel() {
		int hotelId = getHotelId();
		if (hotelRepository.removeHotel(hotelId)) {
			return REMOVE_HOTEL_SUCCESS;
		}
		return REMOVE_HOTEL_FAIL;
	}

	private int getHotelId() {
		System.out.println(PREFIX_TEXT + HOTEL_ID);
		System.out.println(getAllHotels());
		return ConsoleReader.readInt();
	}

	@Override
	public String editHotel() {
		int hotelId = getHotelId();
		try {
			Hotel hotel = hotelRepository.getHotelById(hotelId);
			System.out.println(EDIT_SELECT);
			System.out.println(HOTEL_EDIT_PARAMS);
			int choice = ConsoleRangeReader.readInt(MIN_HOTEL_EDIT_INT, MAX_HOTEL_EDIT_INT);

			while (choice != 0) {
				if (choice == 5) {
					return "Edit canceled";
				}
				try {
					switch (choice) {
						case 1 -> {
							System.out.println(PREFIX_TEXT + HOTEL_PARAMS.get(0));
							System.out.println("Current name: " + hotel.getName());
							String newName = ConsoleReader.readString();
							hotel.setName(newName);
						}
						case 2 -> {
							System.out.println(PREFIX_TEXT + HOTEL_PARAMS.get(1));
							System.out.println("Current address: " + hotel.getAddress());
							String newAddress = ConsoleReader.readString();
							hotel.setAddress(newAddress);
						}
						case 3 -> {
							System.out.println(PREFIX_TEXT + HOTEL_PARAMS.get(2));
							System.out.println("Current email: " + hotel.getEmail());
							String newEmail = ConsoleReader.readString();
							hotel.setEmail(newEmail);
						}
						case 4 -> {
							System.out.println(PREFIX_TEXT + HOTEL_PARAMS.get(3));
							System.out.println("Current phone: " + hotel.getPhone());
							String newPhone = ConsoleReader.readString();
							hotel.setPhone(newPhone);
						}
					}
					System.out.println(EDIT_SELECT);
					System.out.println(HOTEL_EDIT_PARAMS);
					choice = ConsoleRangeReader.readInt(MIN_HOTEL_EDIT_INT, MAX_HOTEL_EDIT_INT);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			if (hotelRepository.updateHotel(hotel)) {
				return HOTEL_UPDATED_SUCCESS;
			}
			return HOTEL_UPDATED_FAIL;
		} catch (Exception e) {
			return String.format(e.getMessage(), hotelId);
		}
	}

	@Override
	public String editRoomInHotel() {
		int hotelId = getHotelId();
		try {
			Hotel hotel = hotelRepository.getHotelById(hotelId);
			hotel.getRooms().forEach(System.out::println);
			System.out.println(PREFIX_TEXT + AVAILABLE_ROOM_NUMBER);
			int roomNumber = ConsoleReader.readInt();
			Room room =
				hotel.getRooms().stream().filter(r -> r.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
			if (room == null) {
				throw new RoomNotFoundException();
			}
			hotel.getRooms().removeIf(r -> r.getRoomNumber().equals(room.getRoomNumber()));
			System.out.println(EDIT_SELECT);
			System.out.println(ROOM_EDIT_PARAMS);
			int choice = ConsoleRangeReader.readInt(MIN_ROOM_EDIT_INT, MAX_ROOM_EDIT_INT);
			while (choice != 0) {
				if (choice == 6) {
					return "Edit canceled";
				}
				switch (choice) {
					case 1 -> {
						System.out.println(PREFIX_TEXT + ROOM_PARAMS.get(0));
						System.out.println("Current room number: " + room.getRoomNumber());
						int newNumberRoom = ConsoleReader.readInt();
						room.setRoomNumber(newNumberRoom);
					}
					case 2 -> {
						System.out.println(PREFIX_TEXT + ROOM_PARAMS.get(1));
						System.out.println(ROOM_TYPE_SELECT);
						System.out.println("Current room type: " + room.getRoomType().name());
						int roomType = ConsoleRangeReader.readInt(MIN_ROOM_TYPE_INT, MAX_ROOM_TYPE_INT);
						room.setRoomType(RoomType.values()[roomType - 1]);
					}
					case 3 -> {
						System.out.println(PREFIX_TEXT + ROOM_PARAMS.get(2));
						System.out.println("Current room price: " + room.getPricePerNight());
						BigDecimal roomPricePerNight = ConsoleReader.readBigDecimal();
						room.setPricePerNight(roomPricePerNight);
					}
					case 4 -> {
						System.out.println(PREFIX_TEXT + ROOM_PARAMS.get(3));
						System.out.println("Current room cancellation fee: " + room.getCancellationFee());
						BigDecimal roomCancellationFee = ConsoleReader.readBigDecimal();
						room.setCancellationFee(roomCancellationFee);
					}
					case 5 -> {
						System.out.println(PREFIX_TEXT + ROOM_PARAMS.get(4));
						System.out.println("Current room status: " + room.getRoomStatus().name());
						System.out.println(ROOM_STATUS_SELECT);
						int roomStatus = ConsoleRangeReader.readInt(MIN_ROOM_STATUS_INT, MAX_ROOM_STATUS_INT);
						room.setRoomStatus(RoomStatus.values()[roomStatus - 1]);
					}
				}
				System.out.println(EDIT_SELECT);
				System.out.println(ROOM_EDIT_PARAMS);
				choice = ConsoleRangeReader.readInt(MIN_ROOM_EDIT_INT, MAX_ROOM_EDIT_INT);
			}
			hotel.getRooms().add(room);
			if (hotelRepository.updateHotel(hotel)) {
				return ROOM_UPDATE_SUCCESS;
			}
			return HOTEL_UPDATE_FAIL;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public void updateHotel(Hotel hotel) {
		hotelRepository.updateHotel(hotel);
	}

	@Override
	public List<Hotel> getAllHotelsAsList() {
		return hotelRepository.getAllHotels();
	}
}
