package hu.inf.szte.ui.book;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.inf.szte.R;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.SeatViewHolder> {

    private List<Boolean> seats;
    private List<Boolean> selectedSeats;

    private List<Boolean> bookedSeats;
    private OnSeatClickListener onSeatClickListener;

    public List<Integer> getSelectedSeats() {
        List<Integer> selected = new ArrayList<>();
        for (int i = 0; i < selectedSeats.size(); i++) {
            if (selectedSeats.get(i)) {
                selected.add(i);
            }
        }
        return selected;
    }

    public void updateSeats(List<Boolean> newSeats) {
        seats = newSeats;
        notifyDataSetChanged();
    }

    public interface OnSeatClickListener {
        void onSeatClick(int position);
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        private final View rectangleView;

        public SeatViewHolder(View itemView, OnSeatClickListener listener) {
            super(itemView);
            rectangleView = itemView.findViewById(R.id.rectangle_view);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSeatClick(position);
                }
            });
        }
    }

    public SeatsAdapter(List<Boolean> seats) {
        this.seats = seats;
        this.selectedSeats = new ArrayList<>(Collections.nCopies(seats.size(), false));
        this.bookedSeats = new ArrayList<>(Collections.nCopies(seats.size(), false)); // Initialize bookedSeats with false
    }

    public void setOnSeatClickListener(OnSeatClickListener listener) {
        this.onSeatClickListener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item, parent, false);
        return new SeatViewHolder(view, onSeatClickListener);
    }

    @Override
    public void onBindViewHolder(SeatViewHolder holder, int position) {
        Boolean seat = seats.get(position);
        TextView seatIndexTextView = holder.itemView.findViewById(R.id.seat_index);
        seatIndexTextView.setText(String.valueOf(position));

        if (seat) { // Check if the seat is available
            boolean isSelected = selectedSeats.get(position);
            holder.rectangleView.setBackgroundColor(isSelected ? Color.BLUE : Color.GREEN);
            seatIndexTextView.setTextColor(isSelected ? Color.WHITE : Color.BLACK);
        } else {
            holder.rectangleView.setBackgroundColor(Color.RED);
        }

        // Always set the OnClickListener for the itemView
        holder.itemView.setOnClickListener(v -> {
            if (seat && !bookedSeats.get(position)) { // Only trigger the OnClickListener if the seat is available and not booked
                onSeatClickListener.onSeatClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public void selectSeat(int position) {
        // Toggle the selection status of the seat
        selectedSeats.set(position, !selectedSeats.get(position));
        notifyItemChanged(position);
    }
}