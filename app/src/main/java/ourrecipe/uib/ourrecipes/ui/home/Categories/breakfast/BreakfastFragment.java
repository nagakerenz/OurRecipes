package ourrecipe.uib.ourrecipes.ui.home.Categories.breakfast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentCategoriesBreakfastBinding;

public class BreakfastFragment extends Fragment {

    private FragmentCategoriesBreakfastBinding binding;
    private ImageButton breakfastImageButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BreakfastViewModel breakfastViewModel =
                new ViewModelProvider(this).get(BreakfastViewModel.class);

        binding = FragmentCategoriesBreakfastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the ImageButton in the layout
        breakfastImageButton = root.findViewById(R.id.breakfast);

        // Retrieve the argument passed from HomeFragment
        String category = getArguments().getString("category");

        // Select the appropriate ImageButton based on the user's choice
        if (category != null && category.equals("breakfast")) {
            // Set the selected state for the ImageButton
            breakfastImageButton.setSelected(true);
        } else {
            // Set the unselected state for the ImageButton
            breakfastImageButton.setSelected(false);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
