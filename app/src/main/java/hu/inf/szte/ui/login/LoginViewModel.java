package hu.inf.szte.ui.login;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {

    private final FirebaseAuth mAuth;

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void authenticateUser(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void sendPasswordResetEmail(String email, OnCompleteListener<Void> listener) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(listener);
    }
}