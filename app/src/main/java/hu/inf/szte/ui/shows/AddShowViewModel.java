package hu.inf.szte.ui.shows;

import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

import hu.inf.szte.R;
import hu.inf.szte.model.Show;

public class AddShowViewModel extends ViewModel {

    private FirebaseFirestore db;
    private FirebaseStorage storage;

    public AddShowViewModel() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void addShow(Fragment fragment, String name, Date date, ArrayList<Boolean> seatNumber) {
        // Add a new document to Firestore
        Show show = new Show(name, date, seatNumber);
        db.collection("shows").add(show);

        NavHostFragment.findNavController(fragment)
                .navigate(R.id.nav_shows);
    }
}