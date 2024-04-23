package hu.inf.szte.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

import hu.inf.szte.R;
import hu.inf.szte.model.Show;

public class BookFragment extends Fragment {

    private static final String ARG_SHOW = "arg_show";

    public static BookFragment newInstance(Show show) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SHOW, (Serializable) show);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        assert getArguments() != null;
        Show show = (Show) getArguments().getSerializable(ARG_SHOW);

        // Find the TextViews
        TextView showNameTextView = view.findViewById(R.id.show_name);
        TextView showDatetimeTextView = view.findViewById(R.id.show_datetime);

        // Set the show's name and datetime to the TextViews
        showNameTextView.setText(show.getMovie());
        showDatetimeTextView.setText(show.getDatetime().toString());

        // Create as many rectangles as the show has
        // ...

        return view;
    }
}
