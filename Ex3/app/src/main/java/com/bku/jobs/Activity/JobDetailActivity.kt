package com.bku.jobs.Activity

import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bku.jobs.Database.OfflineDatabaseHelper
import com.bku.jobs.ModelData.JobData
import com.bku.jobs.Models.JobInfo
import com.bku.jobs.R
import com.bku.jobs.Util.UlTagHandler
import com.bku.jobs.Util.UtilityJob

import org.w3c.dom.Text

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class JobDetailActivity : AppCompatActivity() {


    /* @BindView(R.id.liked)
     internal var liked: ImageView? = null //
     @BindView(R.id.jobTitle)
     internal var jobTitle: TextView? = null
     @BindView(R.id.companyName)
     internal var company: TextView? = null
     @BindView(R.id.jobType)
     internal var jobType: TextView? = null
     @BindView(R.id.location)
     internal var location: TextView? = null
     @BindView(R.id.jobCreated)
     internal var jobCreated: TextView? = null
     @BindView(R.id.jobDescription)
     internal var jobDescription: TextView? = null
     @BindView(R.id.applyBtn)
     internal var applyBtn: Button? = null
     @BindView(R.id.backBtn)
     internal var backBtn: ImageView? = null
     internal var howToApply: String = ""
     internal var db: OfflineDatabaseHelper? = null
     internal var jobInfo: JobData? = null*/


    private var liked = findViewById<ImageView>(R.id.liked)
    private var jobTitle = findViewById<TextView>(R.id.jobTitle)
    private var jobType = findViewById<TextView>(R.id.jobType)
    private var company = findViewById<TextView>(R.id.companyName)
    private var location = findViewById<TextView>(R.id.location)
    private var jobCreated = findViewById<TextView>(R.id.jobCreated)
    private var jobDescription = findViewById<TextView>(R.id.jobDescription)
    private var applyBtn = findViewById<Button>(R.id.applyBtn)
    private var backBtn = findViewById<ImageView>(R.id.backBtn)

    private var howToApply: String = " "

    private var db: OfflineDatabaseHelper? = null   //  ? = for null saferty
    private var jobInfo: JobData? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        //  ButterKnife.bind(this)

        //Declare Database

        db = OfflineDatabaseHelper(this@JobDetailActivity)

        //Get JobInfo

        jobInfo = UtilityJob.getInstance().jobInfo

        checkAlreadyLiked()

        initUI()


        backBtn.setOnClickListener { (finish()) }

        applyBtn.setOnClickListener {
            var builder: AlertDialog.Builder? = null
            val message = TextView(this@JobDetailActivity)
            message.setPadding(10, 5, 5, 0)
            message.text = fromHtml(howToApply)
            message.movementMethod = LinkMovementMethod.getInstance()
            builder = AlertDialog.Builder(this@JobDetailActivity)
            builder.setTitle(fromHtml("<b> How to apply: </b>"))
                    .setView(message)
                    .setPositiveButton(android.R.string.cancel) { dialog, which ->
                        // continue will delete
                    }
                    .show()
        }

        liked.setOnClickListener{
            if(db!!.checkExist(jobInfo!!.id)){
                db!!.deleteJob(jobInfo!!.id)
                liked.setImageDrawable(getDrawable(R.drawable.ic_fav_video))
            }
            else{
                db!!.insertJob(jobInfo!!)
                liked.setImageDrawable(getDrawable(R.drawable.ic_faved_album))
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkAlreadyLiked() {
        if (db!!.checkExist(jobInfo!!.id)) {
            liked.setImageDrawable(getDrawable(R.drawable.ic_faved_album))
        } else {
            liked.setImageDrawable(getDrawable(R.drawable.ic_fav_video))
        }
    }

    private fun initUI() {
        jobTitle.text= jobInfo!!.title
        company.text = jobInfo!!.company
        jobType.text = jobInfo!!.type
        location.text = jobInfo!!.location
        jobCreated.text = jobInfo!!.createdAt
        jobDescription.text = fromHtml(jobInfo!!.description)
        jobDescription.movementMethod = LinkMovementMethod.getInstance()
        howToApply = jobInfo!!.howToApply

    }

    companion object {

        fun fromHtml(html: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, null, UlTagHandler())
            } else {
                Html.fromHtml(html, null, UlTagHandler())
            }
        }
    }


}
