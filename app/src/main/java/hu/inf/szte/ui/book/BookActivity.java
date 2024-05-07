package hu.inf.szte.ui.book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import hu.inf.szte.model.Show;

import hu.inf.szte.R;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Show selectedShow = (Show) getIntent().getSerializableExtra("selected_show");
        BookFragment bookFragment = BookFragment.newInstance(selectedShow);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, bookFragment)
                .commit();
    }

}
