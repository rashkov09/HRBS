package view;

import model.Booking;
import model.Hotel;
import model.Room;
import model.enums.RoomStatus;
import service.BookingService;
import service.HotelService;
import service.impl.BookingServiceImpl;
import service.impl.HotelServiceImpl;
import util.ConsoleRangeReader;
import util.ConsoleReader;
import util.DateParser;

public class UserView implements ConsoleView {

	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 2;
	private static final String MESSAGE =
		"Welcome %s! Please, select an option to continue:\n1. View Rooms\n2. Book a Room\n3. Cancel Booking\n0. Logout";
	private static final String MESSAGE_WITHOUT_USERNAME =
		"Please, select an option to continue:\n1. View Rooms\n2. Book a Room\n3. Cancel Booking\n0. Logout";
	private final BookingService bookingService = new BookingServiceImpl();
	private final HotelService hotelService = new HotelServiceImpl();

	public void showItemMenu(String firstName, ConsoleView invoker) {
		System.out.printf((MESSAGE) + "%n", firstName);
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
		switch (choice) {
			case 0 -> invoker.showItemMenu(this);
			case 1 -> viewRooms(invoker);
			case 2 -> bookRoom(invoker);
		}
	}

	private void bookRoom(ConsoleView invoker) {
		System.out.println("Please, insert FROM date in format yyyy-MM-dd:");
		String fromDate = ConsoleReader.readString();
		System.out.println("Please, insert TO date in format yyyy-MM-dd:");
		String toDate = ConsoleReader.readString();
		System.out.println("Please, select a hotel id:");
		System.out.println(hotelService.getAllHotels());
		int hotelId = ConsoleReader.readInt();
		Hotel hotel = hotelService.getHotelById(hotelId);
		if (hotel != null) {
			System.out.println("Please,select available room number:");
			hotel.getRooms().stream().filter(room -> room.getRoomStatus().equals(RoomStatus.AVAILABLE)).forEach(
				System.out::println);
			int roomNumber = ConsoleReader.readInt();
			Room room = hotel.getRooms().stream().filter(r -> r.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
			Booking booking =
				new Booking(hotel, room, DateParser.parseDateFromString(fromDate), DateParser.parseDateFromString(toDate));
			System.out.println(bookingService.addBooking(booking));
		}
		this.showItemMenu(invoker);
	}

	private void viewRooms(ConsoleView invoker) {
		System.out.println("Please, enter Hotel ID:");
		System.out.println(hotelService.getAllHotels());
		int hotelId = ConsoleReader.readInt();
		System.out.println(hotelService.viewRoomsByHotel(hotelId));
		this.showItemMenu(invoker);
	}

	@Override
	public void showItemMenu(ConsoleView invoker) {
		System.out.println(MESSAGE_WITHOUT_USERNAME);
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
		switch (choice) {
			case 0 -> invoker.showItemMenu(this);
			case 1 -> viewRooms(invoker);
			case 2 -> bookRoom(invoker);
		}
	}
}
