package exception;

public class UserAlreadyExistsException extends RuntimeException{
	private static final String MESSAGE = "User with this username already exists!";
	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
