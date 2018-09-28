package com.bku.jobs.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bku.jobs.Activity.JobDetailActivity
import com.bku.jobs.Database.OfflineDatabaseHelper
import com.bku.jobs.Fragment.FavoriteFragment
import com.bku.jobs.ModelData.JobData
import com.bku.jobs.Models.JobInfo
import com.bku.jobs.R
import com.bku.jobs.Util.UtilityJob
import com.bumptech.glide.Glide

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Welcome on 5/22/2018.
 */

class FavoriteJobAdapter(context: Context, lst: ArrayList<JobData>) : RecyclerView.Adapter<FavoriteJobAdapter.ViewHolder>() {

    var lstJobInfo: ArrayList<JobData>

    companion object {
        private  lateinit var ctx: Context
    }

    init {
        ctx = context
        lstJobInfo = ArrayList(lst)
        lstJobInfo = lst
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteJobAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_job_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteJobAdapter.ViewHolder, position: Int) {
        val jobInfo = lstJobInfo[position]
        Glide.with(ctx).load(jobInfo.companyLogo).fitCenter().into(holder.companyLogo)
        holder.titleJob?.text = jobInfo.title
        holder.typeJob?.text = "/ " + jobInfo.type
        holder.companyName?.text = "Company Name : " + jobInfo.company
        holder.createdAt?.text = jobInfo.createdAt
        holder.location?.text = "Location : " + jobInfo.location
        holder.itemView.setOnClickListener {
            val utilityJob = UtilityJob.getInstance()
            utilityJob.jobInfo = jobInfo

            ctx.startActivity(Intent(ctx, JobDetailActivity::class.java ))
        }
    }

    override fun getItemCount(): Int {
        return lstJobInfo.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var companyLogo: ImageView = itemView.findViewById(R.id.companyLogo)

        internal var titleJob: TextView? = itemView.findViewById(R.id.titleJob)

        internal var companyName: TextView? = itemView.findViewById(R.id.companyName)

        internal var typeJob: TextView? = itemView.findViewById(R.id.typeJob)

        internal var location: TextView? = itemView.findViewById(R.id.location)

        internal var createdAt: TextView? = itemView.findViewById(R.id.createdAt)

        init {

        }
    }


}
