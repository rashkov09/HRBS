package model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
	private Integer id;

	private String name;
	private String address;
	private String email;
	private String phone;
	private List<Room> rooms;

	public Hotel() {
	}

	public Hotel(String name, String address, String email, String phone) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.rooms = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<Room> getRooms() {
		return rooms;
	}

	public Boolean addRoom(Room room) {
		return this.rooms.add(room);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {

		return String.format("Hotel ID: %d\nHotel name: %s\nAddress: %s\nEmail: %s\nPhone: %s\nNumber of rooms: %d\n",
		                    this.id, this.name,this.address,this.email,this.phone,this.rooms.size());
	}

}
