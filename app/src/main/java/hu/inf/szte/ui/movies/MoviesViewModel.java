package hu.inf.szte.ui.movies;

import hu.inf.szte.model.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

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
                            movieList.add(movie);
                        }
                        movies.setValue(movieList);
                    } else {
                        // Handle the error
                    }
                });
    }
}