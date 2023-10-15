package model;

import java.util.Date;

public class Booking {
	private Integer id;
	private Hotel hotel;
	private Room room;

	private Date fromDate;
	private Date toDate;

	public Booking() {
	}

	public Booking(Hotel hotel, Room room, Date fromDate, Date toDate) {
		this.hotel = hotel;
		this.room = room;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}


	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
