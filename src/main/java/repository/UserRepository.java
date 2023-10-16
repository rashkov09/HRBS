package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.UserAlreadyExistsException;
import model.User;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import util.GsonFactory;

public class UserRepository {

	private static final String jsonFilePath = "src/main/resources/users.json";
	private final Gson gson = GsonFactory.getInstance();

	public User validateUser(String username, String password) {
		String enteredPasswordHash = DigestUtils.sha256Hex(password);
		return getAllUsers().stream()
		                    .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(enteredPasswordHash))
		                    .findFirst().orElse(null);
	}

	private List<User> getAllUsers() {
		List<User> users;
		try (Reader reader = new FileReader(jsonFilePath)) {
			Type userListType = new TypeToken<List<User>>() {
			}.getType();
			users = gson.fromJson(reader, userListType);
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return users != null ? users : new ArrayList<>();
	}

	public boolean addUser(User user) {
		List<User> users = getAllUsers();
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		users.add(user);
		String jsonData = gson.toJson(users);
		try (FileWriter writer = new FileWriter(jsonFilePath)) {
			writer.write(jsonData);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void checkUsernameAvailability(String input) {
		List<User> users = getAllUsers();
		User user = new User();
		user.setUsername(input);
		if (users.contains(user)){
			throw new UserAlreadyExistsException();
		}
	}
}
