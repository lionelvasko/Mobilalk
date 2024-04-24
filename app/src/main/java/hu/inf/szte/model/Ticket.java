package hu.inf.szte.model;
import com.google.firebase.Timestamp;
import com.google.type.Date;
import java.util.ArrayList;

public class Ticket {
    public Timestamp date;
    public String movie;
    public ArrayList<Long> seats;

    public Ticket(Timestamp timestamp, String movie, ArrayList<Long> seats) {
        this.date = timestamp;
        this.movie = movie;
        this.seats = seats;
    }
}
