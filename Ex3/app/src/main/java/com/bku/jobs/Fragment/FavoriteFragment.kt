package com.bku.jobs.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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


    private  lateinit var recyclerView  : RecyclerView

    private lateinit var db: OfflineDatabaseHelper
    private lateinit var adapter: FavoriteJobAdapter
    private val mListener: FavoriteFragment.OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_job, container, false)
        db = OfflineDatabaseHelper(requireContext())
        Log.d("TESTING","SIZE JOB : "+db.sizedb())
        bindView(view)
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun bindView(view : View) {
        recyclerView=view.findViewById(R.id.rvFavoriteList)
        //recyclerView = view!!.findViewById(R.id.rvFavoriteList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        restoreFromDb()

        requireActivity().supportFragmentManager.addOnBackStackChangedListener { restoreFromDb() }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    private fun restoreFromDb() {
        val lstJob = db.allJob
        adapter = FavoriteJobAdapter(requireActivity(), lstJob)
        recyclerView?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        //Restore from db
        restoreFromDb()
    }

}// Required empty public constructor
