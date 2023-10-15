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
		if (bookingRepository.addBooking(booking)){
			hotelService.bookRoom(booking.getHotel().getId(), booking.getRoom().getRoomNumber());
			return "Booking added successfully!";
		}
		return "Booking failed!";
	}
}
