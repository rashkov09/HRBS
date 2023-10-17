package exception;

public class BookingNotFoundException extends RuntimeException{
	private static final String MESSAGE = "Booking with id %d not found!";
	public BookingNotFoundException(Integer id) {
		super(String.format(MESSAGE,id));
	}
}
