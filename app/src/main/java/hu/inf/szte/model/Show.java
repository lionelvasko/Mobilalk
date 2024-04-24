package hu.inf.szte.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Show  implements Serializable {

    private String id;
    private final String movie;
    private final Date datetime; // Changed from Timestamp to Date
    private ArrayList<Boolean> seats;

    public Show(String id, String movie, Date datetime, ArrayList<Boolean> seats) { // Changed from Timestamp to Date
        this.id = id;
        this.movie = movie;
        this.datetime = datetime;
        this.seats = seats;
    }

    public Show() {
        this.id = "";
        this.movie = "";
        this.datetime = null;
        this.seats = null;
    }

    public String getMovie() {
        return movie;
    }

    public Date getDatetime() { // Changed from Timestamp to Date
        return datetime;
    }

    public ArrayList<Boolean> getSeats() {
        return seats;
    }

    public void setSeat(int index, boolean value) {
        seats.set(index, value);
    }

    public int getFreeSeats() {
        int freeSeats = 0;
        for (Boolean seat : seats) {
            if (!seat) {
                freeSeats++;
            }
        }
        return freeSeats;
    }

    public int getReservedSeats() {
        return seats.size() - getFreeSeats();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
       this.id = id;
    }

    public void setSeats(ArrayList<Boolean> seats) {
        this.seats = new ArrayList<>();
        this.seats.addAll(seats);
    }
}