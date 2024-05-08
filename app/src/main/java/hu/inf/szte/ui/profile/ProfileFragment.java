package hu.inf.szte.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import hu.inf.szte.model.Profile;
import hu.inf.szte.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        // Start the animation
        root.startAnimation(fadeInAnimation);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = root.findViewById(R.id.email);
        editTextName = root.findViewById(R.id.name);
        editTextPhone = root.findViewById(R.id.phone);
        editTextAddress = root.findViewById(R.id.address);
        Button buttonUpdate = root.findViewById(R.id.button_update);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            editTextEmail.setHint(user.getEmail());
            editTextName.setHint(user.getName());
            editTextPhone.setHint(user.getPhone());
            editTextAddress.setHint(user.getAddress());
        });

        buttonUpdate.setOnClickListener(v -> {
            Profile user = new Profile();

            String email = editTextEmail.getText().toString();
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String address = editTextAddress.getText().toString();

            if (!email.isEmpty()) {
                user.setEmail(email);
            } else {
                user.setEmail(Objects.requireNonNull(profileViewModel.getUser().getValue()).getEmail());
            }

            if (!name.isEmpty()) {
                user.setName(name);
            } else {
                user.setName(Objects.requireNonNull(profileViewModel.getUser().getValue()).getName());
            }

            if (!phone.isEmpty()) {
                user.setPhone(phone);
            } else {
                user.setPhone(Objects.requireNonNull(profileViewModel.getUser().getValue()).getPhone());
            }

            if (!address.isEmpty()) {
                user.setAddress(address);
            } else {
                user.setAddress(Objects.requireNonNull(profileViewModel.getUser().getValue()).getAddress());
            }

            profileViewModel.updateUser(user);

            NavHostFragment.findNavController(ProfileFragment.this)
                    .navigate(R.id.nav_profile);
        });

        return root;
    }
}