package com.bku.jobs.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bku.jobs.ModelData.JobData
import com.bku.jobs.Models.JobInfo
import com.bku.jobs.R
import com.bumptech.glide.Glide

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Huy on 05/23/18.
 */

class JobsAdapter(private val mContext: Context, list: ArrayList<JobData>) : ArrayAdapter<JobData>(mContext, 0, list) {
    private var jobList = ArrayList<JobData>()

    private lateinit var companyLogo: ImageView
    private lateinit var titleJob: TextView
    private lateinit var companyName: TextView
    private lateinit var typeJob: TextView
    private lateinit var location: TextView
    private lateinit var createdAt: TextView

    init {
        this.jobList = list
    }




    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem: View = LayoutInflater.from(mContext).inflate(R.layout.favorite_job_item, parent, false)
        companyLogo=listItem.findViewById(R.id.companyLogo)
        titleJob=listItem.findViewById(R.id.titleJob)
        companyName=listItem.findViewById(R.id.companyName)
        typeJob=listItem.findViewById(R.id.typeJob)
        location=listItem.findViewById(R.id.location)
        createdAt=listItem.findViewById(R.id.createdAt)

//
        val jobInfo = jobList[position]

      //  ButterKnife.bind(this, listItem)

        titleJob.text = jobInfo.title

        typeJob.text = "/ " + jobInfo.type

        companyName.text = "Company Name : " + jobInfo.company

        createdAt.text = jobInfo.createdAt

        location.text = "Location : " + jobInfo.location

        Glide.with(mContext).load(jobInfo.companyLogo).into(companyLogo)



        return listItem
    }

}
