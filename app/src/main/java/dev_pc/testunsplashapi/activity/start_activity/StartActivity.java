package dev_pc.testunsplashapi.activity.start_activity;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import dev_pc.testunsplashapi.Fragment.NewPhotoFragment;
import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.model.Photo;

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
       toolbar.setNavigationIcon(R.drawable.ic_back_white24dp);
       toolbar.setNavigationOnClickListener(listener->{
           onBackPressed();
       });
    }


    private void addFragment(){
        NewPhotoFragment newPhotoFragment = new NewPhotoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.app_bar, newPhotoFragment).commit();
    }

    public void DownloadImage(Photo photo){
        Uri uri = Uri.parse(photo.getUrls().getRaw());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Long reference = downloadManager.enqueue(request);
    }
}
