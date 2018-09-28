package com.bku.jobs.Adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log

import com.bku.jobs.Fragment.FavoriteFragment
import com.bku.jobs.Fragment.HomeFragment
import com.bku.jobs.Fragment.ProfileFragment
import com.bku.jobs.Fragment.SearchFragment
import com.bku.jobs.Util.UtilityJob

/**
 * Created by Welcome on 5/21/2018.
 */

class MainFragmentPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment? {
        return  when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            else -> FavoriteFragment()

        }
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 3
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return when (position) {
            0 ->
                //return mContext.getString(R.string.category_usefulinfo);
                "Home"
            1 ->
                //return mContext.getString(R.string.category_usefulinfo);
                "Search"
            2 ->
                //return mContext.getString(R.string.category_places);
                "Favorite Job"
            else -> null

        }
    }
}

