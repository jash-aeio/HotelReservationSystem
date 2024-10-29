/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Jasper
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class HotelReservationSystem {

    private static String[] singleRooms = {"S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08", "S09", "S10"};
    private static String[] doubleRooms = {"D01", "D02", "D03", "D04", "D05", "D06", "D07", "D08", "D09", "D10"};
    private static Reservation[] reservations = new Reservation[20];  // Max 20 reservations
    private static int reservationCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean menu = true;

        while (menu) {

            System.out.println("============================");
            System.out.println("  HOTEL RESERVATION SYSTEM");
            System.out.println("============================\n");
            System.out.println("         Main Menu\n");
            System.out.println("   Check In           [1]");
            System.out.println("   Check Out          [2]");
            System.out.println("   View Reservations  [3]");
            System.out.println("   Exit               [0]");
            System.out.print("\nEnter your choice: ");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        checkIn(scanner);
                        break;
                    case 2:
                        String roomID = checkOut(scanner);
                         if (roomID != null) {
                            updateRoomArray(roomID);
                        }
                        break;
                    case 3:
                        viewReservationDetails(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        menu = false;
                        scanner.close();
                        break;
                    default:
                        System.out.println("ERROR: Input is invalid.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input is invalid.\n");
                scanner.next();
            }
        }
    }

    private static void checkIn(Scanner scanner) {
        if (reservationCount >= reservations.length) {
            System.out.println("Error: Reservation limit reached.");
            return;
        }

        System.out.println("\n============================");
        System.out.println("         Check In");
        System.out.println("============================\n");
        System.out.println("      Single Room   [1]");
        System.out.println("      Double Room   [2]");
        System.out.print("\nEnter room type: ");
        int roomType = getValidIntInput(scanner, 1, 2, "ERROR: Input is invalid. Try again: ");

        displayAvailableRooms(roomType);

        String roomID = getValidRoomID(scanner, roomType);
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        if (roomType == 1) {
            markRoomAsReserved(singleRooms, roomID);
            reservations[reservationCount] = new SingleRoom(guestName, roomID);
        } else {
            markRoomAsReserved(doubleRooms, roomID);
            reservations[reservationCount] = new DoubleRoom(guestName, roomID);
        }

        reservations[reservationCount].checkIn();
        reservationCount++;
        System.out.println("Room " + roomID + " reserved and checked in successfully.");       
        displayAvailableRooms(roomType);
    }

    private static String checkOut(Scanner scanner) {
        if (reservationCount == 0) {
            System.out.println("No reservations available.\n");
            return null;
        }

        // Display reserved rooms with their index
        System.out.println("\n============================");
        System.out.println("          Check Out");
        System.out.println("============================\n");
        System.out.println("Index\tRoom ID");
        List<Integer> availableIndices = new ArrayList<>();
        for (int i = 0; i < reservationCount; i++) {
            if (reservations[i].isCheckedIn) { 
                System.out.println(availableIndices.size() + "\t" + reservations[i].roomID);
                availableIndices.add(i); 
            }
        }

        if (availableIndices.isEmpty()) {
            System.out.println("No rooms are currently checked in.");
            return null;
        }
        System.out.print("\nEnter Reservation Index: ");

        int index = getValidIntInput(scanner, 0, availableIndices.size() - 1, "ERROR: Enter a valid reservation index: ");
        int actualIndex = availableIndices.get(index); 
        
        String roomID = reservations[actualIndex].roomID;
        reservations[actualIndex].checkOut();
        removeReservation(actualIndex);
        return roomID;
    }

    private static void removeReservation(int index) {
        for (int i = index; i < reservationCount - 1; i++) {
            reservations[i] = reservations[i + 1]; 
        }
        reservations[--reservationCount] = null;
    }

    private static void viewReservationDetails(Scanner scanner) {
        if (reservationCount == 0) {
            System.out.println("No reservations available.\n");
            return;
        }

        System.out.println("\n============================");
        System.out.println("  View Reservation Details");
        System.out.println("============================\n");
        System.out.println("Index\tRoom ID");
        List<Integer> availableIndices = new ArrayList<>();
        for (int i = 0; i < reservationCount; i++) {
            if (reservations[i].isCheckedIn) { 
                System.out.println(availableIndices.size() + "\t" + reservations[i].roomID);
                availableIndices.add(i); 
            }
        }

        if (availableIndices.isEmpty()) {
            System.out.println("No rooms are currently checked in.");
            return;
        }
        System.out.print("\nEnter Reservation Index: ");
        int index = getValidIntInput(scanner, 0, availableIndices.size() - 1, "ERROR: Enter a valid reservation index: ");
        int actualIndex = availableIndices.get(index); 

        System.out.println(reservations[actualIndex].getDetails());
    }

    private static int getValidIntInput(Scanner scanner, int min, int max, String errorMessage) {
        int input = -1;
        boolean valid = false;
        while (!valid) {
            try {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
                scanner.next(); 
            }
        }
        scanner.nextLine(); 
        return input;
    }

    private static void displayAvailableRooms(int roomType) {
        if (roomType == 1) {
            System.out.println("\nAvailable Single Rooms:");
            for (String room : singleRooms) {
                System.out.print("| " + room + " ");
            }
            System.out.println("|");
        } else {
            System.out.println("Available Double Rooms:");
            for (String room : doubleRooms) {
                System.out.print("| " + room + " ");
            }
            System.out.println("|");
        }
        System.out.println(); 
    }

    private static String getValidRoomID(Scanner scanner, int roomType) {
        String roomID = "";
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter room ID: ");
            roomID = scanner.nextLine().toUpperCase();

            if (roomType == 1 && isRoomAvailable(singleRooms, roomID)) {
                valid = true;
            } else if (roomType == 2 && isRoomAvailable(doubleRooms, roomID)) {
                valid = true;
            } else {
                System.out.println("ERROR: The room is either reserved or invalid.\n");
            }
        }
        return roomID;
    }

    private static boolean isRoomAvailable(String[] rooms, String roomID) {
        for (String room : rooms) {
            if (room.equals(roomID)) {
                return true;
            }
        }
        return false;
    }

    private static void markRoomAsReserved(String[] rooms, String roomID) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].equals(roomID)) {
                rooms[i] = "RRR";
                break;
            }
        }
    }

    private static void updateRoomArray(String roomID) {
        if (roomID.startsWith("S")) { // Check if it's a single room
            for (int i = 0; i < singleRooms.length; i++) {
                if (singleRooms[i].equals("RRR")) {
                    singleRooms[i] = roomID;
                    break;
                }
            }
        } else {
            for (int i = 0; i < doubleRooms.length; i++) {
                if (doubleRooms[i].equals("RRR")) {
                    doubleRooms[i] = roomID;
                    break;
                }
            }
        }
    }

}
