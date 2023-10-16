package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Booking {
	private Integer id;
	private Hotel hotel;
	private Room room;

	private LocalDate fromDate;
	private LocalDate toDate;

	public Booking() {
	}

	public Booking(Hotel hotel, Room room, LocalDate fromDate, LocalDate toDate) {
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

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal calculateProfit(){
		long days = ChronoUnit.DAYS.between(fromDate,toDate);
		return this.room.getPricePerNight().multiply(BigDecimal.valueOf(days));
	}
}
