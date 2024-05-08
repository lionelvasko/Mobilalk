package hu.inf.szte.ui.movies;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import hu.inf.szte.R;
import hu.inf.szte.model.Movie;

public class AddMovieViewModel extends ViewModel {

    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public AddMovieViewModel() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void addMovie(Fragment fragment, String name, int duration, Timestamp releaseDate, Uri pictureUri) {
        // Upload the picture to Firebase Storage
        StorageReference pictureRef = storage.getReference().child(Objects.requireNonNull(pictureUri.getLastPathSegment()));
        pictureRef.putFile(pictureUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL
                    pictureRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String pictureUrl = uri.toString();

                        // Add a new document to Firestore
                        Movie movie = new Movie(name, duration, releaseDate, pictureUrl);
                        db.collection("movies").add(movie);

                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.nav_movies);
                    });
                });
    }
}