package service.impl;

import exceptions.UserAlreadyExistsException;
import model.User;
import model.enums.UserRole;
import repository.UserRepository;
import service.UserService;
import util.ConsoleReader;

import java.util.List;

import static constants.Shared.PREFIX_TEXT;

public class UserServiceImpl implements UserService {

	private static final String ADMIN_PASSWORD = "admin99";
	private static final List<String> params =
		List.of("first name:", "last name", "email address", "phone number", "username", "password");
	private final UserRepository userRepository = new UserRepository();

	@Override
	public User login(String username, String password) {

		return userRepository.validateUser(username, password);
	}

	@Override
	public boolean registerUser(User user) {
		try {
			return userRepository.addUser(user);
		} catch (UserAlreadyExistsException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public User createUser() {
		User user = new User();
		for (int i = 0; i < params.size(); i++) {
			System.out.printf(PREFIX_TEXT + params.get(i) + "\n");
			try {
				String input = ConsoleReader.readString();

				switch (i) {
					case 0 -> user.setFirstName(input);
					case 1 -> user.setLastName(input);
					case 2 -> user.setEmail(input);
					case 3 -> user.setPhone(input);
					case 4 -> {
						userRepository.checkUsernameAvailability(input);
						user.setUsername(input);
					}
					case 5 -> user.setPassword(input);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				i--;
			}
		}
		user.setUserRole(UserRole.USER);
		return user;
	}

	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user);
	}

	@Override
	public String activateAdminUser() {
		System.out.println("Username:");
		String username = ConsoleReader.readString();
		System.out.println("ADMIN password:");
		String password = ConsoleReader.readString();
		if (password.equals(ADMIN_PASSWORD)) {
			User user = userRepository.activateAdminRole(username);
			if (user == null) {
				return "Wrong password or username!";
			}
			if (!user.getUserRole().equals(UserRole.ADMIN)) {
				return "Wrong password or username!";
			}
		}
		return "ADMIN role added successfully!";
	}
}
