package view;

import service.BookingService;
import service.HotelService;
import service.impl.BookingServiceImpl;
import service.impl.HotelServiceImpl;
import util.ConsoleRangeReader;

public class AdminView implements ConsoleView {

	public static final String MENU = """
                                      Please, select and option to continue:
                                        1. Add Hotel
                                        2. Add Room to hotel
                                        3. Edit Hotel
                                        4. Edit Room in hotel
                                        5. Remove Hotel
                                        6. Remove Room in hotel
                                        7. List Hotels
                                        8. List all bookings
                                        9. Financial report per hotel
	                                      
                                        0. Logout
                                    """;
	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 9;
	private final HotelService hotelService = new HotelServiceImpl();
	private final BookingService bookingService = new BookingServiceImpl();

	@Override
	public void showItemMenu(ConsoleView invoker) {
		System.out.println(MENU);
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
		switch (choice) {
			case 0 -> invoker.showItemMenu(this);
			case 1 -> addHotel(invoker);
			case 2 -> addRoomToHotel(invoker);
			case 3 -> editHotel(invoker);
			case 4 -> editRoomIInHotel(invoker);
			case 5 -> removeHotel(invoker);
			case 6 -> removeRoomFromHotel(invoker);
			case 7 -> listAllHotels(invoker);
			case 8 -> listAllBookings(invoker);
			case 9 -> printHotelProfitReport(invoker);
		}
	}

	private void printHotelProfitReport(ConsoleView invoker) {
		System.out.println(bookingService.printFinancialReportPerHotel());
		this.showItemMenu(invoker);
	}

	private void listAllBookings(ConsoleView invoker) {
		System.out.println(bookingService.getAllBookings());
		this.showItemMenu(invoker);
	}

	private void editRoomIInHotel(ConsoleView invoker) {
		System.out.println(hotelService.editRoomInHotel());
		this.showItemMenu(invoker);
	}

	private void editHotel(ConsoleView invoker) {
		System.out.println(hotelService.editHotel());
		this.showItemMenu(invoker);
	}

	private void removeHotel(ConsoleView invoker) {
		System.out.println(hotelService.removeHotel());
		this.showItemMenu(invoker);
	}

	private void removeRoomFromHotel(ConsoleView invoker) {
		System.out.println(hotelService.removeRoom());
		this.showItemMenu(invoker);
	}

	private void addRoomToHotel(ConsoleView invoker) {
		System.out.println(hotelService.addRoom());
		this.showItemMenu(invoker);
	}

	private void listAllHotels(ConsoleView invoker) {
		System.out.println(hotelService.getAllHotels());
		this.showItemMenu(invoker);
	}

	private void addHotel(ConsoleView invoker) {
		System.out.println(hotelService.addHotel());
		this.showItemMenu(invoker);
	}
}
