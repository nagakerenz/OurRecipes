package ourrecipe.uib.ourrecipes.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.AccountPage;
import ourrecipe.uib.ourrecipes.BreakfastPage;
import ourrecipe.uib.ourrecipes.FavoritePage;
import ourrecipe.uib.ourrecipes.NotificationPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentProfileBinding;
import ourrecipe.uib.ourrecipes.ui.home.HomeFragment;

public class ProfileFragment extends Fragment {
    Button favorites;
    Button notification;
    Button account;

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        favorites = (Button) root.findViewById(R.id.favorites);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavoritesPage();
            }
        });

        notification = (Button) root.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationPage();
            }
        });

        account = (Button) root.findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountPage();
            }
        });
        return root;
    }

    public void openFavoritesPage() {
        Intent favorites = new Intent(ProfileFragment.this.getActivity(), FavoritePage.class);
        startActivity(favorites);
    }

    public void openNotificationPage() {
        Intent notification = new Intent(ProfileFragment.this.getActivity(), NotificationPage.class);
        startActivity(notification);
    }

    public void openAccountPage() {
        Intent account = new Intent(ProfileFragment.this.getActivity(), AccountPage.class);
        startActivity(account);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}