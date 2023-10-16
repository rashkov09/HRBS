package service.impl;

import model.Booking;
import model.Hotel;
import model.Room;
import model.User;
import model.enums.RoomStatus;
import repository.BookingRepository;
import service.BookingService;
import service.HotelService;
import service.UserService;
import util.ConsoleReader;
import util.DateParser;

import java.util.regex.Pattern;

import static constants.Shared.AVAILABLE_ROOM_NUMBER;
import static constants.Shared.HOTEL_ID;
import static constants.Shared.PREFIX_TEXT;

public class BookingServiceImpl implements BookingService {

	private static final String FROM_DATE = "FROM date in format yyyy-MM-dd:";
	private static final String TO_DATE = "TO date in format yyyy-MM-dd:";
	private static final String INVALID_ROOM_NUMBER = "Invalid room number, please insert existing room number!";
	private static final String BOOKING_SUCCESSFUL = "Booking added successfully!";
	private static final String BOOKING_FAILED = "Booking failed!";
	private static final String INVALID_DATE_INPUT_MESSAGE = "Invalid date input! Valid input should be yyyy-MM-dd!";
	private static final String ROOM_NOT_AVAILABLE = "Room with this number is already booked!";
	private final BookingRepository bookingRepository = new BookingRepository();
	private final HotelService hotelService = new HotelServiceImpl();
	private final UserService userService = new UserServiceImpl();

	@Override
	public String addBooking(User user) {
		String result = "";
		System.out.println(PREFIX_TEXT + FROM_DATE);
		String fromDate = ConsoleReader.readString();
		if (isInvalidDateInput(fromDate)){
			 System.out.println(INVALID_DATE_INPUT_MESSAGE);
			 addBooking(user);
		}
		System.out.println(PREFIX_TEXT + TO_DATE);
		String toDate = ConsoleReader.readString();
		if (isInvalidDateInput(toDate)){
			System.out.println(INVALID_DATE_INPUT_MESSAGE);
			addBooking(user);
		}
		System.out.println(PREFIX_TEXT + HOTEL_ID);
		System.out.println(hotelService.getAllHotels());
		int hotelId = ConsoleReader.readInt();
		Hotel hotel = hotelService.getHotelById(hotelId);
		if (hotel != null) {
			Room selectedRoom;
			do {
				System.out.println(PREFIX_TEXT + AVAILABLE_ROOM_NUMBER);
				hotel.getRooms().stream().filter(room -> room.getRoomStatus().equals(RoomStatus.AVAILABLE)).forEach(
					System.out::println);
				int roomNumber = ConsoleReader.readInt();
				selectedRoom =
					hotel.getRooms().stream().filter(r -> r.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
				if (selectedRoom == null) {
					System.out.println(INVALID_ROOM_NUMBER);
				} else if (!roomIsAvailable(selectedRoom.getRoomNumber())){
					System.out.println(ROOM_NOT_AVAILABLE);
					selectedRoom = null;
				}
			} while (selectedRoom == null);
			Booking booking =
				new Booking(hotel, selectedRoom, DateParser.parseFromString(fromDate), DateParser.parseFromString(toDate));

			result = BOOKING_FAILED;
			if (bookingRepository.addBooking(booking)) {
				try {
					hotelService.bookRoom(booking.getHotel().getId(), booking.getRoom().getRoomNumber());
					user.getBookings().add(booking);
					userService.updateUser(user);
					result = BOOKING_SUCCESSFUL;
				} catch (Exception e) {
					result = e.getMessage();
				}
			}
		}
		return result;
	}

	private boolean roomIsAvailable(Integer roomNumber) {
		return bookingRepository.checkRoomAvailability(roomNumber);
	}

	private boolean isInvalidDateInput(String fromDate) {
		String datePattern = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
		return !Pattern.matches(datePattern, fromDate);
	}
}