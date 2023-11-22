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

data class SummaryModel(
    var summary: String
)

data class ResponseModel(
    var data: List<String>
)
data class MechanismResultsModel(
    var res_by_variant: Map<String, List<Any>>
)

data class KmaxMechanismResultsModel(
    var rating_and_place_by_variant: Map<String, List<Any>>,
    var rating_and_place_by_variant_with_optimal: Map<String, List<Any>>
)

data class ResponseResultModel(
    var type: String,
//    var summary_result: Map<String, String>,
    var result_by_variant: MechanismResultsModel
)

data class KMaxResultModel(
    var type: String,
    var result_by_variant: KmaxMechanismResultsModel,
    var result_by_variant_optimal: KmaxMechanismResultsModel
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
            } else {
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

    val call: Call<ResponseResultModel?>? = api.calculateTournament(dataModel);

    call!!.enqueue(object: Callback<ResponseResultModel?> {
        override fun onResponse(
            call: Call<ResponseResultModel?>,
            response: Response<ResponseResultModel?>
        ) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
                val res = response.body()?.result_by_variant?.res_by_variant.toString()
                val data = res.split("], ")
                var result_string = ""
                val iter = data.iterator()
                while (iter.hasNext()) {
                    val it = iter.next()
                    val inner = it.split("=[")
                    result_string += "Вариант \"" + inner[0].replace("{", "") + "\" набрал "
                    val points = inner[1].split(", ")
                    result_string += points[0] + " очков и занял " + points[1]
                        .replace("}", "")
                        .replace("]", "").toFloat().toInt() + " место\n"
                }
                result.value = result_string
            } else {
                Toast.makeText(ctx, "Failed to get data " + response.code() + ": " + response.message(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<ResponseResultModel?>, t: Throwable) {
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

    val call: Call<ResponseResultModel?>? = api.calculateLocking(dataModel);

    call!!.enqueue(object: Callback<ResponseResultModel?> {
        override fun onResponse(call: Call<ResponseResultModel?>, response: Response<ResponseResultModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
//                Log.d("Main", "success!" + response.body()?.info)
//                val model: RawDataModel? = response.body()!!.info
//                result.value = resp
                val res = response.body()?.result_by_variant?.res_by_variant.toString()
                val data = res.split("], ")
                var result_string = ""
                val iter = data.iterator()
                while (iter.hasNext()) {
                    val it = iter.next()
                    val inner = it.split("=[")
                    result_string += "Вариант \"" + inner[0].replace("{", "") + "\" набрал "
                    val points = inner[1].split(", ")
                    result_string += points[0] + " очков и занял " + points[1]
                        .replace("}", "")
                        .replace("]", "").toFloat().toInt() + " место\n"
                }
                result.value = result_string
            } else {
                Toast.makeText(ctx, "Failed to get data " + response.code() + ": " + response.message(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<ResponseResultModel?>, t: Throwable) {
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

    val call: Call<ResponseResultModel?>? = api.calculateDominance(dataModel);

    call!!.enqueue(object: Callback<ResponseResultModel?> {
        override fun onResponse(call: Call<ResponseResultModel?>, response: Response<ResponseResultModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
//                Log.d("Main", "success!" + response.body()?.info)
//                val model: RawDataModel? = response.body()!!.info
//                result.value = resp
                val res = response.body()?.result_by_variant?.res_by_variant.toString()
                val data = res.split("], ")
                var result_string = ""
                val iter = data.iterator()
                while (iter.hasNext()) {
                    val it = iter.next()
                    val inner = it.split("=[")
                    result_string += "Вариант \"" + inner[0].replace("{", "") + "\" набрал "
                    val points = inner[1].split(", ")
                    result_string += points[0] + " очков и занял " + points[1]
                        .replace("}", "")
                        .replace("]", "").toFloat().toInt() + " место\n"
                }
                result.value = result_string
            } else {
                Toast.makeText(ctx, "Failed to get data " + response.code() + ": " + response.message(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<ResponseResultModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}

fun sendKMaxRequest(ctx: Context,
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

    val call: Call<KMaxResultModel?>? = api.calculateKmax(dataModel);

    call!!.enqueue(object: Callback<KMaxResultModel?> {
        override fun onResponse(call: Call<KMaxResultModel?>, response: Response<KMaxResultModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Data get from API ", Toast.LENGTH_SHORT).show()
//                Log.d("Main", "success!" + response.body()?.info)
//                val model: RawDataModel? = response.body()!!.info
//                result.value = resp
                var res = response.body()?.result_by_variant?.rating_and_place_by_variant.toString()
                var data = res.split("], ")
                var result_string = ""
                var iter = data.iterator()
                while (iter.hasNext()) {
                    val it = iter.next()
                    val inner = it.split("=[")
                    result_string += "Вариант \"" + inner[0].replace("{", "") + "\" набрал "
                    val points = inner[1].split(", ")
                    result_string += points[0] + " очков и занял " + points[1]
                        .replace("}", "")
                        .replace("]", "").toFloat().toInt() + " место\n"
                }
                result_string += "С учетом оптимальности:\n"
                res = response.body()?.result_by_variant?.rating_and_place_by_variant_with_optimal.toString()
                data = res.split("], ")
                iter = data.iterator()
                while (iter.hasNext()) {
                    val it = iter.next()
                    val inner = it.split("=[")
                    result_string += "Вариант \"" + inner[0].replace("{", "") + "\" набрал "
                    val points = inner[1].split(", ")
                    result_string += points[0] + " очков и занял " + points[1]
                        .replace("}", "")
                        .replace("]", "").toFloat().toInt() + " место\n"
                }
                result.value = result_string
            } else {
                Toast.makeText(ctx, "Failed to get data" + response.code() + ": " + response.message(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<KMaxResultModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}

fun sendSummaryRequest(ctx: Context,
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

    val call: Call<SummaryModel?>? = api.get_summary(dataModel);

    call!!.enqueue(object: Callback<SummaryModel?> {
        override fun onResponse(call: Call<SummaryModel?>, response: Response<SummaryModel?>) {
            if(response.isSuccessful) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
                result.value = response.body()?.summary.toString()
            } else {
                Toast.makeText(ctx, "Failed to get data " + response.code() + ": " + response.message(), Toast.LENGTH_SHORT).show()
            }
            return
        }

        override fun onFailure(call: Call<SummaryModel?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT).show()
            return
        }
    })
//    Toast.makeText(ctx, "Failed to get data. Unreachable host", Toast.LENGTH_SHORT).show()
}