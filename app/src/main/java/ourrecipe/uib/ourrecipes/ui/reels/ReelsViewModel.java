package ourrecipe.uib.ourrecipes.ui.reels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReelsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReelsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}