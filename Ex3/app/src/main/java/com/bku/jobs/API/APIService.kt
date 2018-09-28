package com.bku.jobs.API

import com.bku.jobs.ModelData.JobData
import com.bku.jobs.ModelData.UserData.UserData

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Welcome on 7/11/2018.
 */

interface APIService {

    @GET("/positions.json")
    fun getJobData() : Observable<List<JobData>>

    @GET("/positions.json?")
    fun getSearchData(@Query("description") keySearch: String): Observable<List<JobData>>

    @GET("/api?inc=gender,name,nat,email,phone,cell,picture")
    fun getUserData(@Query("results") numResults: String): Observable<UserData>

}
