package model;

import model.enums.RoomStatus;
import model.enums.RoomType;

import java.math.BigDecimal;

import static constants.HotelRoomProperties.DELUXE_ROOM_AMENITIES;
import static constants.HotelRoomProperties.DELUXE_ROOM_MAX_OCCUPANCY;
import static constants.HotelRoomProperties.DOUBLE_ROOM_AMENITIES;
import static constants.HotelRoomProperties.DOUBLE_ROOM_MAX_OCCUPANCY;
import static constants.HotelRoomProperties.SINGLE_ROOM_AMENITIES;
import static constants.HotelRoomProperties.SINGLE_ROOM_MAX_OCCUPANCY;
import static constants.HotelRoomProperties.SUITE_ROOM_AMENITIES;
import static constants.HotelRoomProperties.SUITE_ROOM_MAX_OCCUPANCY;

public class Room {

	private Integer roomNumber;
	private RoomType roomType;
	private BigDecimal pricePerNight;
	private BigDecimal cancellationFee;
	private RoomStatus roomStatus;

	private Integer maxOccupancy;
	private String amenities;

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
		setAmenities();
		setMaxOccupancy();
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

	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}

	private void setMaxOccupancy() {
		switch (roomType) {
			case SINGLE -> this.maxOccupancy = SINGLE_ROOM_MAX_OCCUPANCY;
			case DOUBLE -> this.maxOccupancy = DOUBLE_ROOM_MAX_OCCUPANCY;
			case DELUXE -> this.maxOccupancy = DELUXE_ROOM_MAX_OCCUPANCY;
			case SUITE -> this.maxOccupancy = SUITE_ROOM_MAX_OCCUPANCY;
		}
	}

	public String getAmenities() {
		return amenities;
	}

	private void setAmenities() {
		switch (roomType) {
			case SINGLE -> this.amenities = SINGLE_ROOM_AMENITIES;
			case DOUBLE -> this.amenities = DOUBLE_ROOM_AMENITIES;
			case DELUXE -> this.amenities = DELUXE_ROOM_AMENITIES;
			case SUITE -> this.amenities = SUITE_ROOM_AMENITIES;
		}
	}
}
