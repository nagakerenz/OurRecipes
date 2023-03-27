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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    Button favorites, notification, account, saveButton;
    TextView displayedName;
    ImageView userPicture;
    ImageButton uploadImage;
    Uri uri;
    String imageURL;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

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
        saveButton = (Button) root.findViewById(R.id.save);
        displayedName = (TextView) root.findViewById(R.id.displayName);
        userPicture = (ImageView) root.findViewById(R.id.displayPicture);
        uploadImage = (ImageButton) root.findViewById(R.id.editProfilePicture);

        //This is for handling Displayed UserName
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("name", "");
        if (!TextUtils.isEmpty(userName)) {
            displayedName.setText(userName);
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
                        for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                            User userProfile = itemSnapShot.getValue(User.class);
                            if (userProfile.getUserId().equals(userId)) {
                                name = userProfile.getName();
                                break;
                            }
                        }
                        displayedName.setText(name);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                }
            };

            databaseReference.addValueEventListener(eventListener);
        }

        //THIS IS FOR HANDLING THE UPLOAD IMAGE TO FIREBASE DATA
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        userPicture.setImageURI(uri);
                    } else {
                        Toast.makeText(ProfileFragment.this.getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        //THIS IS FOR HANDLING USER DISPLAYED NAME
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

    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User Profile Picture")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                Toast.makeText(ProfileFragment.this.getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileFragment.this.getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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