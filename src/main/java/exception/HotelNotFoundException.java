package exception;

public class HotelNotFoundException extends RuntimeException{
	private final static String MESSAGE = "Hotel with id %d not found!";
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
