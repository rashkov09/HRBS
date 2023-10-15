package service.impl;

import model.Booking;
import repository.BookingRepository;
import service.BookingService;
import service.HotelService;

public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository = new BookingRepository();
	private final HotelService hotelService = new HotelServiceImpl();

	@Override
	public String addBooking(Booking booking) {
		String result= "Failed";
		if (bookingRepository.addBooking(booking)) {
			try {
				hotelService.bookRoom(booking.getHotel().getId(), booking.getRoom().getRoomNumber());
				result = "Booking added successfully!";
			} catch (Exception e) {
				result = e.getMessage();
			}
		}
		return result;
	}
}
