package ourrecipe.uib.ourrecipes.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
//    kalo crash hapus ini
    SearchView searchView;

    private FragmentSearchBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//kalo crash hapus ini
        searchView = (SearchView) root.findViewById(R.id.search1);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             openResult();
            }
        });
//        sampai sini
        return root;
    }
//    dan ini
    public void openResult(){
        Intent result = new Intent(SearchFragment.this.getActivity(),searchResult.class);
        startActivity(result);
    }
//    sampai sini

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
