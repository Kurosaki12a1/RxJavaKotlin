package com.bku.jobs

import com.bku.jobs.ModelData.JobData
import com.bku.jobs.ModelData.UserData.UserData

import java.util.ArrayList

/**
 * Created by Welcome on 7/15/2018.
 */

class DataZip(lst: List<JobData>, userData1: UserData) {

    var jobData: ArrayList<JobData>
        internal set
    var userData: UserData
        internal set

    init {
        this.jobData = ArrayList(lst)
        this.userData = userData1
    }


}
