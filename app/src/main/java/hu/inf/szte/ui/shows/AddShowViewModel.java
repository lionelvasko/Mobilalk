package hu.inf.szte.ui.shows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddShowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddShowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}