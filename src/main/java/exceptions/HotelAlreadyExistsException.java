package exceptions;

public class HotelAlreadyExistsException extends RuntimeException{
	private static final String MESSAGE= "Hotel already exists!";
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
