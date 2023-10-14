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
}
