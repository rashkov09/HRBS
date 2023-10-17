package exception;

public class InvalidUserInputException extends RuntimeException {

	private final static String MESSAGE = "Invalid %s input! Please, insert valid %s to continue!";


	public InvalidUserInputException(String param) {
		super(String.format(MESSAGE, param, param));
	}
	public InvalidUserInputException(String param, String passwordRequirement) {
		super(String.format(MESSAGE, param, param) + passwordRequirement);
	}
}
