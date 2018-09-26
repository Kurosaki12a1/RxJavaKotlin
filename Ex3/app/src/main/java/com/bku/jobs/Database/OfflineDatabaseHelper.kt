package com.bku.jobs.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.bku.jobs.ModelData.JobData
import com.bku.jobs.Models.JobInfo

import java.util.ArrayList

/**
 * Created by Welcome on 5/21/2018.
 */

class OfflineDatabaseHelper : SQLiteOpenHelper {

    private var context: Context? = null

    // Select All Query
    val allJob: ArrayList<JobData>
        get() {
            val listJob = ArrayList<JobData>()
            val selectQuery = "SELECT * FROM " + JobInfo.TABLE_NAME

            val db = readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val lst = JobData()
                    lst.id = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_ID))
                    lst.createdAt = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_CreatedAt))
                    lst.title = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_Title))
                    lst.location = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_Location))
                    lst.type = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_Type))
                    lst.description = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_Description))
                    lst.howToApply = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_howToApply))
                    lst.company = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_Company))
                    lst.companyUrl = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_CompanyURL))
                    lst.companyLogo = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_CompanyLogo))
                    lst.url = cursor.getString(cursor.getColumnIndex(JobInfo.COLUMN_URL))
                    listJob.add(lst)
                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()

            return listJob
        }

    constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION) {
        this.context = context
    }

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : super(context, name, factory, version) {
        this.context = context
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(JobInfo.CREATE_TABLE)
        /*  sqLiteDatabase.execSQL(OfflinePlaylist.CREATE_TABLE);
        sqLiteDatabase.execSQL(SongPlayerOfflineInfo.CREATE_TABLE);*/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + JobInfo.TABLE_NAME)
        onCreate(db)
    }

    fun insertJob(job: JobData): Int {
        val db = writableDatabase
        val value = ContentValues()
        value.put(JobInfo.COLUMN_ID, job.id)
        value.put(JobInfo.COLUMN_CreatedAt, job.createdAt)
        value.put(JobInfo.COLUMN_Title, job.title)
        value.put(JobInfo.COLUMN_Location, job.location)
        value.put(JobInfo.COLUMN_Type, job.type)
        value.put(JobInfo.COLUMN_Description, job.description)
        value.put(JobInfo.COLUMN_howToApply, job.howToApply)
        value.put(JobInfo.COLUMN_Company, job.company)
        value.put(JobInfo.COLUMN_CompanyURL, job.companyUrl)
        value.put(JobInfo.COLUMN_CompanyLogo, job.companyLogo)
        value.put(JobInfo.COLUMN_URL, job.url)
        val id = db.insert(JobInfo.TABLE_NAME, null, value)
        db.close()
        return id.toInt()
    }

    fun updateJob(id: String, columnName: String, value: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(columnName, value.toString())
        db.update(JobInfo.TABLE_NAME, values, " Id = ? ", arrayOf(id))
    }

    fun deleteJob(id: String) {
        val db = writableDatabase
        //  delete the job id in table Jon
        db.delete(JobInfo.TABLE_NAME, "Id = ?", arrayOf(id))
        db.close()
    }

    fun checkExist(id: String): Boolean {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM " + JobInfo.TABLE_NAME + " WHERE " + JobInfo.COLUMN_ID + " ='" + id + "' "
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.close()
                return true
            }
            cursor.close()
        }
        return false
    }

    companion object {

        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "favorite_job_db"
    }


}
