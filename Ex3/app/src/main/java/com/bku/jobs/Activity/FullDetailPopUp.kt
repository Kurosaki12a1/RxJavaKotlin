package com.bku.jobs.Activity

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics

import com.bku.jobs.R

/**
 * Created by Welcome on 5/22/2018.
 */

class FullDetailPopUp : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_popup)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)


        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * .8).toInt(), (height * .4).toInt())
    }
}
