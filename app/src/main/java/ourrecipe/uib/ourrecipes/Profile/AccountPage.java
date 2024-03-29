package ourrecipe.uib.ourrecipes.Profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import ourrecipe.uib.ourrecipes.AccountPage.ChangeEmail;
import ourrecipe.uib.ourrecipes.AccountPage.ChangePassword;
import ourrecipe.uib.ourrecipes.AccountPage.LoginPage;
import ourrecipe.uib.ourrecipes.AccountPage.User;
import ourrecipe.uib.ourrecipes.R;
import ourrecipe.uib.ourrecipes.ui.profile.ProfileFragment;

public class AccountPage extends AppCompatActivity {
    ImageView userPicture;
    TextView displayedName, displayedEmail, displayedPassword, displayedAge;
    ImageButton uploadImage, nameEdit, emailEdit, passwordEdit, ageEdit ;
    Button logout, delete;
    FirebaseAuth mAuth;
    FirebaseUser user;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    Uri uri;
    String imageURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account_page);

        userPicture = findViewById(R.id.displayPicture);
        displayedName = findViewById(R.id.displayName);
        displayedEmail = findViewById(R.id.displayEmail);
        displayedPassword = findViewById(R.id.displayPass);
        displayedAge = findViewById(R.id.displayBirth);

        uploadImage = findViewById(R.id.editProfilePicture);
        nameEdit = findViewById(R.id.editName);
        emailEdit = findViewById(R.id.editEmail);
        passwordEdit = findViewById(R.id.editPass);
        ageEdit = findViewById(R.id.editBirth);

        logout = findViewById(R.id.logOut);
        delete = findViewById(R.id.deleteAccount);

        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

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
                        Toast.makeText(AccountPage.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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

        //This is for handling Displayed UserName
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("name", "");
        String userEmail = prefs.getString("email", "");
        String userAge = prefs.getString("age", "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userAge)) {
            displayedName.setText(userName);
            displayedEmail.setText(userEmail);
            displayedAge.setText(userAge);
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
                        String email = "";
                        String age = "";
                        for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                            User userProfile = itemSnapShot.getValue(User.class);
                            if (userProfile.getUserId().equals(userId)) {
                                name = userProfile.getName();
                                email = userProfile.getEmail();
                                age = userProfile.getAge();
                                break;
                            }
                        }
                        displayedName.setText(name);
                        displayedEmail.setText(email);
                        displayedAge.setText(age);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AccountPage.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
                }
            };
            databaseReference.addValueEventListener(eventListener);
        }

        //This uses a button to save it into the firebase, currently im working on something else with this problem
//        saveProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveImage();
//            }
//        });

        emailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailPage();
            }
        });

        passwordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPasswordPage();
            }
        });

        user = mAuth.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getInstance().signOut();
                gsc.signOut();
                Toast.makeText(AccountPage.this, "Account Logout!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountPage.this, LoginPage.class));
                finish(); // prevent the user from returning to the main activity via the back button
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountPage.this, "Account Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Currently this uses a button to save it into the firebase currently im trying to use another way
//    public void saveImage() {
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User Profile Picture")
//                .child(uri.getLastPathSegment());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.getActivity());
//        builder.setCancelable(false);
//        builder.setView(R.layout.progress_layout);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete());
//                Uri urlImage = uriTask.getResult();
//                imageURL = urlImage.toString();
//                Toast.makeText(ProfileFragment.this.getActivity(), "Saved", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ProfileFragment.this.getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//    }
//
    public void openEmailPage() {
        Intent email = new Intent(this, ChangeEmail.class);
        startActivity(email);
    }

    public void openPasswordPage() {
        Intent password = new Intent(this, ChangePassword.class);
        startActivity(password);
    }
}