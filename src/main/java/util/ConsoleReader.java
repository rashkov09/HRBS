package util;

import java.math.BigDecimal;
import java.util.Scanner;

public final class ConsoleReader {

	private static final String INVALID_INT = "%s is not an int. Try again: ";
	private static final Scanner scanner = new Scanner(System.in);

	private ConsoleReader() {
		throw new UnsupportedOperationException();
	}

	public static String readString() {
		return scanner.nextLine();
	}

	public static int readInt() {
		while (!scanner.hasNextInt()) {
			String input = scanner.next();
			System.out.printf(INVALID_INT, input);
		}
		int input = scanner.nextInt();
		scanner.nextLine();
		return input;
	}
	public static BigDecimal readBigDecimal() {
		while (!scanner.hasNextBigDecimal()) {
			String input = scanner.next();
			System.out.printf(INVALID_INT, input);
		}
		BigDecimal input = scanner.nextBigDecimal();
		scanner.nextLine();
		return input;
	}
}