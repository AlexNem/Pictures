package dev_pc.testunsplashapi.activity.start_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import dev_pc.testunsplashapi.Fragment.NewPhotoFragment;
import dev_pc.testunsplashapi.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.DefaultBlack);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        addFragment();
        initToolbaar();
    }

    private void initToolbaar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void addFragment(){
        NewPhotoFragment newPhotoFragment = new NewPhotoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.app_bar, newPhotoFragment).commit();
    }
}
