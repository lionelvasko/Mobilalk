package hu.inf.szte.ui.register;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hu.inf.szte.MainActivity;
import hu.inf.szte.R;
import hu.inf.szte.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegisterViewModel registerViewModel =
                new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        // Start the animation
        root.startAnimation(fadeInAnimation);

        final EditText usernameEditText = binding.email;
        final EditText passwordEditText = binding.password;
        final EditText repeatPasswordEditText = binding.repeatPassword;
        final EditText nameEditText = binding.name;
        final EditText phoneEditText = binding.phone;
        final EditText addressEditText = binding.address;
        final Button registerButton = binding.register;
        final Button loginButton = binding.login;

        db = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(RegisterFragment.this)
                    .navigate(R.id.nav_login);
        });

        registerButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String repeatPassword = repeatPasswordEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String address = addressEditText.getText().toString();

            if(!password.equals(repeatPassword)) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            registerViewModel.registerUser(email, password, task -> {
                if (task.isSuccessful()) {
                    // Sign in successful
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("email", email);
                    userData.put("name", name);
                    userData.put("phone", phone);
                    userData.put("address", address);
                    userData.put("admin", false);

                    db.collection("users").document(user.getUid())
                            .set(userData);

                    // Update user info
                    Activity activity = getActivity();
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).updateUserInfo();
                        ((MainActivity) activity).updateNavigationViewMenu();
                    }

                    // Navigate to the MoviesFragment
                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.nav_movies);

                    requireActivity().invalidateOptionsMenu();
                } else {
                    // Get the exception from the task
                    Exception e = task.getException();
                    String errorMessage = "Registration failed. Please check your data and try again.";

                    if (e instanceof FirebaseAuthException) {
                        String errorCode = ((FirebaseAuthException) e).getErrorCode();

                        switch (errorCode) {
                            case "ERROR_INVALID_EMAIL":
                                errorMessage = "The email address is badly formatted.";
                                break;
                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                errorMessage = "The email address is already in use by another account.";
                                break;
                            case "ERROR_WEAK_PASSWORD":
                                errorMessage = "The password is not strong enough.";
                                break;

                        }
                    }

                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}