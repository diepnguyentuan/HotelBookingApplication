package com.hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Integer> bookedRooms;

    public Customer() {}
    public Customer(String name) {
        this.name = name;
        this.bookedRooms = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public List<Integer> getBookedRooms() {
        return bookedRooms;
    }
    public void bookRoom(int room) {
        bookedRooms.add(room);
    }
    public void cancelRoom(int room) {
        bookedRooms.remove((Integer) room);
    }
}
