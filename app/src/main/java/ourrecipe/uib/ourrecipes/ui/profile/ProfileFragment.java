package ourrecipe.uib.ourrecipes.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ourrecipe.uib.ourrecipes.AccountPage.AccountPage;
import ourrecipe.uib.ourrecipes.FavoritePage;
import ourrecipe.uib.ourrecipes.NotificationPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    Button favorites;
    Button notification;
    Button account;
    TextView displayedName;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        displayedName = (TextView) root.findViewById(R.id.displayName);
        favorites = (Button) root.findViewById(R.id.favorites);
        notification = (Button) root.findViewById(R.id.notification);
        account = (Button) root.findViewById(R.id.account);

        String name = getActivity().getIntent().getStringExtra("inputText");
        if (name != null) {
            displayedName.setText(name);
        }
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavoritesPage();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationPage();
            }
        });

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