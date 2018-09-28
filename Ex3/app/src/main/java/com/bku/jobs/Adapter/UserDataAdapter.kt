package com.bku.jobs.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bku.jobs.ModelData.UserData.ResultsItem
import com.bku.jobs.R
import com.bumptech.glide.Glide

import java.util.ArrayList

import javax.xml.transform.Result

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Welcome on 7/15/2018.
 */

class UserDataAdapter(context: Context, private var lstResult: ArrayList<ResultsItem>) : RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {


    companion object {
        private lateinit var ctx : Context
    }

    init {
        ctx = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataAdapter.ViewHolder, position: Int) {
        val data = lstResult[position]
        Glide.with(ctx).load(data.picture.thumbnail).into(holder.avatarUser)
        holder.nameUser.text = "Name : " + data.name.first + " " + data.name.last
        holder.genderUser.text = "Gender : " + data.gender
        holder.emailUser.text = "Email : " + data.email
        holder.phoneUser.text = "Phone : " + data.phone
        holder.cellUser.text = "Cell " + data.cell
        holder.natUser.text = "Nation " + data.nat
    }

    override fun getItemCount(): Int {
        return lstResult.size
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal  var avatarUser = itemView.findViewById<ImageView>(R.id.avatarUser)
        internal  var nameUser: TextView = itemView.findViewById(R.id.nameUser)
        internal  var genderUser: TextView = itemView.findViewById(R.id.genderUser)
        internal  var emailUser: TextView = itemView.findViewById(R.id.emailUser)
        internal  var phoneUser: TextView = itemView.findViewById(R.id.phoneUser)
        internal  var cellUser: TextView = itemView.findViewById(R.id.cellUser)
        internal  var natUser: TextView = itemView.findViewById(R.id.natUser)


        init {

        }
    }


}
