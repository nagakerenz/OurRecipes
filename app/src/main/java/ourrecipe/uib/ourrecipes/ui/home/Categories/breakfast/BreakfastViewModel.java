package ourrecipe.uib.ourrecipes.ui.home.Categories.breakfast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BreakfastViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BreakfastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}