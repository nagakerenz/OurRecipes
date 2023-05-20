package ourrecipe.uib.ourrecipes;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ourrecipe.uib.ourrecipes.databinding.CActivityBottomNavigationBarBinding;

public class BottomNavigationBar extends AppCompatActivity {
    private CActivityBottomNavigationBarBinding binding;
    private boolean isBackPressedOnce = false;
    private String inputTextName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = CActivityBottomNavigationBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_reels, R.id.navigation_search, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation_bar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        getSupportActionBar().hide();
    }


    @Override
    public void onBackPressed() {
        if (isBackPressedOnce){
            finishAffinity();
            finish();
            return;
        }

        Toast.makeText(BottomNavigationBar.this, "Press Again To Exit Apps!", Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);


    }
}