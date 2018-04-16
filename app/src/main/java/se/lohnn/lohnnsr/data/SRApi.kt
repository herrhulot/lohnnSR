package se.lohnn.lohnnsr.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SRApi {
    @GET("api/v2/programs?format=json&size=40")
    fun programs(@Query("page") page: Int = 1): Call<SRData>
}