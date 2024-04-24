package hu.inf.szte.ui.book;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.inf.szte.R;
import hu.inf.szte.model.Show;

public class BookFragment extends Fragment {

    private static final String ARG_SHOW = "arg_show";
    private FirebaseFirestore db;

    private Show show;

    private SeatsAdapter seatsAdapter;

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

        if (savedInstanceState != null) {
            show = (Show) savedInstanceState.getSerializable(ARG_SHOW);
        } else {
            assert getArguments() != null;
            show = (Show) getArguments().getSerializable(ARG_SHOW);
        }


        db = FirebaseFirestore.getInstance();
        
        assert show != null;
        if (show.getSeats() == null) {
            seatsAdapter = null;
            Log.e("BookFragment", "Seats is null");
        } else {
            // Set up RecyclerView with SeatsAdapter
            RecyclerView seatsRecyclerView = view.findViewById(R.id.seats_recycler_view);
            seatsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            seatsAdapter = new SeatsAdapter(show.getSeats());
            // Handle seat click
            seatsAdapter.setOnSeatClickListener(seatsAdapter::selectSeat);
            seatsRecyclerView.setAdapter(seatsAdapter);
        }

        // Find the TextViews
        TextView showNameTextView = view.findViewById(R.id.show_name);
        TextView showDatetimeTextView = view.findViewById(R.id.show_datetime);

        // Set the show's name and datetime to the TextViews
        showNameTextView.setText(show.getMovie());
        showDatetimeTextView.setText(show.getDatetime().toString());

        // Find the Book button
        Button bookButton = view.findViewById(R.id.book_button);
        // Set a click listener on the Book button
        bookButton.setOnClickListener(v -> {
            // Get the selected seats
            assert seatsAdapter != null;
            List<Integer> selectedSeats = seatsAdapter.getSelectedSeats();

            // Update the show data in Firestore
            Map<String, Object> showData = new HashMap<>();
            showData.put("movie", show.getMovie());
            showData.put("date", show.getDatetime());
            showData.put("seats", show.getSeats());
            Log.i("BookFragment", "DocID: " + show.getId());
            Log.i("BookFragment", "Selected seats: " + selectedSeats.toString());
            db.collection("shows").document(show.getId())
                    .update(showData);

            // Update the user's tickets in Firestore
            // Replace "userId" with the actual user's ID
            Map<String, Object> ticketData = new HashMap<>();
            ticketData.put("date", show.getDatetime().toString());
            ticketData.put("movie", show.getMovie());
            ticketData.put("seats", selectedSeats);
            db.collection("users").document("userId")
                    .update("tickets", FieldValue.arrayUnion(ticketData));

            new AlertDialog.Builder(getContext())
                    .setTitle("Booking Successful")
                    .setMessage("Your booking has been successful!")
                    .setPositiveButton("OK", null)
                    .show();
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Retrieve the updated state from the Firestore database
        db.collection("shows").document(show.getId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Show updatedShow = documentSnapshot.toObject(Show.class);
                    assert updatedShow != null;
                    show.setSeats(updatedShow.getSeats());

                    // Update the seats in the SeatsAdapter
                    seatsAdapter.updateSeats(show.getSeats());
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_SHOW, show);
    }
}