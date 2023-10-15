package view;

import model.User;
import model.enums.UserRole;
import service.UserService;
import service.impl.UserServiceImpl;
import util.ConsoleRangeReader;
import util.ConsoleReader;

import static java.lang.System.exit;

public class MainView implements ConsoleView {

	private static final int MIN_MENU_OPTION = 0;
	private static final int MAX_MENU_OPTION = 2;
	private final static String MESSAGE = """
	                                        WELCOME TO Hotel Room Reservation System!
	                                        Please, select an option to continue:
	                                        1. Login
	                                        2. Register
	                                        0. Exit
	                                      """;
	private final UserService userService = new UserServiceImpl();
	private final ConsoleView adminView = new AdminView();
	private final UserView userView = new UserView();

	@Override
	public void showItemMenu(ConsoleView invoker) {
		System.out.println(MESSAGE);
		int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);

		switch (choice) {
			case 0 -> exit(1);
			case 1 -> login();
			case 2 -> register();
		}
	}

	private void register() {
		User user = userService.createUser();
		if (userService.registerUser(user)) {
			userView.showItemMenu(user.getFirstName(), this);
		} else {
			this.showItemMenu(this);
		}
	}

	private void login() {
		System.out.println("Please, insert username:");
		String username = ConsoleReader.readString();
		System.out.println("Please, insert password:");
		String password = ConsoleReader.readString();
		User user = userService.login(username, password);
		if (user != null) {
			switch (user.getUserRole()){
				case USER:	userView.showItemMenu(user.getFirstName(), this);
				case ADMIN: adminView.showItemMenu(this);
			}

		} else {
			System.out.println("Invalid username or password! Please, try again!");
			this.showItemMenu(this);
		}
	}
}