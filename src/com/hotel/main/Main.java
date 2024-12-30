package com.hotel.main;

import com.hotel.service.HotelManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HotelManager hotelManager = new HotelManager();


        while (true) {
            while (hotelManager.getCurrentCustomer() == null) {
                System.out.println("Enter your name to login: ");
                String name = sc.nextLine();
                hotelManager.loginCustomer(name);
            }
            System.out.println("1. View Rooms available");
            System.out.println("2. Book Rooms");
            System.out.println("3. Cancel Rooms");
            System.out.println("4. View booked rooms");
            System.out.println("5. Check out");
            System.out.println("6. Log out");
            System.out.println("7. Exit!");
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    hotelManager.showAvailableRooms();
                    break;
                case 2:
                    System.out.println("Enter room number you want to book: ");
                    int roomNumber = sc.nextInt();
                    hotelManager.bookRoom(roomNumber);
                    break;
                case 3:
                    System.out.println("Enter room number you want to cancel: ");
                    int cancelRoomNumber = sc.nextInt();
                    hotelManager.cancelRoom(cancelRoomNumber);
                    break;
                case 4:
                    hotelManager.viewBookedRooms();
                    break;
                case 5:
                    hotelManager.checkoutRoom();
                    break;
                case 6:
                    hotelManager.logout();
                    break;
                case 7:
                    System.out.println("Thank you for using Hotel Booking Application!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}