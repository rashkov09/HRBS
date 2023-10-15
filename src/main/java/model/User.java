package model;

import exceptions.InvalidUserInputException;
import model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static constants.Shared.EMAIL_PATTERN;
import static constants.Shared.PHONE_PATTERN;

public class User {
	private final static String PASSWORD_ADDITION = " Password should be at least 5 characters!";
	private static final String USERNAME_PATTERN ="^[A-Za-z0-9_-]{5,}$";
	private static final String NAME_PATTERN = "^[A-Z][a-z]+$";
	private static final String PASSWORD_PATTERN = "^.{5,}$";
	private String firstName;
	private String lastName;
	private UserRole userRole;

	private String email;

	private String phone;

	private String username;
	private String password;

	private List<Booking> bookings;

	public User() {
	}

	public User(
		String firstName, String lastName, UserRole userRole, String email, String phone, String username,
		String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setUserRole(userRole);
		setEmail(email);
		setPhone(phone);
		setUsername(username);
		setPassword(password);
		this.bookings = new ArrayList<>();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (!Pattern.matches(NAME_PATTERN,firstName)) {
		 throw new InvalidUserInputException("first name");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (!Pattern.matches(NAME_PATTERN,lastName)) {
			throw new InvalidUserInputException("last name");
		}
		this.lastName = lastName;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (!Pattern.matches(EMAIL_PATTERN,email)) {
			throw new InvalidUserInputException("email");
		}
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (!Pattern.matches(PHONE_PATTERN,phone)) {
			throw new InvalidUserInputException("phone");
		}
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (!Pattern.matches(USERNAME_PATTERN,username)) {
			throw new InvalidUserInputException("username");
		}
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (!Pattern.matches(PASSWORD_PATTERN,password)) {
			throw new InvalidUserInputException("password",PASSWORD_ADDITION);
		}
		this.password = password;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User user = (User) obj;
		return this.username.equals(user.username);
	}
}
