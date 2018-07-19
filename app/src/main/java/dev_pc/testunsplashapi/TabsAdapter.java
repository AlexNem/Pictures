package dev_pc.testunsplashapi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import dev_pc.testunsplashapi.Fragment.AbstractTabFragment;
import dev_pc.testunsplashapi.Fragment.CuratedFragment;
import dev_pc.testunsplashapi.Fragment.NewPhotoFragment;

public class TabsAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, NewPhotoFragment.getInstance(context));
        tabs.put(1, CuratedFragment.getInstance(context));
    }
}
