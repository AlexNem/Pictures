package dev_pc.testunsplashapi.activity.user_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.CollectionsFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.LikesFragment;
import dev_pc.testunsplashapi.activity.user_activity.user_fragment.PhotosFragment;
import dev_pc.testunsplashapi.authentication.MySharedPreferences;
import dev_pc.testunsplashapi.authentication.OkhttpClient;
import dev_pc.testunsplashapi.authentication.ServiceRetrofit;

public class UserActivity extends AppCompatActivity implements
        PhotosFragment.OnClickFragmentPhotos

{

    private Button btnFollow;
    private FragmentTransaction transaction;
    private MySharedPreferences mySharedPreferences;
    private ServiceRetrofit serviceRetrofit;
    private OkhttpClient myClient;

    private CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initResources();
        initToolbar();
        initViewPager();
        setUserImage();
        setBtnFollow();

    }

    private void initResources(){
        btnFollow = findViewById(R.id.btn_follow);
        mySharedPreferences = new MySharedPreferences(this);
        serviceRetrofit = new ServiceRetrofit();
        myClient = new OkhttpClient(this);
        userImage = findViewById(R.id.user_profile_image);
    }

    private void initToolbar(){
        android.widget.Toolbar toolbar = findViewById(R.id.user_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        toolbar.setNavigationIcon(R.drawable.ic_back_white24dp);
        toolbar.setNavigationOnClickListener(listener -> onBackPressed());
    }

    private void initViewPager(){
        ViewPager viewPager = findViewById(R.id.user_profile_view_pager);
        TabLayout tabLayout = findViewById(R.id.user_tab_layout);
        UserPagerAdapter adapter = new UserPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PhotosFragment(), "Photos");
        adapter.addFragment(new LikesFragment(), "Likes");
        adapter.addFragment(new CollectionsFragment(), "Collections");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUserImage(){
    Picasso.with(this)
            .load("http://i.imgur.com/DvpvklR.png")
            .error(R.drawable.ic_favorite_red_24dp)
            .into(userImage);
}
    public void setBtnFollow(){
        btnFollow.setOnClickListener(listener->
            Toast.makeText(this, "Follow!", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void photosClick(Intent intent) {
        startActivity(intent);
    }
}
