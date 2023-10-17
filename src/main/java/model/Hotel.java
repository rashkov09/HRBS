package model;

import exception.InvalidUserInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static constant.Shared.EMAIL_PATTERN;
import static constant.Shared.PHONE_PATTERN;

public class Hotel {
	private static final String HOTEL_NAME_PATTERN = "^[A-Za-z0-9\\s-]+";
	private static final String HOTEL_ADDRESS_PATTERN = "^[A-Za-z0-9\\s.-]{7,}";
	private Integer id;

	private String name;
	private String address;
	private String email;
	private String phone;
	private final List<Room> rooms = new ArrayList<>();

	public Hotel() {
	}

	public Hotel(String name, String address, String email, String phone) {
		setName(name);
		setAddress(address);
		setEmail(email);
		setPhone(phone);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!Pattern.matches(HOTEL_NAME_PATTERN,name)){
			throw new InvalidUserInputException("hotel name");
		}
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (!Pattern.matches(HOTEL_ADDRESS_PATTERN,address)){
			throw new InvalidUserInputException("hotel address");
		}
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (!Pattern.matches(EMAIL_PATTERN,email)){
			throw new InvalidUserInputException("hotel email");
		}
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (!Pattern.matches(PHONE_PATTERN,phone)){
			throw new InvalidUserInputException("hotel phone");
		}
		this.phone = phone;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.address.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Hotel hotel = (Hotel) obj;
		return this.name.equals(hotel.name) && this.address.equals(hotel.address);
	}

	@Override
	public String toString() {

		return String.format("Hotel ID: %d\nHotel name: %s\nAddress: %s\nEmail: %s\nPhone: %s\nNumber of rooms: %d\n",
		                    this.id, this.name,this.address,this.email,this.phone,this.rooms.size());
	}



}
