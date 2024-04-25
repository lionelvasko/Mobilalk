package hu.inf.szte.model;

import android.media.tv.TimelineRequest;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Show implements Serializable {

    private String id;
    private final String movie;
    private final Date datetime;
    private ArrayList<Boolean> seats;

    public Show(String id, String movie, Date datetime, ArrayList<Boolean> seats) {
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

    public Show(String name, Date date, ArrayList<Boolean> seatNumber) {
        this.movie = name;
        this.datetime = date;
        this.seats = seatNumber;
    }

    public String getMovie() {
        return movie;
    }

    public Date getDatetime() {
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

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public void setSeats(ArrayList<Boolean> seats) {
        this.seats = new ArrayList<>();
        this.seats.addAll(seats);
    }
}