package service.impl;

import model.User;
import repository.UserRepository;
import service.UserService;

public class UserServiceImpl implements UserService {
	private final UserRepository userRepository = new UserRepository();
	@Override
	public User login(String username, String password) {

		return userRepository.validateUser(username,password);
	}

	@Override
	public boolean registerUser(User user) {
		return userRepository.addUser(user);
	}
}
