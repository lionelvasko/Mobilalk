package hu.inf.szte.model;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String email;
    private String name;
    private String phone;
    private String address;
    private ArrayList<Ticket> tickets;

    public Profile(String email, String name, String phone, String address, ArrayList<Ticket> tickets) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tickets = tickets;
    }

    public Profile(){
        this.email = "";
        this.name = "";
        this.phone = "";
        this.address = "";
        this.tickets = new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}
