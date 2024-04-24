package hu.inf.szte.ui.book;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.inf.szte.R;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.SeatViewHolder> {

    private List<Boolean> seats;
    private List<Boolean> selectedSeats;
    private OnSeatClickListener onSeatClickListener;

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
        if (seat) {
            holder.rectangleView.setBackgroundColor(selectedSeats.get(position) ? Color.BLUE : Color.GREEN);
        } else {
            holder.rectangleView.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    public void selectSeat(int position) {
        selectedSeats.set(position, !selectedSeats.get(position));
        notifyItemChanged(position);
    }
}