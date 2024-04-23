package hu.inf.szte.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Show  implements Serializable {
    private final String movie;
    private final String datetime;
    private final ArrayList<Boolean> seats;

    public Show(String movie, String datetime, ArrayList<Boolean> seats) {
        this.movie = movie;
        this.datetime = datetime;
        this.seats = seats;
    }

    public Show(){
        this.movie = "";
        this.datetime = "";
        this.seats = new ArrayList<>();
    }

    public String getMovie() {
        return movie;
    }

    public String getDatetime() {
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

}
