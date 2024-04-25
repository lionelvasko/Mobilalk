package hu.inf.szte.ui.mytickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import hu.inf.szte.model.Profile;
import hu.inf.szte.model.Ticket;

// File: TicketsViewModel.java
public class TicketsViewModel extends ViewModel {

    private MutableLiveData<List<Ticket>> tickets;
    private FirebaseFirestore db;

    public TicketsViewModel() {
        tickets = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        fetchTickets();
    }

    public LiveData<List<Ticket>> getTickets() {
        return tickets;
    }

    void fetchTickets() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Profile profile = documentSnapshot.toObject(Profile.class);
                    if (profile != null) {
                        tickets.setValue(profile.getTickets());
                    }
                });
    }
}