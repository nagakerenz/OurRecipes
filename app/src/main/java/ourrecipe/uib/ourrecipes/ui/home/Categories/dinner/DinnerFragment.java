package ourrecipe.uib.ourrecipes.ui.home.Categories.dinner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.databinding.FragmentCategoriesLunchBinding;

public class DinnerFragment extends Fragment {

    private FragmentCategoriesLunchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DinnerViewModel dinnerViewModel =
                new ViewModelProvider(this).get(DinnerViewModel.class);

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