package service;

import model.User;

public interface UserService {

	User login(String username, String password);

	boolean registerUser(User user);

	User createUser();
}
