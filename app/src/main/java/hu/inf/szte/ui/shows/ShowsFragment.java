package hu.inf.szte.ui.shows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import hu.inf.szte.databinding.FragmentShowsBinding;
import hu.inf.szte.ui.movies.MoviesViewModel;

public class ShowsFragment extends Fragment {

    private FragmentShowsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShowsViewModel homeViewModel =
                new ViewModelProvider(this).get(ShowsViewModel.class);

        binding = FragmentShowsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}