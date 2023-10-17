package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {

	private Integer id;
	private Hotel hotel;
	private Room room;

	private LocalDate fromDate;
	private LocalDate toDate;

	private Boolean isCanceled;

	public Booking() {
	}

	public Booking(Hotel hotel, Room room, LocalDate fromDate, LocalDate toDate) {
		this.hotel = hotel;
		this.room = room;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.isCanceled = false;
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

	public Boolean getCanceled() {
		return isCanceled;
	}

	public void setCanceled(Boolean canceled) {
		isCanceled = canceled;
	}

	public BigDecimal calculateProfit() {
		if (this.getCanceled().equals(true)){
			return this.getRoom().getCancellationFee();
		}
		long days = ChronoUnit.DAYS.between(fromDate, toDate);
		return this.room.getPricePerNight().multiply(BigDecimal.valueOf(days));
	}

	@Override
	public String toString() {
		return String.format("""
                         ------------------------------
                         Booking ID: %d
                         Hotel name: %s
                         Room number: %d
                         From: %s
                         To: %s
                         Total price: %.2f
                         Cancellation fee: %.2f
                         Is canceled: %s
                         ------------------------------
                         """, this.getId(), this.getHotel().getName(), this.getRoom().getRoomNumber(),
		                     this.getFromDate().toString(), this.getToDate().toString(), this.calculateProfit(),
		                     this.getRoom().getCancellationFee(),this.getCanceled().toString());
	}
}
