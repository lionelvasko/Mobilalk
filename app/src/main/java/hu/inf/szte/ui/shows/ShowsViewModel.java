package hu.inf.szte.ui.shows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShowsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is show fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}