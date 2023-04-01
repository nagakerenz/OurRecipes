package ourrecipe.uib.ourrecipes.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;

import ourrecipe.uib.ourrecipes.AccountPage.DataClass;
import ourrecipe.uib.ourrecipes.AccountPage.SignUpPage;
import ourrecipe.uib.ourrecipes.AccountPage.User;
import ourrecipe.uib.ourrecipes.Profile.AccountPage;
import ourrecipe.uib.ourrecipes.Profile.FavoritePage;
import ourrecipe.uib.ourrecipes.Profile.NotificationPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    ImageView displayPicture;
    TextView displayName;
    Button favorites, notification, account;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        displayPicture = (ImageView) root.findViewById(R.id.displayPicture);
        displayName = (TextView) root.findViewById(R.id.displayName);
        favorites = (Button) root.findViewById(R.id.favorites);
        notification = (Button) root.findViewById(R.id.notification);
        account = (Button) root.findViewById(R.id.account);

        //This is for handling Displayed UserName
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = mAuth.getInstance().getCurrentUser().getUid();
        String userName = prefs.getString(userId, "");

        if (!TextUtils.isEmpty(userName)) {
            displayName.setText(userName);
        } else {
            // User name is not available locally, retrieve it from Firebase
            databaseReference = FirebaseDatabase.getInstance().getReference("User Profile");
            eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String userId = user.getUid();
                        String name = "";
                        boolean isFacebookUser = false;
                        boolean isGoogleUser = false;
                        for (UserInfo userInfo : user.getProviderData()) {
                            if (userInfo.getProviderId().equals("facebook.com")) {
                                isFacebookUser = true;
                                name = user.getDisplayName();
                                break;
                            } else if (userInfo.getProviderId().equals("google.com")) {
                                isGoogleUser = true;
                                name = user.getDisplayName();
                                break;
                            }
                        }
                        if (!isFacebookUser && !isGoogleUser) {
                            for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                                User userProfile = itemSnapShot.getValue(User.class);
                                if (userProfile.getUserId().equals(userId)) {
                                    name = userProfile.getName();
                                    break;
                                }
                            }
                        } else {
                            // Replace locally cached name with Facebook or Google name
                            prefs.edit().putString(userId, name).apply();
                        }
                        displayName.setText(name);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                }
            };
            databaseReference.addListenerForSingleValueEvent(eventListener);
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
        // Remove the ValueEventListener
        databaseReference.removeEventListener(eventListener);
        binding = null;
    }


}