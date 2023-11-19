package com.grih9.dssandroid.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.apiexample.api.MechanismApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class RawDataModel(
    var variants: List<String>,
    var preferences: List<String>,
    var matrix: List<List<Float>>,
    var weightCoefficients: List<Float>,
    var choise_functions: List<Boolean>
)

data class InfoResultModel(
    var info: String
)

fun sendHelpRequest(ctx: Context) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.107:8081")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(MechanismApi::class.java)

    val call: Call<InfoResultModel?>? = api.getRoot();

    call!!.enqueue(object: Callback<InfoResultModel?> {
        override fun onResponse(call: Call<InfoResultModel?>, response: Response<InfoResultModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Data posted to API" + response.body().toString(), Toast.LENGTH_SHORT).show()
                Log.d("Main", "success!" + response.body().toString())
            }
        }

        override fun onFailure(call: Call<InfoResultModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}