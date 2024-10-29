/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jasper
 */

public class DoubleRoom extends Reservation {

    public DoubleRoom(String guestName, String roomID) {
        super(guestName, roomID);
    }

    @Override
    public void checkIn() {
        isCheckedIn = true;
        System.out.println("\nDouble room " + roomID + " checked in for guest " + guestName + ".");
    }

    @Override
    public void checkOut() {
        isCheckedIn = false;
        System.out.println("Double room " + roomID + " checked out for guest " + guestName + ".\n");
    }

    @Override
    public String getDetails() {
        String format = "| %-15s | %-8s | %-10s |%n"; // Table row format
        StringBuilder details = new StringBuilder();

        details.append(String.format("\n+-----------------+----------+------------+%n"));
        details.append(String.format("| Guest Name      | Room ID  | Checked In |%n"));
        details.append(String.format("+-----------------+----------+------------+%n"));

        details.append(String.format(format, guestName, roomID, isCheckedIn ? "Yes" : "No"));
        details.append(String.format("+-----------------+----------+------------+%n"));

        return details.toString(); 
    }

}
