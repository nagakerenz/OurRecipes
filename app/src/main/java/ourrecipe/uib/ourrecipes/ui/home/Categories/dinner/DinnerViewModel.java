package ourrecipe.uib.ourrecipes.ui.home.Categories.dinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DinnerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DinnerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}