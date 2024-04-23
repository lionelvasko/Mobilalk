package hu.inf.szte.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import hu.inf.szte.MainActivity;
import hu.inf.szte.R;
import hu.inf.szte.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText emailEditText = binding.email;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            loginViewModel.authenticateUser(email, password, task -> {
                if (task.isSuccessful()) {
                    // Sign in successful, navigate to the next page
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.nav_movies);

                    // Update user info
                    Activity activity = getActivity();
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).updateUserInfo();
                        ((MainActivity) activity).updateNavigationViewMenu();
                    }

                    requireActivity().invalidateOptionsMenu();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        final Button forgotPasswordButton = binding.forgotPassword;

        forgotPasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            if (!email.isEmpty()) {
                loginViewModel.sendPasswordResetEmail(email, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Password reset email sent.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please enter your email.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}