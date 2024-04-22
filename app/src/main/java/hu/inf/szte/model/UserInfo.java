package hu.inf.szte.model;

import java.util.ArrayList;

public class UserInfo {
    public String email;
    public String name;
    public String phone;
    public String address;
    public boolean admin;
    public ArrayList<Ticket> tickets;

    public UserInfo(String email, String name, String phone, String address, boolean admin, ArrayList<Ticket> tickets) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.admin = admin;
        this.tickets = tickets;
    }
}