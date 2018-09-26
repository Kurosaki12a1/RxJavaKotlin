package com.bku.jobs.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.bku.jobs.Fragment.FavoriteFragment;
import com.bku.jobs.Fragment.HomeFragment;
import com.bku.jobs.Fragment.ProfileFragment;
import com.bku.jobs.Fragment.SearchFragment;
import com.bku.jobs.Util.UtilityJob;

/**
 * Created by Welcome on 5/21/2018.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public MainFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new SearchFragment();
        } else if (position == 2) {
            return new FavoriteFragment();
        } else {
            return new ProfileFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                //return mContext.getString(R.string.category_usefulinfo);
                return "Home";
            case 1:
                //return mContext.getString(R.string.category_usefulinfo);
                return "Search";
            case 2:
                //return mContext.getString(R.string.category_places);
                return "Favorite Job";
            case 3:
                return "Profile";
            default:
                return null;
        }
    }
}

