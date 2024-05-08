package hu.inf.szte.ui.movies;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import hu.inf.szte.R;

public class AddMovieFragment extends Fragment {

    private EditText nameEditText;
    private EditText durationEditText;
    private EditText releaseDateEditText;
    private ImageView pictureImageView;
    private Button selectPictureButton;
    private Button submitButton;

    private NavController navController;
    private AddMovieViewModel viewModel;

    // Declare a Uri field to store the Uri of the selected image
    private Uri selectedImageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addmovie, container, false);

        // Load the animation
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade);

        // Start the animation
        root.startAnimation(fadeInAnimation);

        viewModel = new ViewModelProvider(this).get(AddMovieViewModel.class);

        nameEditText = root.findViewById(R.id.name_edit_text);
        durationEditText = root.findViewById(R.id.duration_edit_text);
        releaseDateEditText = root.findViewById(R.id.release_date_edit_text);
        pictureImageView = root.findViewById(R.id.picture_image_view);
        selectPictureButton = root.findViewById(R.id.select_picture_button);
        submitButton = root.findViewById(R.id.submit_button);

        selectPictureButton.setOnClickListener(v -> selectPicture());
        submitButton.setOnClickListener(v -> addMovie());

        // Set an OnClickListener for the releaseDateEditText
        releaseDateEditText.setOnClickListener(v -> {
            // Get the current date
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // Display the selected date in releaseDateEditText
                        releaseDateEditText.setText(String.format(Locale.US, "%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth));
                    }, year, month, day);
            datePickerDialog.show();
        });

        return root;
    }

    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            pictureImageView.setImageURI(selectedImageUri);
        }
    }

    private void addMovie() {
        String name = nameEditText.getText().toString();
        int duration = Integer.parseInt(durationEditText.getText().toString());

        // Get the selected date from the releaseDateEditText
        Timestamp releaseDate = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(releaseDateEditText.getText().toString());
            releaseDate = new Timestamp(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Pass the Uri of the selected image to the addMovie() method in your AddMovieViewModel
        viewModel.addMovie(this, name, duration, releaseDate, selectedImageUri);
    }
}