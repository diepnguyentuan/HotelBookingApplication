package com.hotel.model;

import java.time.LocalDateTime;

public class Room {
    private int roomNo;
    private String typeRoom;
    private boolean available;
    private double price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Room(int roomNo, String typeRoom, double price, boolean available) {
        this.roomNo = roomNo;
        this.typeRoom = typeRoom;
        this.price = price;
        this.available = true;
    }

    public int getRoomNo() {
        return roomNo;
    }
    public String getTypeRoom() {
        return typeRoom;
    }
    public double getPrice() {
        return price;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }
    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return String.format("| %-15d | %-15s | %-17s | %-13.2f |%n",
                roomNo, typeRoom, available ? "Yes" : "No", price);
    }
}
