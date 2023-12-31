package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.BookingNotFoundException;
import model.Booking;
import model.enums.RoomStatus;
import util.GsonFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
	private static final String jsonFilePath = "src/main/resources/booking.json";
	private final Gson gson = GsonFactory.getInstance();

	public boolean addBooking(Booking booking) {
			List<Booking> bookings = getAllBookings();
			booking.setId(bookings.size()+1);
			bookings.add(booking);
			String jsonData = gson.toJson(bookings);
			return save(jsonData);
		}

	public List<Booking> getAllBookings() {
		List<Booking> bookings;
		try (Reader reader = new FileReader(jsonFilePath)) {
			Type bookingListType = new TypeToken<List<Booking>>() {
			}.getType();
			bookings = gson.fromJson(reader, bookingListType);
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return bookings != null ? bookings : new ArrayList<>();
	}

	private boolean save(String data){
		try (FileWriter writer = new FileWriter(jsonFilePath)) {
			writer.write(data);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkRoomAvailability(Integer roomNumber) {
		List<Booking> bookings = getAllBookings();
		for (Booking booking:bookings){
			if(booking.getRoom().getRoomNumber().equals(roomNumber) && booking.getRoom().getRoomStatus().equals(RoomStatus.BOOKED)){
				return false;
			}
		}
		return true;
	}

	public Booking getBookingById(int bookingId) {
			List<Booking> bookings =getAllBookings();
			Booking booking = bookings.stream().filter(b-> b.getId().equals(bookingId)).findFirst().orElse(null);
			if (booking == null){
				throw new BookingNotFoundException(bookingId);
			}
			return booking;
	}

	public void updateBooking(Booking booking) {
		List<Booking> bookings = getAllBookings();
		bookings.stream().filter(b->b.getId().equals(booking.getId())).findFirst().ifPresent(b -> b.setCanceled(true));
		String jsonData=  gson.toJson(bookings);
		save(jsonData);
	}
}
