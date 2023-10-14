package model;

import model.enums.RoomStatus;
import model.enums.RoomType;

import java.math.BigDecimal;

public class Room {
	private Integer roomNumber;
	private RoomType roomType;
	private BigDecimal pricePerNight;
	private BigDecimal cancellationFee;
	private RoomStatus roomStatus;

	public Room() {
	}

	public Room(
		Integer roomNumber, RoomType roomType, BigDecimal pricePerNight, BigDecimal cancellationFee,
		RoomStatus roomStatus) {
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.pricePerNight = pricePerNight;
		this.cancellationFee = cancellationFee;
		this.roomStatus = roomStatus;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public BigDecimal getCancellationFee() {
		return cancellationFee;
	}

	public void setCancellationFee(BigDecimal cancellationFee) {
		this.cancellationFee = cancellationFee;
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}
}
