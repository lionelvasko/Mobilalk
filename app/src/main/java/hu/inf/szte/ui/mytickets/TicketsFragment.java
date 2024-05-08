package hu.inf.szte.ui.mytickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hu.inf.szte.R;
import hu.inf.szte.databinding.FragmentMyticketsBinding;


public class TicketsFragment extends Fragment {

    private TicketsViewModel ticketsViewModel;
    private RecyclerView ticketsRecyclerView;
    private TicketsAdapter ticketsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mytickets, container, false);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        // Start the animation
        root.startAnimation(fadeInAnimation);

        ticketsRecyclerView = root.findViewById(R.id.tickets_recycler_view);
        ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the TicketsAdapter
        ticketsAdapter = new TicketsAdapter(new ArrayList<>());
        ticketsRecyclerView.setAdapter(ticketsAdapter);

        ticketsViewModel = new ViewModelProvider(this).get(TicketsViewModel.class);
        ticketsViewModel.getTickets().observe(getViewLifecycleOwner(), tickets -> {
            // Update the data in the TicketsAdapter
            ticketsAdapter.updateTickets(tickets);
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch the tickets again
        ticketsViewModel.fetchTickets();
    }
}