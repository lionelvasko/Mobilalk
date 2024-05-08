package hu.inf.szte.ui.shows;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.inf.szte.R;
import hu.inf.szte.databinding.FragmentShowsBinding;
import hu.inf.szte.model.Show;
import hu.inf.szte.ui.book.BookActivity;
import hu.inf.szte.ui.book.BookFragment;

public class ShowsFragment extends Fragment implements ShowsAdapter.OnShowClickListener {

    private FragmentShowsBinding binding;
    private List<Show> shows; // Add this line

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShowsViewModel showsViewModel =
                new ViewModelProvider(this).get(ShowsViewModel.class);

        binding = FragmentShowsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        // Start the animation
        root.startAnimation(fadeInAnimation);

        RecyclerView recyclerView = binding.recyclerView;
        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        ShowsAdapter adapter = new ShowsAdapter(this);
        recyclerView.setAdapter(adapter);

        // Update the RecyclerView
        showsViewModel.getShows().observe(getViewLifecycleOwner(), shows -> {
            adapter.setShows(shows);
            this.shows = shows; // Update the shows list
        });

        return root;
    }

    @Override
    public void onShowClick(int position) {
        Show selectedShow = shows.get(position);
        Intent intent = new Intent(getActivity(), BookActivity.class);
        intent.putExtra("selected_show", selectedShow);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}