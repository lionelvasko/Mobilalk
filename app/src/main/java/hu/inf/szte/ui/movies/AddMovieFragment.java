package hu.inf.szte.ui.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import hu.inf.szte.databinding.FragmentAddmovieBinding;

public class AddMovieFragment extends Fragment {

    private FragmentAddmovieBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is logged in
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        boolean isAdmin = Boolean.TRUE.equals(document.getBoolean("admin"));
                        if (!isAdmin) {
                            // User is not an admin, redirect to MoviesFragment
                            Intent intent = new Intent(getActivity(), MoviesFragment.class);
                            startActivity(intent);
                            requireActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                        }
                    }
                }
            });
        } else {
            // User is not logged in, redirect to MoviesFragment
            Intent intent = new Intent(getActivity(), MoviesFragment.class);
            startActivity(intent);
            requireActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        AddMovieViewModel homeViewModel =
                new ViewModelProvider(this).get(AddMovieViewModel.class);

        binding = FragmentAddmovieBinding.inflate(inflater, container, false);
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