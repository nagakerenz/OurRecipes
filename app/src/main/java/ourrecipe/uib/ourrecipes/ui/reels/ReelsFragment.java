package ourrecipe.uib.ourrecipes.ui.reels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.databinding.FragmentReelsBinding;

public class ReelsFragment extends Fragment {

    private FragmentReelsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReelsViewModel reelsViewModel =
                new ViewModelProvider(this).get(ReelsViewModel.class);

        binding = FragmentReelsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReels;
        reelsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}