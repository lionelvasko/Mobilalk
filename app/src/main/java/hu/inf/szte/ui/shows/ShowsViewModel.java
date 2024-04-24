package hu.inf.szte.ui.shows;

import hu.inf.szte.model.Show;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowsViewModel extends ViewModel {

    private final MutableLiveData<List<Show>> shows = new MutableLiveData<>();

    public ShowsViewModel() {
        fetchShows();
    }

    public LiveData<List<Show>> getShows() {
        return shows;
    }

    private void fetchShows() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shows")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Show> showList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Show show = document.toObject(Show.class);
                            show.setId(document.getId()); // Set the id of the Show object to the document's ID
                            showList.add(show);
                        }
                        shows.setValue(showList);
                    } else {
                        // Handle the error
                    }
                });
    }
}