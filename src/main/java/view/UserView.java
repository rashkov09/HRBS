package view;

import model.User;
import service.BookingService;
import service.HotelService;
import service.impl.BookingServiceImpl;
import service.impl.HotelServiceImpl;
import util.ConsoleRangeReader;
import util.ConsoleReader;

import static constant.Shared.HOTEL_ID;
import static constant.Shared.PREFIX_TEXT;

public class UserView implements ConsoleView {

	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 4;
	private static final String MESSAGE = """
                                          Welcome %s! Please, select an option to continue:
                                            1. View Rooms
                                            2. Book a Room
                                            3. Cancel Booking
                                            4. View profile
	                                          
                                            0. Logout
                                        """;
	private static final String MESSAGE_WITHOUT_USERNAME = """
                                                           Please, select an option to continue:
                                                            1. View Rooms
                                                            2. Book a Room
                                                            3. Cancel Booking
                                                            4. View profile
	                                                           	  	                                                       
                                                            0. Logout
                                                         """;
	private static User currentUser = null;
	private final BookingService bookingService = new BookingServiceImpl();
	private final HotelService hotelService = new HotelServiceImpl();

	public void showItemMenu(User user, ConsoleView invoker) {
		currentUser = user;
		System.out.printf((MESSAGE) + "%n", currentUser.getFirstName());
		getCommand(invoker);
	}

	private void getCommand(ConsoleView invoker) {
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
		switch (choice) {
			case 0 -> invoker.showItemMenu(this);
			case 1 -> viewRooms(invoker);
			case 2 -> bookRoom(invoker);
			case 3 -> cancelBooking(invoker);
			case 4 -> viewUserProfile(invoker);
		}
	}

	private void cancelBooking(ConsoleView invoker) {
		System.out.println(bookingService.cancelBooking(currentUser));
		this.showItemMenu(invoker);
	}

	private void viewUserProfile(ConsoleView invoker) {
		System.out.println(currentUser.toString());
		System.out.println("0. Back");
		int choice = ConsoleReader.readInt();
		while (choice!=0){
			 choice = ConsoleReader.readInt();
		}
		this.showItemMenu(invoker);
	}

	private void bookRoom(ConsoleView invoker) {
		System.out.println(bookingService.addBooking(currentUser));
		this.showItemMenu(invoker);
	}

	private void viewRooms(ConsoleView invoker) {
		System.out.println(PREFIX_TEXT + HOTEL_ID);
		System.out.println(hotelService.getAllHotels());
		int hotelId = ConsoleReader.readInt();
		System.out.println(hotelService.viewRoomsByHotel(hotelId));
		this.showItemMenu(invoker);
	}

	@Override
	public void showItemMenu(ConsoleView invoker) {
		System.out.println(MESSAGE_WITHOUT_USERNAME);
		getCommand(invoker);
	}
}
