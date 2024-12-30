package com.hotel.model;

public class Room {
    private int roomNo;
    private String typeRoom;
    private boolean available;
    private double price;

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
    @Override
    public String toString() {
        return "roomNo=" + roomNo + ", typeRoom=" + typeRoom + ", available=" + available + ", price=" + price;
    }
}
