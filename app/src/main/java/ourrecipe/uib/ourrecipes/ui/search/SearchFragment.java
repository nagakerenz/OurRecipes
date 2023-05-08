package ourrecipe.uib.ourrecipes.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.FoodRecipes;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
//    kalo crash hapus ini
    SearchView searchView;
    ImageButton menu;
    ImageButton menu1;
    ImageButton menu2;
    ImageButton menu3;
    ImageButton menu4;
    ImageButton menu5;
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


        menu = (ImageButton) root.findViewById(R.id.imageButton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu1 = (ImageButton) root.findViewById(R.id.imageButton1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu2 = (ImageButton) root.findViewById(R.id.imageButton2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu3 = (ImageButton) root.findViewById(R.id.imageButton4);
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu4 = (ImageButton) root.findViewById(R.id.imageButton5);
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });
        menu5 = (ImageButton) root.findViewById(R.id.imageButton6);
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuPage();
            }
        });

//        sampai sini
        return root;
    }
//    dan ini
    public void openResult(){
        Intent result = new Intent(SearchFragment.this.getActivity(), SearchResult.class);
        startActivity(result);
    }
//    sampai sini

    public void openMenuPage() {
        Intent menu = new Intent(SearchFragment.this.getActivity(), FoodRecipes.class);
        startActivity(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
