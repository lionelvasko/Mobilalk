package hu.inf.szte.ui.shows;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import hu.inf.szte.R;

public class AddShowFragment extends Fragment {

    private EditText nameEditText;
    private EditText datetimeEditText;
    private NumberPicker seatNumberPicker;
    private Button submitButton;

    private NavController navController;
    private AddShowViewModel viewModel;

    // Declare a Uri field to store the Uri of the selected image
    private Uri selectedImageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addshow, container, false);

        viewModel = new ViewModelProvider(this).get(AddShowViewModel.class);

        nameEditText = root.findViewById(R.id.movie_edit_text);
        datetimeEditText = root.findViewById(R.id.datetime_edit_text);
        seatNumberPicker = root.findViewById(R.id.seat_number_edit_text);
        seatNumberPicker.setMinValue(1);
        seatNumberPicker.setMaxValue(100);
        submitButton = root.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(v -> addShow());

        // Set an OnClickListener for the releaseDateEditText
        datetimeEditText.setOnClickListener(v -> {
            // Get the current date
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // Display the selected date in releaseDateEditText
                        datetimeEditText.setText(String.format(Locale.US, "%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth));
                    }, year, month, day);
            datePickerDialog.show();
        });

        return root;
    }

    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }


    private void addShow() {
        String name = nameEditText.getText().toString();
        int seatNumber = seatNumberPicker.getValue();

        // Get the selected date from the datetimeEditText
        Date datetime = null;
        try {
            datetime = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(datetimeEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Boolean> seats = new ArrayList<>(Collections.nCopies(seatNumber, true));

        // Pass the seatNumber to the addShow() method in your AddShowViewModel
        viewModel.addShow(this, name, datetime, seats);
    }
}