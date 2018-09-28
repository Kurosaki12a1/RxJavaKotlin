package com.bku.jobs.ModelData

import com.google.gson.annotations.SerializedName

class JobData {

    @SerializedName("company_logo")
     var companyLogo: String ? = " "
    @SerializedName("how_to_apply")
    var howToApply: String ? = " "

    @SerializedName("created_at")
     var createdAt: String ? = " "

    var description: String ? = " "

     lateinit var location: String

     var company: String ? = " "

    @SerializedName("company_url")
     var companyUrl: String ? = " "

     lateinit var id: String

     var title: String ? = " "

     var type: String ? = " "

     var url: String ? = " "

    override fun toString(): String {
        return "JobData{" +
                "company_logo = '" + companyLogo + '\''.toString() +
                ",how_to_apply = '" + howToApply + '\''.toString() +
                ",created_at = '" + createdAt + '\''.toString() +
                ",description = '" + description + '\''.toString() +
                ",location = '" + location + '\''.toString() +
                ",company = '" + company + '\''.toString() +
                ",company_url = '" + companyUrl + '\''.toString() +
                ",id = '" + id + '\''.toString() +
                ",title = '" + title + '\''.toString() +
                ",type = '" + type + '\''.toString() +
                ",url = '" + url + '\''.toString() +
                "}"
    }
}
