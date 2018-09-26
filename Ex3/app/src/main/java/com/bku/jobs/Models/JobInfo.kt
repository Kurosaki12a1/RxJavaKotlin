package com.bku.jobs.Models


import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Welcome on 5/21/2018.
 */

class JobInfo : Parcelable {

    var jobId: String? = null

    var jobCreatedAt: String? = null
    var jobTitle: String? = null
    var location: String? = null
    var type: String? = null
    var description: String? = null
    var howToApply: String? = null //url cua trang dang ky ungtuyen
    var company: String? = null // ten cong ty
    var companyURL: String? = null //url cua trang cong ty
    var companyLogo: String? = null
    var url: String? = null // url cua trang tren github

    constructor() {

    }

    constructor(jobId: String, jobCreatedAt: String, jobTitle: String, location: String, Type: String,
                description: String, howToApply: String, company: String, companyURL: String,
                companyLogo: String, URL: String) {
        this.jobId = jobId
        this.jobTitle = jobTitle
        this.jobCreatedAt = jobCreatedAt
        this.location = location
        this.type = Type
        this.description = description
        this.howToApply = howToApply
        this.company = company
        this.companyURL = companyURL
        this.companyLogo = companyLogo
        this.url = URL
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(this.jobId)
        parcel.writeString(this.jobTitle)
        parcel.writeString(this.jobCreatedAt)
        parcel.writeString(this.location)
        parcel.writeString(this.type)
        parcel.writeString(this.description)
        parcel.writeString(this.howToApply)
        parcel.writeString(this.company)
        parcel.writeString(this.companyURL)
        parcel.writeString(this.companyLogo)
        parcel.writeString(this.url)
    }

    constructor(`in`: Parcel) {
        this.jobId = `in`.readString()
        this.jobTitle = `in`.readString()
        this.jobCreatedAt = `in`.readString()
        this.location = `in`.readString()
        this.type = `in`.readString()
        this.description = `in`.readString()
        this.howToApply = `in`.readString()
        this.company = `in`.readString()
        this.companyURL = `in`.readString()
        this.companyLogo = `in`.readString()
        this.url = `in`.readString()
    }

    companion object {

        val TABLE_NAME = "Favorite_Job"

        val COLUMN_ID = "Id"

        val COLUMN_CreatedAt = "createdAt"

        val COLUMN_Title = "Title"

        val COLUMN_Location = "Location"

        val COLUMN_Type = "Type"

        val COLUMN_Description = "Description"

        val COLUMN_howToApply = "How_To_Apply"

        val COLUMN_Company = "Company_Name"

        val COLUMN_CompanyURL = "Website_of_Company"

        val COLUMN_CompanyLogo = "Company_Logo"

        val COLUMN_URL = "URL"

        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " TEXT PRIMARY KEY ," +
                COLUMN_Title + " TEXT, " + COLUMN_CreatedAt + " TEXT, " +
                COLUMN_Location + " TEXT, " + COLUMN_Type + " TEXT, " +
                COLUMN_Description + " TEXT, " + COLUMN_howToApply + " TEXT, " +
                COLUMN_Company + " TEXT, " + COLUMN_CompanyURL + " TEXT, " +
                COLUMN_CompanyLogo + " TEXT, " + COLUMN_URL + " TEXT " + ");"

        val CREATOR: Parcelable.Creator<*> = object : Parcelable.Creator {
            override fun createFromParcel(`in`: Parcel): JobInfo {
                return JobInfo(`in`)
            }

            override fun newArray(size: Int): Array<JobInfo> {
                return arrayOfNulls(size)
            }
        }
    }

}
