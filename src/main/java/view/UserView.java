package view;

import model.User;
import util.ConsoleRangeReader;

public class UserView implements ConsoleView {

	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 3;
	private static final String MESSAGE =
		"Welcome %s! Please, select an option to continue:\n1. View Rooms\n2. Book a Room\n3. Cancel Booking\n0. Logout";

	@Override
	public void showItemMenu(ConsoleView invoker) {

	}

	public void showItemMenu(String firstName, ConsoleView invoker) {
		System.out.printf((MESSAGE) + "%n", firstName);
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION,MAX_MENU_OPTION);
		switch (choice){
			case 0 : invoker.showItemMenu(this);
			case 1 :  break;
		}
	}
}
