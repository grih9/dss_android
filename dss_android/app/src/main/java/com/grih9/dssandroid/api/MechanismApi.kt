package com.example.apiexample.api


import com.grih9.dssandroid.api.InfoResultModel
import com.grih9.dssandroid.api.KMaxResultModel
import com.grih9.dssandroid.api.RawDataModel
import com.grih9.dssandroid.api.ResponseResultModel
import com.grih9.dssandroid.api.SummaryModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface MechanismApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("/")
    fun getRoot(): Call<InfoResultModel?>?

    @Headers(
        "Accept: application/json"
    )
    @POST("mechanism/tournament")
    fun calculateTournament(@Body data: RawDataModel): Call<ResponseResultModel?>?

    @Headers(
        "Accept: application/json"
    )
    @POST("mechanism/locking")
    fun calculateLocking(@Body data: RawDataModel): Call<ResponseResultModel?>?

    @Headers(
        "Accept: application/json"
    )
    @POST("mechanism/dominance")
    fun calculateDominance(@Body data: RawDataModel): Call<ResponseResultModel?>?

    @Headers(
        "Accept: application/json"
    )
    @POST("mechanism/kmax")
    fun calculateKmax(@Body data: RawDataModel): Call<KMaxResultModel?>?

    @Headers(
        "Accept: application/json"
    )
    @POST("summary")
    fun get_summary(@Body data: RawDataModel): Call<SummaryModel?>?

}