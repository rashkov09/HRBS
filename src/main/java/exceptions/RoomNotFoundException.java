package exceptions;

public class RoomNotFoundException extends RuntimeException{

	private static final String MESSAGE= "Room not found!";
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
