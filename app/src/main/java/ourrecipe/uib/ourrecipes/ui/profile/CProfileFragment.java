package ourrecipe.uib.ourrecipes.ui.profile;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ourrecipe.uib.ourrecipes.AccountPage.User;
import ourrecipe.uib.ourrecipes.ui.profile.Profile.AccountPage;
import ourrecipe.uib.ourrecipes.ui.profile.Profile.FavoritePage;
import ourrecipe.uib.ourrecipes.ui.profile.Profile.NotificationPage;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.databinding.CFragmentProfileBinding;

public class CProfileFragment extends Fragment {
    ImageView profilePicture;
    Button favorites, notification, account, upload, save;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    EditText editName;


    private boolean hasShownToast = false;
    private static final int REQUEST_CODE_IMAGE_PICK = 1;


    private CFragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = CFragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profilePicture = (ImageView) root.findViewById(R.id.displayPicture);
        editName = root.findViewById(R.id.editName);
        upload = root.findViewById(R.id.upload);
        save = root.findViewById(R.id.save);
        favorites = (Button) root.findViewById(R.id.favorites);
//        notification = (Button) root.findViewById(R.id.notification);
        account = (Button) root.findViewById(R.id.account);
        progressBar = root.findViewById(R.id.progressBar);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        String provider = "";
        for (UserInfo userInfo : currentUser.getProviderData()) {
            if (userInfo.getProviderId().equals("facebook.com")) {
                provider = "FacebookUser";
                break;
            } else if (userInfo.getProviderId().equals("google.com")) {
                provider = "GoogleUser";
                break;
            }
        }
        if (provider.isEmpty()) {
            provider = "User";
        }

        // This is for handling Display Picture
        // Retrieve the user's profile picture URL from Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User Profile").child(provider).child(userId).child("userInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    String profilePicUrl = user.getProfilePictureUrl();

                    // Load the profile picture using Glide
                    if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
                        Glide.with(CProfileFragment.this.getActivity())
                                .load(profilePicUrl)
                                .placeholder(R.drawable.c_fragment_profile_picture) // Placeholder image while loading
                                .error(R.drawable.c_fragment_profile_picture) // Error image if loading fails
                                .into(profilePicture); // ImageView where you want to load the profile picture
                    } else {
                        Toast.makeText(CProfileFragment.this.getActivity(), "Failed to Retrieve Picture No Picture Found", Toast.LENGTH_SHORT).show();
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

        //THIS IS FOR HANDLING THE DISPLAY NAME
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference()
                .child("User Profile")
                .child(provider)
                .child(userId)
                .child("userInfo")
                .child("name");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null) {
                    editName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error appropriately
                Toast.makeText(CProfileFragment.this.getActivity(), "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
//        eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    String userId = user.getUid();
//                    String name = "";
//                    boolean isFacebookUser = false;
//                    boolean isGoogleUser = false;
//                    for (UserInfo userInfo : user.getProviderData()) {
//                        if (userInfo.getProviderId().equals("facebook.com")) {
//                            isFacebookUser = true;
//                            name = user.getDisplayName();
//                            break;
//                        } else if (userInfo.getProviderId().equals("google.com")) {
//                            isGoogleUser = true;
//                            name = user.getDisplayName();
//                            break;
//                        }
//                    }
//                    if (!isFacebookUser && !isGoogleUser) {
//                        for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
//                            User userProfile = itemSnapShot.getValue(User.class);
//                            if (userProfile != null && userProfile.getUserId() != null && userProfile.getUserId().equals(userId)) {
//                                name = userProfile.getName();
//                                break;
//                            }
//                        }
//                    }
//                    editName.setText(name);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(CProfileFragment.this.getActivity(), "Failed to read value.", Toast.LENGTH_SHORT).show();
//            }
//        };
//        userReference.addValueEventListener(eventListener);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click to upload picture
                uploadProfilePicture();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editName.getText().toString().trim();
                if (!TextUtils.isEmpty(newName)) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = currentUser.getUid();
                    String provider = "";
                    for (UserInfo userInfo : currentUser.getProviderData()) {
                        if (userInfo.getProviderId().equals("facebook.com")) {
                            provider = "FacebookUser";
                            break;
                        } else if (userInfo.getProviderId().equals("google.com")) {
                            provider = "GoogleUser";
                            break;
                        }
                    }
                    if (provider.isEmpty()) {
                        provider = "User";
                    }
                    DatabaseReference nameRef = FirebaseDatabase.getInstance()
                            .getReference("User Profile")
                            .child(provider)
                            .child(userId)
                            .child("userInfo")
                            .child("name");
                    nameRef.setValue(newName)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CProfileFragment.this.getActivity(), "Name updated successfully", Toast.LENGTH_SHORT).show();
                                    editName.setText(newName);
                                } else {
                                    Toast.makeText(CProfileFragment.this.getActivity(), "Failed to update name", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                } else {
                    Toast.makeText(CProfileFragment.this.getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFavoritesPage();
            }
        });

//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openNotificationPage();
//            }
//        });

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

//    public void openNotificationPage() {
//        Intent notification = new Intent(CProfileFragment.this.getActivity(), NotificationPage.class);
//        startActivity(notification);
//    }

    public void openAccountPage() {
        Intent account = new Intent(CProfileFragment.this.getActivity(), AccountPage.class);
        startActivity(account);
    }

    private void uploadProfilePicture() {
        // Open a gallery view to select an image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // Show the progress bar
            progressBar.setVisibility(View.VISIBLE);
            // Upload the selected image to Firebase Storage
            // Save the download URL to the Realtime Database
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userId = currentUser.getUid();
            String provider = "";
            for (UserInfo userInfo : currentUser.getProviderData()) {
                if (userInfo.getProviderId().equals("facebook.com")) {
                    provider = "FacebookUser";
                    break;
                } else if (userInfo.getProviderId().equals("google.com")) {
                    provider = "GoogleUser";
                    break;
                }
            }
            if (provider.isEmpty()) {
                provider = "User";
            }
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User Profile Pictures").child(provider).child(userId + ".jpg");
            storageReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                // Save the download URL to the Realtime Database
                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                String userId = currentUser.getUid();
                                String provider = "";
                                for (UserInfo userInfo : currentUser.getProviderData()) {
                                    if (userInfo.getProviderId().equals("facebook.com")) {
                                        provider = "FacebookUser";
                                        break;
                                    } else if (userInfo.getProviderId().equals("google.com")) {
                                        provider = "GoogleUser";
                                        break;
                                    }
                                }
                                if (provider.isEmpty()) {
                                    provider = "User";
                                }
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User Profile")
                                        .child(provider)
                                        .child(userId)
                                        .child("userInfo");

                                databaseReference.child("profilePictureUrl").setValue(downloadUri.toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Profile picture uploaded successfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Failed to upload profile picture to the database", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        });
                                progressBar.setVisibility(View.GONE);

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to upload profile picture to Firebase Storage", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                });
            progressBar.setVisibility(View.GONE);

        }
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