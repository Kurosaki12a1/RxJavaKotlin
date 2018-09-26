package com.bku.jobs.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bku.jobs.Adapter.FavoriteJobAdapter
import com.bku.jobs.Database.OfflineDatabaseHelper
import com.bku.jobs.ModelData.JobData
import com.bku.jobs.Models.JobInfo
import com.bku.jobs.R

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Welcome on 5/21/2018.
 */

class FavoriteFragment : Fragment() {


    private var recyclerView = view!!.findViewById<RecyclerView>(R.id.rvFavoriteList)
    private var db: OfflineDatabaseHelper? = null
    private var adapter: FavoriteJobAdapter? = null
    private val mListener: FavoriteFragment.OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_job, container, false)
        db = OfflineDatabaseHelper(context!!)
        bindView()
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun bindView() {

        recyclerView!!.layoutManager = LinearLayoutManager(context)
        restoreFromDb()

        activity!!.supportFragmentManager.addOnBackStackChangedListener { restoreFromDb() }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    private fun restoreFromDb() {
        val lstJob = db!!.allJob
        adapter = FavoriteJobAdapter(activity!!, lstJob)
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        //Restore from db
        restoreFromDb()
    }

}// Required empty public constructor
