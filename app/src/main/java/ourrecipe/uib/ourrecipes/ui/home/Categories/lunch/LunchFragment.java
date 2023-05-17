package ourrecipe.uib.ourrecipes.ui.home.Categories.lunch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.databinding.FragmentCategoriesLunchBinding;
public class LunchFragment extends Fragment {

    private FragmentCategoriesLunchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LunchViewModel lunchViewModel =
                new ViewModelProvider(this).get(LunchViewModel.class);

        binding = FragmentCategoriesLunchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}