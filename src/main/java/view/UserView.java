package view;

import model.Booking;
import model.Hotel;
import model.Room;
import model.User;
import model.enums.RoomStatus;
import service.BookingService;
import service.HotelService;
import service.impl.BookingServiceImpl;
import service.impl.HotelServiceImpl;
import util.ConsoleRangeReader;
import util.ConsoleReader;
import util.DateParser;

import static constants.Shared.HOTEL_ID;
import static constants.Shared.PREFIX_TEXT;

public class UserView implements ConsoleView {
	private static User currentUser = null;
	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 2;
	private static final String MESSAGE =
		"Welcome %s! Please, select an option to continue:\n1. View Rooms\n2. Book a Room\n3. Cancel Booking\n0. Logout";
	private static final String MESSAGE_WITHOUT_USERNAME =
		"Please, select an option to continue:\n1. View Rooms\n2. Book a Room\n3. Cancel Booking\n0. Logout";
	private final BookingService bookingService = new BookingServiceImpl();
	private final HotelService hotelService = new HotelServiceImpl();

	public void showItemMenu(User user, ConsoleView invoker) {
		currentUser = user;
		System.out.printf((MESSAGE) + "%n", currentUser.getFirstName());
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
		switch (choice) {
			case 0 -> invoker.showItemMenu(this);
			case 1 -> viewRooms(invoker);
			case 2 -> bookRoom(invoker);
		}
	}

	private void bookRoom(ConsoleView invoker) {
		System.out.println(bookingService.addBooking(currentUser));
		this.showItemMenu(invoker);
	}

	private void viewRooms(ConsoleView invoker) {
		System.out.println(PREFIX_TEXT+HOTEL_ID);
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
