package model;

import model.enums.UserRole;

public class User {
	private String firstName;
	private String lastName;
	private UserRole userRole;

	private String email;

	private String phone;

	private String username;
	private String password;

	public User() {
	}

	public User(
		String firstName, String lastName, UserRole userRole, String email, String phone, String username,
		String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
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
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
