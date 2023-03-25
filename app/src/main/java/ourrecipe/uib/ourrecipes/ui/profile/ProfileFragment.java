package ourrecipe.uib.ourrecipes.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ourrecipe.uib.ourrecipes.Profile.AccountPage;
import ourrecipe.uib.ourrecipes.Profile.FavoritePage;
import ourrecipe.uib.ourrecipes.Profile.NotificationPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    Button favorites;
    Button notification;
    Button account;
    TextView displayedName;
    ImageView userPicture;
//    FirebaseUser user;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        favorites = (Button) root.findViewById(R.id.favorites);
        notification = (Button) root.findViewById(R.id.notification);
        account = (Button) root.findViewById(R.id.account);
        displayedName = (TextView) root.findViewById(R.id.displayName);
        userPicture = (ImageView) root.findViewById(R.id.displayPicture);

        
//        user = FirebaseAuth.getInstance().getCurrentUser();
//
//        displayedName.setText(user.getDisplayName());
//        Glide.with(ProfileFragment.this).load(user.getPhotoUrl()).into(userPicture);

//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
//
//        usersRef.child(userId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String name = snapshot.getValue(String.class);
//                displayedName.setText(name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle error
//            }
//        });


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