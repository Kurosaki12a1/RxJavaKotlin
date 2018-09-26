package com.bku.jobs.Activity

import android.graphics.Color
import android.net.Uri
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.bku.jobs.Adapter.MainFragmentPagerAdapter
import com.bku.jobs.Fragment.FavoriteFragment
import com.bku.jobs.Fragment.HomeFragment
import com.bku.jobs.Fragment.SearchFragment
import com.bku.jobs.R

import java.sql.BatchUpdateException
import java.util.ArrayList

import devlight.io.library.ntb.NavigationTabBar

class MainScreenActivity : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener, FavoriteFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {


    private var mainFragmentPagerAdapter: MainFragmentPagerAdapter? = null
    private var mainViewPager= findViewById<ViewPager>(R.id.vp_horizontal_ntb)
    private var txtStatus = findViewById<TextView>(R.id.txtTab)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewPager = findViewById(R.id.vp_horizontal_ntb)
        mainViewPager.offscreenPageLimit = 4
        mainFragmentPagerAdapter = MainFragmentPagerAdapter(this, supportFragmentManager)
        mainViewPager.adapter = mainFragmentPagerAdapter
        initUI()

    }

    private fun initUI() {
        val colors = resources.getStringArray(R.array.default_preview)

        val navigationTabBar = findViewById<View>(R.id.ntb_horizontal) as NavigationTabBar
        val models = ArrayList<NavigationTabBar.Model>()
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_transparent_home),
                        Color.parseColor(colors[0]))
                        .title("Home")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_search),
                        Color.parseColor(colors[2]))
                        .title("Search")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_heart),
                        Color.parseColor(colors[1]))
                        .title("Your Favorite Job")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_profile_transparent),
                        Color.parseColor(colors[3]))
                        .title("Profile")
                        .build()
        )

        navigationTabBar.models = models
        navigationTabBar.setViewPager(mainViewPager, 0)
        //set Default Item


        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.isBehaviorEnabled = true


        navigationTabBar.onTabBarSelectedIndexListener = object : NavigationTabBar.OnTabBarSelectedIndexListener {
            override fun onStartTabSelected(model: NavigationTabBar.Model, index: Int) {

            }

            override fun onEndTabSelected(model: NavigationTabBar.Model, index: Int) {
                model.hideBadge()
            }
        }
        navigationTabBar.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    txtStatus.text = "Home"
                } else if (position == 1) {
                    txtStatus.text = "Search"
                } else if (position == 2) {
                    txtStatus.text = "Your Favorite Job"
                } else {
                    txtStatus.text = "Profile"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


    }


    override fun onFragmentInteraction(uri: Uri) {}
}
