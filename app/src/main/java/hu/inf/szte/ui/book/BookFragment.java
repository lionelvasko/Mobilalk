package hu.inf.szte.ui.book;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import hu.inf.szte.R;
import hu.inf.szte.model.Show;

public class BookFragment extends Fragment {

    private static final String ARG_SHOW = "arg_show";

    public static BookFragment newInstance(Show show) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHOW, (Serializable) show);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        assert getArguments() != null;
        Show show = (Show) getArguments().getSerializable(ARG_SHOW);

        if (show.getSeats() == null) {
            Log.e("BookFragment", "Seats is null");
        } else {
            // Set up RecyclerView with SeatsAdapter
            RecyclerView seatsRecyclerView = view.findViewById(R.id.seats_recycler_view);
            seatsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            SeatsAdapter seatsAdapter = new SeatsAdapter(show.getSeats());
            seatsAdapter.setOnSeatClickListener(position -> {
                // Handle seat click
                seatsAdapter.selectSeat(position);
            });
            seatsRecyclerView.setAdapter(seatsAdapter);
        }

        // Find the TextViews
        TextView showNameTextView = view.findViewById(R.id.show_name);
        TextView showDatetimeTextView = view.findViewById(R.id.show_datetime);

        // Set the show's name and datetime to the TextViews
        showNameTextView.setText(show.getMovie());
        showDatetimeTextView.setText(show.getDatetime().toString());


        return view;
    }
}