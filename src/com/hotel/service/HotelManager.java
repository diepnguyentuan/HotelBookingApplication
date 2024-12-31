package com.hotel.service;

import com.hotel.model.Customer;
import com.hotel.model.Room;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class HotelManager {
    private Map<String, Customer> customers;
    private List<Room> rooms;
    private Customer currentCustomer;
    Scanner scanner = new Scanner(System.in);

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
        } else {
            currentCustomer = new Customer(name);
            customers.put(name, currentCustomer);
            System.out.println("Customer is registered: " + currentCustomer.getName());
        }
    }

    public void logout() {
        currentCustomer = null;
        System.out.println("Customer has been logged out");
    }

    public void showAvailableRooms() {
        boolean found = false;
        System.out.format("+-----------------+-----------------+-------------------+-----------------+%n");
        System.out.format("|   Room Number   |    Type Room    |      Available    |      Price      |%n");
        System.out.format("|=========================================================================|%n");

        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.format("| %-15d | %-15s | %-17s | %-10.2f $/day|%n",
                        room.getRoomNo(), room.getTypeRoom(),
                        room.isAvailable() ? "Yes" : "No", room.getPrice());
                System.out.format("+-----------------+-----------------+-------------------+-----------------+%n");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available");
        }
    }

    public void bookRoom(int roomNumber) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Room room : rooms) {
            if (room.getRoomNo() == roomNumber) {
                if (room.isAvailable()) {
                    LocalDate checkinDate = null;
                    while (true) {
                        System.out.println("Please enter date to book room(dd/MM/yyyy): ");
                        String checkinTime = scanner.nextLine();

                        try {
                            checkinDate = LocalDate.parse(checkinTime, dtf);
                            LocalDate today = LocalDate.now();
                            if (checkinDate.isBefore(today)) {
                                System.out.println("Checkin date must not before today");
                                continue;
                            }
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format");
                        }
                    }
                    LocalDateTime checkinDateTime = checkinDate.atStartOfDay();
                    room.setStartTime(checkinDateTime);
                    currentCustomer.bookRoom(roomNumber);
                    room.setAvailable(false);
                    System.out.println("Room " + roomNumber + " booked - in " + dtf.format(checkinDate));
                } else {
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
                    currentCustomer.cancelRoom(roomNumber);
                    System.out.println("Room " + roomNumber + " has been cancelled successfully");
                }
            }
        } else {
            System.out.println("You do not have booked rooms");
        }
    }

    public void viewBookedRooms() {
        List<Integer> bookedRooms = currentCustomer.getBookedRooms();
        if (bookedRooms.isEmpty()) {
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

    public void checkoutRoom(String outputFilePath) {
        List<Integer> listRooms = currentCustomer.getBookedRooms();
        try (BufferedWriter br = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            if (currentCustomer == null) {
                String outputLine = "Please login first";
                System.out.println(outputLine);
                br.write(outputLine);
                br.newLine();
                return;
            }
            double totalCost = 0.0;

            if (listRooms.isEmpty()) {
                System.out.println("No booked rooms");
                return;
            }
            br.write("---------------Checkout information----");
            br.newLine();
            br.write("Name of Customer: " + currentCustomer.getName());
            br.newLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int roomNumber : listRooms) {
                Room room = findRoom(roomNumber);
                if (room != null) {
                    LocalDate checkOutDate;
                    while (true) {
                        System.out.println("Enter check-out date (dd/MM/yyyy):");
                        String checkOutDateString = scanner.nextLine();
                        try {
                            checkOutDate = LocalDate.parse(checkOutDateString, dtf);
                            if (checkOutDate.isBefore(room.getStartTime().toLocalDate()) ||
                                    checkOutDate.isEqual(room.getStartTime().toLocalDate())) {
                                System.out.println("Check-out date must be after check-in date. Please try again.");
                                continue;
                            }
                            break;

                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                        }
                    }
                    LocalDateTime checkOutDateTime = checkOutDate.atStartOfDay();
                    room.setEndTime(checkOutDateTime);
                    Duration duration = Duration.between(room.getStartTime(), room.getEndTime());
                    long days = duration.toDays();
                    double roomCost = room.getPrice() * (days > 0 ? days : 1);
                    String outputLine = "Room " + room.getRoomNo() + " (" + room.getTypeRoom() + ") " + room.getPrice() + "/day: " + roomCost + "$";
                    System.out.println(outputLine);
                    br.write(outputLine);
                    br.newLine();
                    br.write("Check-in date: " + dtf.format(room.getStartTime().toLocalDate()));//In ra LocalDate
                    br.newLine();
                    br.write("Check-out date: " + dtf.format(room.getEndTime().toLocalDate()));//In ra LocalDate
                    br.newLine();
                    totalCost += roomCost;
                    room.setAvailable(true);
                }
            }
            String outputLineTotal = "Total cost: " + totalCost + " $";
            br.write(outputLineTotal);
            br.newLine();
            br.write("--------------------------");
            br.newLine();
            currentCustomer.getBookedRooms().clear();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
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
