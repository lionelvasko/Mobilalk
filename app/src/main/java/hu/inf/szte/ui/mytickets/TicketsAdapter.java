package hu.inf.szte.ui.mytickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.inf.szte.R;
import hu.inf.szte.model.Ticket;


public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketViewHolder> {

    private List<Ticket> tickets;

    public TicketsAdapter(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.ticketMovie.setText(ticket.getMovie());
        holder.ticketDate.setText(ticket.getDate().toString());
        holder.ticketSeats.setText(ticket.getSeats().toString());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    // Method to update the tickets
    public void updateTickets(List<Ticket> newTickets) {
        this.tickets = newTickets;
        notifyDataSetChanged();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView ticketMovie;
        TextView ticketDate;
        TextView ticketSeats;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketMovie = itemView.findViewById(R.id.ticket_movie);
            ticketDate = itemView.findViewById(R.id.ticket_date);
            ticketSeats = itemView.findViewById(R.id.ticket_seats);
        }
    }
}