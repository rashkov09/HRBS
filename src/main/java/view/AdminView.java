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
		System.out.println("Please, insert hotel ID:");
		System.out.println(hotelService.getAllHotels());
		int hotelId = ConsoleReader.readInt();
		System.out.println("Room number:");
		int roomNumber = ConsoleReader.readInt();
		int minRoomTypeOption = 1;
		int maxRoomTypeOption = 4;
		System.out.println("Please, select room type:\n1. Single\n2. Double\n3. Deluxe\n4. Suite");
		int roomTypeChoice = ConsoleRangeReader.readInt(minRoomTypeOption, maxRoomTypeOption);
		System.out.println("Please, insert price per night:");
		BigDecimal pricePerNight = ConsoleReader.readBigDecimal();
		System.out.println("Please, insert cancellation price:");
		BigDecimal cancellationPrice = ConsoleReader.readBigDecimal();
		Room room =
			new Room(roomNumber, RoomType.values()[roomTypeChoice - 1], pricePerNight, cancellationPrice,
			         RoomStatus.AVAILABLE);
		System.out.println(hotelService.addRoom(room, hotelId));
		this.showItemMenu(invoker);
	}

	private void listAllHotels(ConsoleView invoker) {
		System.out.println(hotelService.getAllHotels());
		this.showItemMenu(invoker);
	}

	private void addHotel(ConsoleView invoker) {
		System.out.println("Please, insert name:");
		String name = ConsoleReader.readString();
		System.out.println("Please, insert address:");
		String address = ConsoleReader.readString();
		System.out.println("Please, insert email address:");
		String email = ConsoleReader.readString();
		System.out.println("Please, insert phone number:");
		String phone = ConsoleReader.readString();
		Hotel hotel = new Hotel(name, address, email, phone);
		System.out.println(hotelService.addHotel(hotel));
		this.showItemMenu(invoker);
	}
}
