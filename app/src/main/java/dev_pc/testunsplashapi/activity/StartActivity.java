package dev_pc.testunsplashapi.activity;

import  android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.TabsAdapter;
import dev_pc.testunsplashapi.util.Constants;

public class StartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private PublicAccess publicAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.DefaultBlack);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        publicAccess = new PublicAccess();

        initToolbaar();
        initNawigationView();
//        initViewPager();
        showPublic();

    }

    private void showPublic(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.drawer_layout, publicAccess);
        transaction.commit();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.view_pager);
        TabsAdapter adapter = new TabsAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNawigationView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.view_navigation_open, R.string.view_navigation_close);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.home:
                        showNewTab();
                }
                return true;
            }
        });
    }

    private void initToolbaar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void showNewTab(){
        viewPager.setCurrentItem(Constants.NEW_TAB);
    }

}
