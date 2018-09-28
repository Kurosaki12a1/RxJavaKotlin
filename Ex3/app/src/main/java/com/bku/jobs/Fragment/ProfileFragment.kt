package com.bku.jobs.Fragment


import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.bku.jobs.GetRelatedJobs
import com.bku.jobs.Models.JobInfo
import com.bku.jobs.R
import com.bku.jobs.Util.HttpHandler
import com.bku.jobs.Util.NotificationHelper

import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList
import java.util.Objects

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnItemSelected

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private var jobsList = ArrayList<JobInfo>()
    @BindView(R.id.edtName)
    private lateinit var edtName: EditText
    @BindView(R.id.edtPosition)
    private lateinit var edtPosition: EditText
    @BindView(R.id.edtLocation)
    private lateinit var edtLocation: EditText
    @BindView(R.id.btnSave)
    private lateinit var btnSave: Button
    @BindView(R.id.spinnerSchedule)
    private lateinit var spinner: Spinner


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        ButterKnife.bind(this, v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = spinner.getChildAt(0) as TextView
        text.setTextColor(resources.getColor(R.color.colorWhite))
    }

    @OnItemSelected(R.id.spinnerSchedule)
    fun spinnerItemSelected(spinner: Spinner) {
        try {
            (spinner.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.colorWhite))
        } catch (e: Exception) {
            // DO NOTHING
        }

    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val position = sharedPreferences.getString("position", "")
        val location = sharedPreferences.getString("location", "")
        val schedule = sharedPreferences.getInt("schedule", 0)

        edtName.setText(name)
        edtPosition.setText(position)
        edtLocation.setText(location)
        spinner.setSelection(schedule)

    }

    @OnClick(R.id.btnSave)
    fun btnSaveClick(v: View) {
        val sharedPreferences = requireContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", edtName.text.toString())
        editor.putString("position", edtPosition.text.toString())
        editor.putString("location", edtLocation.text.toString())
        editor.putInt("schedule", spinner.selectedItemPosition)
        editor.apply()

        Toast.makeText(context, "Profile saved!", Toast.LENGTH_LONG).show()

        val schedule = if (spinner.selectedItemPosition == 0) "fulltime" else "parttime"
        val url = ("https://jobs.search.gov/jobs/search.json?query="
                + schedule + "+" + edtPosition.text.toString() + "+jobs+at+" + edtLocation.text.toString())
        val getRelatedJobs = GetRelatedJobs(activity)
        getRelatedJobs.execute(url)
    }

    companion object {
        val TAG = "PROFILE_FRAGMENT"
    }

}// Required empty public constructor
