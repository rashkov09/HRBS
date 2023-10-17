package service;

import model.User;

public interface BookingService {

	String addBooking(User user);

	String cancelBooking(User currentUser);

	String getAllBookings();

	String printFinancialReportPerHotel();
}
