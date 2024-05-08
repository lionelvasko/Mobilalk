package hu.inf.szte.ui.movies;

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

import hu.inf.szte.R;
import hu.inf.szte.databinding.FragmentMoviesBinding;

public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MoviesViewModel moviesViewModel =
                new ViewModelProvider(this).get(MoviesViewModel.class);
        moviesViewModel.fetchUserAdminStatus();

        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        // Start the animation
        root.startAnimation(fadeInAnimation);

        RecyclerView recyclerView = binding.recyclerView;
        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 1;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        MovieAdapter adapter = new MovieAdapter(moviesViewModel, getViewLifecycleOwner());
        recyclerView.setAdapter(adapter);

        // Update the RecyclerView
        moviesViewModel.getMovies().observe(getViewLifecycleOwner(), adapter::setMovies);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}