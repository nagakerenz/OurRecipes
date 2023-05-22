package ourrecipe.uib.ourrecipes.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ourrecipe.uib.ourrecipes.AccountPage.User;
import ourrecipe.uib.ourrecipes.ui.profile.Profile.AccountPage;
import ourrecipe.uib.ourrecipes.ui.profile.Profile.FavoritePage;
import ourrecipe.uib.ourrecipes.ui.profile.Profile.NotificationPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.CFragmentProfileBinding;

public class CProfileFragment extends Fragment {
    ImageView profilePicture;
    TextView displayName;
    Button favorites, notification, account;
    FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    ValueEventListener eventListener;


    private boolean hasShownToast = false;

    private CFragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = CFragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profilePicture = (ImageView) root.findViewById(R.id.displayPicture);
        displayName = (TextView) root.findViewById(R.id.displayName);
        favorites = (Button) root.findViewById(R.id.favorites);
        notification = (Button) root.findViewById(R.id.notification);
        account = (Button) root.findViewById(R.id.account);


        // This is for handling Display Picture
        // Retrieve the user's profile picture URL from Realtime Database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User Profile").child("GoogleUser").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    String profilePicUrl = user.getProfilePictureUrl();

                    // Load the profile picture using Glide
                    if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
                        Glide.with(CProfileFragment.this.getActivity())
                                .load(profilePicUrl)
                                .placeholder(R.drawable.profile_picture) // Placeholder image while loading
                                .error(R.drawable.profile_picture) // Error image if loading fails
                                .into(profilePicture); // ImageView where you want to load the profile picture
                    } else {
                        Toast.makeText(CProfileFragment.this.getActivity(), "Failed to Retrieve Picture", Toast.LENGTH_SHORT).show();
                        hasShownToast = true;
                    }
                } else {
                    Toast.makeText(CProfileFragment.this.getActivity(), "Failed to Retrieve Picture Cause There are no data in the database", Toast.LENGTH_SHORT).show();
                    hasShownToast = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CProfileFragment.this.getActivity(), "Failed to Retrieve Picture Because Database Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //This is for handling Displayed UserName
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
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
                                if (userProfile != null && userProfile.getUserId() != null && userProfile.getUserId().equals(userId)) {
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
                    Toast.makeText(CProfileFragment.this.getActivity(), "Failed to read value.", Toast.LENGTH_SHORT).show();
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
        Intent favorites = new Intent(CProfileFragment.this.getActivity(), FavoritePage.class);
        startActivity(favorites);
    }

    public void openNotificationPage() {
        Intent notification = new Intent(CProfileFragment.this.getActivity(), NotificationPage.class);
        startActivity(notification);
    }

    public void openAccountPage() {
        Intent account = new Intent(CProfileFragment.this.getActivity(), AccountPage.class);
        startActivity(account);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Check if databaseReference is not null and eventListener has been added
        if (databaseReference != null && eventListener != null) {
            // Remove the ValueEventListener
            databaseReference.removeEventListener(eventListener);
        }
        binding = null;
    }


}