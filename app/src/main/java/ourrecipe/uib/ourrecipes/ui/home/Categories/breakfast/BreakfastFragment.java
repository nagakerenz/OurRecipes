package ourrecipe.uib.ourrecipes.ui.home.Categories.breakfast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.databinding.FragmentCategoriesBreakfastBinding;

public class BreakfastFragment extends Fragment {

    private FragmentCategoriesBreakfastBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BreakfastViewModel breakfastViewModel =
                new ViewModelProvider(this).get(BreakfastViewModel.class);

        binding = FragmentCategoriesBreakfastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}