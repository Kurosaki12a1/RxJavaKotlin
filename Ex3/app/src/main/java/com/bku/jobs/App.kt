package com.bku.jobs

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Notify only when app start
        val sharedPreferences = applicationContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
        val position = sharedPreferences.getString("position", "")
        val location = sharedPreferences.getString("location", "")
        val schedule = sharedPreferences.getInt("schedule", 0)
        val strSchedule = if (schedule == 0) "fulltime" else "parttime"
        val url = ("https://jobs.search.gov/jobs/search.json?query="
                + strSchedule + "+" + position + "+jobs+at+" + location)

        val getRelatedJobs = GetRelatedJobs(baseContext)
        getRelatedJobs.execute(url)

    }
}
