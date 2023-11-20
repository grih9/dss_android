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
    var weight_coefficients: List<Float>,
    var choice_function: List<Boolean>
)

data class InfoResultModel(
    var info: String
)

data class ResponseModel(
    var data: List<String>
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
                Toast.makeText(ctx, "Data get from API " + (response.body()?.info ?: ""), Toast.LENGTH_SHORT).show()
                Log.d("Main", "success!" + response.body()?.info)
            }else {
                Toast.makeText(ctx, "Failed to get data" + response.code(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<InfoResultModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}

fun sendTournamentRequest(
    ctx: Context,
    variants: MutableState<List<String>>,
    preferences: MutableState<List<String>>,
    matrix: MutableState<List<List<Float>>>,
    weightCoefficients: MutableState<List<Float>>,
    choiceFunction: MutableState<List<Boolean>>,
    result: MutableState<String>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.107:8081")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dataModel = RawDataModel(
        variants = variants.value,
        preferences = preferences.value,
        matrix = matrix.value,
        weight_coefficients = weightCoefficients.value,
        choice_function = choiceFunction.value)
    val api = retrofit.create(MechanismApi::class.java)

    val call: Call<RawDataModel?>? = api.calculateTournament(dataModel);

    call!!.enqueue(object: Callback<RawDataModel?> {
        override fun onResponse(call: Call<RawDataModel?>, response: Response<RawDataModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Data get from API ", Toast.LENGTH_SHORT).show()
//                Log.d("Main", "success!" + response.body()?.info)
//                val model: RawDataModel? = response.body()!!.info
//                result.value = resp
                result.value = "123"
            } else {
                Toast.makeText(ctx, "Failed to get data" + response.code(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<RawDataModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}

fun sendLockRequest(ctx: Context,
                          variants: MutableState<List<String>>,
                          preferences: MutableState<List<String>>,
                          matrix: MutableState<List<List<Float>>>,
                          weightCoefficients: MutableState<List<Float>>,
                          choiceFunction: MutableState<List<Boolean>>,
                          result: MutableState<String>) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.107:8081")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dataModel = RawDataModel(
        variants = variants.value,
        preferences = preferences.value,
        matrix = matrix.value,
        weight_coefficients = weightCoefficients.value,
        choice_function = choiceFunction.value)
    val api = retrofit.create(MechanismApi::class.java)

    val call: Call<RawDataModel?>? = api.calculateLocking(dataModel);

    call!!.enqueue(object: Callback<RawDataModel?> {
        override fun onResponse(call: Call<RawDataModel?>, response: Response<RawDataModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Data get from API ", Toast.LENGTH_SHORT).show()
//                Log.d("Main", "success!" + response.body()?.info)
//                val model: RawDataModel? = response.body()!!.info
//                result.value = resp
                result.value = "123"
            } else {
                Toast.makeText(ctx, "Failed to get data" + response.code(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<RawDataModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}

fun sendDominanceRequest(ctx: Context,
                         variants: MutableState<List<String>>,
                         preferences: MutableState<List<String>>,
                         matrix: MutableState<List<List<Float>>>,
                         weightCoefficients: MutableState<List<Float>>,
                         choiceFunction: MutableState<List<Boolean>>,
                         result: MutableState<String>) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.107:8081")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dataModel = RawDataModel(
        variants = variants.value,
        preferences = preferences.value,
        matrix = matrix.value,
        weight_coefficients = weightCoefficients.value,
        choice_function = choiceFunction.value)
    val api = retrofit.create(MechanismApi::class.java)

    val call: Call<RawDataModel?>? = api.calculateDominance(dataModel);

    call!!.enqueue(object: Callback<RawDataModel?> {
        override fun onResponse(call: Call<RawDataModel?>, response: Response<RawDataModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Data get from API ", Toast.LENGTH_SHORT).show()
//                Log.d("Main", "success!" + response.body()?.info)
//                val model: RawDataModel? = response.body()!!.info
//                result.value = resp
                result.value = "123"
            } else {
                Toast.makeText(ctx, "Failed to get data" + response.code(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<RawDataModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}