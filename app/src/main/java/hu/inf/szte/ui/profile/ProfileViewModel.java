package hu.inf.szte.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import hu.inf.szte.model.Profile;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> user;
    private FirebaseFirestore db;

    public ProfileViewModel() {
        user = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        fetchUser();
    }

    public LiveData<Profile> getUser() {
        return user;
    }

    private void fetchUser() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Profile user = documentSnapshot.toObject(Profile.class);
                    this.user.setValue(user);
                });
    }

    public void updateUser(Profile userdata) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(currentUserId)
                .set(userdata)
                .addOnSuccessListener(aVoid -> {
                    // Handle success
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }
}