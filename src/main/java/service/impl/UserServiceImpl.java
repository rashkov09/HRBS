package service.impl;

import exceptions.UserAlreadyExistsException;
import model.User;
import model.enums.UserRole;
import repository.UserRepository;
import service.UserService;
import util.ConsoleReader;

import java.util.List;

public class UserServiceImpl implements UserService {
	private final UserRepository userRepository = new UserRepository();
	@Override
	public User login(String username, String password) {

		return userRepository.validateUser(username,password);
	}

	@Override
	public boolean registerUser(User user) {
		try {
			return userRepository.addUser(user);
		}catch (UserAlreadyExistsException e){
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public User createUser() {
		List<String> params = List.of("first name:", "last name", "email address","phone number", "username", "password");
		User user = new User();
		for (int i = 0; i < params.size(); i++) {
			System.out.printf("Please, insert " + params.get(i) + "\n");
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
			}catch (Exception e ){
				System.out.println(e.getMessage());
				i--;
			}
		}
		return user;
	}
}
