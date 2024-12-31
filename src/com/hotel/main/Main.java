package com.hotel.main;

import com.hotel.service.HotelManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HotelManager hotelManager = new HotelManager();
        while (true) {
            while (hotelManager.getCurrentCustomer() == null) {
                System.out.println("Enter your name to start: ");
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
            int choice = 0;
            while (true){
                System.out.println("Enter your choice: ");
                try{
                    choice = sc.nextInt();
                    if(choice < 1 || choice > 7){
                        System.out.println("Invalid choice");
                        continue;
                    }
                    break;
                }catch (InputMismatchException e){
                    System.out.println("Invalid input, enter a number between 1 and 7");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    hotelManager.showAvailableRooms();
                    break;
                case 2:
                    while (true){
                        try{
                            System.out.println("Enter room number you want to book: ");
                            int roomNumber = sc.nextInt();
                            hotelManager.bookRoom(roomNumber);
                            break;
                        }catch (InputMismatchException e){
                            System.out.println("please enter a number");
                            sc.nextLine();
                        }
                    }
                case 3:
                    hotelManager.viewBookedRooms();
                    if (hotelManager.getCurrentCustomer().getBookedRooms().isEmpty()) {
                        System.out.println("No room to cancel");
                        break;
                    }else {
                        while (true){
                            try {
                                System.out.println("Enter room number you want to cancel: ");
                                int cancelRoomNumber = sc.nextInt();
                                hotelManager.cancelRoom(cancelRoomNumber);
                                break;
                            }catch (InputMismatchException e) {
                                System.out.println("Please enter a valid number");
                                sc.nextLine();
                            }
                        }

                    }
                case 4:
                    hotelManager.viewBookedRooms();
                    break;
                case 5:
                    String outputFilePath = "checkout_log.txt";
                    hotelManager.checkoutRoom(outputFilePath);
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