package hu.inf.szte.model;
import com.google.type.Date;
import java.util.ArrayList;

public class Ticket {
    public Date date;
    public String movie;
    public ArrayList<Long> seats;

    public Ticket(Date date, String movie, ArrayList<Long> seats) {
        this.date = date;
        this.movie = movie;
        this.seats = seats;
    }
}
