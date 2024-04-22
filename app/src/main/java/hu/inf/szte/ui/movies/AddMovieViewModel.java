package hu.inf.szte.ui.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddMovieViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddMovieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addmovie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}