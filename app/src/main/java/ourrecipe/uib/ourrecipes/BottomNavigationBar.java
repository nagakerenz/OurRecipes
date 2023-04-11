    package ourrecipe.uib.ourrecipes;

    import android.os.Bundle;
    import android.os.Handler;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;

    import com.google.android.material.bottomnavigation.BottomNavigationView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;

    import ourrecipe.uib.ourrecipes.databinding.ActivityBottomNavigationBarBinding;
    import ourrecipe.uib.ourrecipes.ui.home.HomeFragment;

    public class BottomNavigationBar extends AppCompatActivity {
        private ActivityBottomNavigationBarBinding binding;
        private boolean isBackPressedOnce = false;
        private String inputTextName;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityBottomNavigationBarBinding.inflate(getLayoutInflater());
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

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.nav_home:
//                            selectedFragment = new HomeFragment();
//                            break;
//                        case R.id.nav_profile:
//                            selectedFragment = new ProfileFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("inputText", inputText);
//                            selectedFragment.setArguments(bundle);
//                            break;
//                    }
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFragment).commit();
//
//                    return true;
//                }
//            };


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