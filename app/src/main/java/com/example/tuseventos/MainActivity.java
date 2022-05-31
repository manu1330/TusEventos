package com.example.tuseventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.tuseventos.requests.UserRequests;
import com.example.tuseventos.ui.AjustesFragment;
import com.example.tuseventos.ui.FavoritosFragment;
import com.example.tuseventos.ui.NoticiasFragment;
import com.example.tuseventos.ui.RecordatoriosFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tuseventos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private boolean loginActive=false;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_noticias, R.id.nav_favoritos, R.id.nav_recordatorios, R.id.nav_ajustes, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Preferences.getToken().equals("")) {
            // Si el usuario sale en el login, termina la app.
            if (loginActive) {
                finish();
            } else {
                loginActive = true;
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }

    public void onLogoutSuccess(){
        if (Preferences.getToken().equals("")) {
            // Si el usuario sale en el login, termina la app.
            loginActive = true;
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nav_logout){
                UserRequests.logout(this);
            return true;
        }

        NavigationUI.onNavDestinationSelected(item, navController);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}