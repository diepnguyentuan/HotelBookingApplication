package com.hotel.service;

import com.hotel.model.Customer;
import com.hotel.model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelManager {
    private Map<String, Customer> customers;
    private List<Room> rooms;
    private Customer currentCustomer;

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
    public HotelManager() {
        customers = new HashMap<>();
        rooms = new ArrayList<>();

        rooms.add(new Room(101, "Single", 100.0, true));
        rooms.add(new Room(102, "Double", 120.0, true));
        rooms.add(new Room(103, "Double", 120.0, true));
        rooms.add(new Room(104, "Single", 100.0, true));
        rooms.add(new Room(105, "Double", 120.0, true));

        customers.put("diep", new Customer("diep"));
    }
    public void loginCustomer(String name) {
        if (customers.containsKey(name)) {
            currentCustomer = customers.get(name);
            System.out.println("Customer is already registered: " + currentCustomer.getName());
        }else{
            currentCustomer = new Customer(name);
            customers.put(name, currentCustomer);
            System.out.println("Customer is registered: " + currentCustomer.getName());
        }
    }
    public void logout(){
        currentCustomer = null;
        System.out.println("Customer has been logged out");
    }
    public void showAvailableRooms() {
        boolean found = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available");
        }
    }
    public void bookRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNo() == roomNumber) {
                if (room.isAvailable()) {
                    room.setAvailable(false);
                    currentCustomer.bookRoom(roomNumber);
                    System.out.println("Room " + roomNumber + " booked");
                }else {
                    System.out.println("Room " + roomNumber + " not booked");
                }
                return;
            }
        }
        System.out.println("Room " + roomNumber + " does not exist");
    }
    public void cancelRoom(int roomNumber) {
        if (currentCustomer.getBookedRooms().contains(roomNumber)) {
            for (Room room : rooms) {
                if (room.getRoomNo() == roomNumber) {
                    room.setAvailable(true);
                    currentCustomer.bookRoom(roomNumber);
                    System.out.println("Room " + roomNumber + " has been cancelled successfully");
                }
            }
        }else {
            System.out.println("You do not have booked rooms");
        }
    }
    public void viewBookedRooms() {
        List<Integer> bookedRooms = currentCustomer.getBookedRooms();
        if(bookedRooms.isEmpty()) {
            System.out.println("No booked rooms");
        } else {
            System.out.println("Your booked rooms are: ");
            for (Integer bookedRoom : bookedRooms) {
                for (Room room : rooms) {
                    if (room.getRoomNo() == bookedRoom) {
                        System.out.println(room);
                    }
                }
            }
        }
    }
    public void checkoutRoom() {
        if (currentCustomer == null) {
            System.out.println("Please login first");
        }
        double totalCost = 0.0;
        List<Integer> listRooms = currentCustomer.getBookedRooms();
        if(listRooms.isEmpty()) {
            System.out.println("No booked rooms");
        }
        for(int roomNumber : listRooms){
            Room room = findRoom(roomNumber);
            if (room != null) {
                System.out.println("Room " + room.getRoomNo() + " (" + room.getTypeRoom() + ") " + room.getPrice());
                totalCost += room.getPrice();
                room.setAvailable(true);
            }
        }
        System.out.println("Total cost: " + totalCost);
        currentCustomer.getBookedRooms().clear();
    }
    private Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNo() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}
