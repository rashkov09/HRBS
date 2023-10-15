package view;

import model.Hotel;
import model.Room;
import model.enums.RoomStatus;
import model.enums.RoomType;
import service.HotelService;
import service.impl.HotelServiceImpl;
import util.ConsoleRangeReader;
import util.ConsoleReader;

import java.math.BigDecimal;

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
	                                  8. List users
	                                  9. Remove user
	                                  0. Logout
	                                  """;
	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 9;
	private final HotelService hotelService = new HotelServiceImpl();

	@Override
	public void showItemMenu(ConsoleView invoker) {
		System.out.println(MENU);
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
		switch (choice) {
			case 0 -> invoker.showItemMenu(this);
			case 1 -> addHotel(invoker);
			case 2 -> addRoomToHotel(invoker);
			case 7 -> listAllHotels(invoker);
		}
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
