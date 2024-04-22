package hu.inf.szte.ui.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hu.inf.szte.databinding.FragmentMoviesBinding;

public class MoviesFragment extends Fragment {

    private FragmentMoviesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MoviesViewModel moviesViewModel =
                new ViewModelProvider(this).get(MoviesViewModel.class);

        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieAdapter adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        // Update the RecyclerView
        moviesViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> adapter.setMovies(movies));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}