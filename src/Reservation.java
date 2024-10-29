/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jasper
 */

public abstract class Reservation {
    protected String guestName;
    protected String roomID;  
    protected boolean isCheckedIn;

    public Reservation(String guestName, String roomID) {
        this.guestName = guestName;
        this.roomID = roomID;
        this.isCheckedIn = false;
    }

    public abstract void checkIn();

    public abstract void checkOut();

    public abstract String getDetails();
}
