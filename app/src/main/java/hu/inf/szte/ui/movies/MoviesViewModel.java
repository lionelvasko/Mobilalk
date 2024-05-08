package hu.inf.szte.ui.movies;

import hu.inf.szte.model.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isAdmin = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsAdmin() {
        return isAdmin;
    }

    public MoviesViewModel() {
        fetchMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    private void fetchMovies() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Movie> movieList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movie.setId(document.getId()); // Set the id of the movie with the document ID
                            movieList.add(movie);
                        }
                        movies.setValue(movieList);
                    } else {
                        // Handle the error
                    }
                });
    }

    public void fetchUserAdminStatus() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Boolean adminStatus = task.getResult().getBoolean("admin");
                            isAdmin.setValue(adminStatus);
                        } else {
                            // Handle the error
                        }
                    });
        }
    }

    public void deleteMovie(String movieId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("movies").document(movieId)
                .delete()
                .addOnSuccessListener(aVoid -> fetchMovies())
                .addOnFailureListener(e -> {
                    // Handle the error
                });
    }
}