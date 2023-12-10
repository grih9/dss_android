package com.grih9.dssandroid

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grih9.dssandroid.api.sendDominanceRequest
import com.grih9.dssandroid.api.sendKMaxRequest
import com.grih9.dssandroid.api.sendLockRequest
import com.grih9.dssandroid.api.sendSummaryRequest
import com.grih9.dssandroid.api.sendTournamentRequest
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme
import org.w3c.dom.Text


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ResultsScreenPreview() {
    DSSAndroidTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            ResultsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun ResultsScreen(
    variants: MutableState<List<String>> = mutableStateOf(listOf("f")),
    preferences: MutableState<List<String>> = mutableStateOf(listOf("f")),
    matrix: MutableState<List<List<Float>>> = mutableStateOf(listOf(listOf(1.2f))),
    weightCoefficients: MutableState<List<Float>> = mutableStateOf(listOf(1.0f)),
    choiceFunction: MutableState<List<Boolean>> = mutableStateOf(listOf(true)),
    screenNameChange: () -> Unit = { },
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    var resultType by remember { mutableStateOf("Common") }
    val response = remember { mutableStateOf("Пока нет данных") }
//    val response = remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (resultType) {
            "Common" -> {
                sendSummaryRequest(
                    ctx = ctx,
                    variants = variants,
                    preferences = preferences,
                    matrix = matrix,
                    weightCoefficients = weightCoefficients,
                    choiceFunction = choiceFunction,
                    result = response
                )
                CommonResults(result = response, nextResultType = {
                    response.value = "Пока нет данных"
                    resultType = "Dominance" })
            }
            "Tournament" -> {
                sendTournamentRequest(
                    ctx = ctx,
                    variants = variants,
                    preferences = preferences,
                    matrix = matrix,
                    weightCoefficients = weightCoefficients,
                    choiceFunction = choiceFunction,
                    result = response)
                TournamentResults(
                    resultData = response,
                    nextResultType = { response.value = "Пока нет данных"
                        resultType = "KMax" },
                    previousResultType = { response.value = "Пока нет данных"
                        resultType = "Blocking" }
                )
            }

            "Blocking" ->  {
                sendLockRequest(
                    ctx = ctx,
                    variants = variants,
                    preferences = preferences,
                    matrix = matrix,
                    weightCoefficients = weightCoefficients,
                    choiceFunction  = choiceFunction,
                    result = response)
                BlockingResults(
                    resultData = response,
                    nextResultType = { response.value = "Пока нет данных"
                        resultType = "Tournament" },
                    previousResultType = { response.value = "Пока нет данных"
                        resultType = "Dominance" }
                )
            }

            "KMax" -> {
                sendKMaxRequest(
                    ctx = ctx,
                    variants = variants,
                    preferences = preferences,
                    matrix = matrix,
                    weightCoefficients = weightCoefficients,
                    choiceFunction  = choiceFunction,
                    result = response
                )
                KMaxResults(
                    resultData = response,
                    nextResultType = { response.value = "Пока нет данных"
                        resultType = "Common" },
                    previousResultType = { response.value = "Пока нет данных"
                        resultType = "Tournament" }
                )
            }

            "Dominance" -> {
                sendDominanceRequest(
                    ctx = ctx,
                    variants = variants,
                    preferences = preferences,
                    matrix = matrix,
                    weightCoefficients = weightCoefficients,
                    choiceFunction = choiceFunction,
                    result = response)
                DominanceResults(
                    resultData = response,
                    nextResultType = { response.value = "Пока нет данных"
                        resultType = "Blocking" },
                    previousResultType = { response.value = "Пока нет данных"
                        resultType = "Common" }
                )
            }

            else -> throw Error("Invalid result type")
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = modifier
            .fillMaxHeight()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                variants.value = listOf<String>()
                preferences.value = listOf<String>()
                matrix.value = listOf(listOf<Float>())
                weightCoefficients.value = listOf<Float>()
                choiceFunction.value = listOf<Boolean>()
                screenNameChange()
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onErrorContainer)
        ) {
            Text(stringResource(id = R.string.back_button))
        }
    }
}

@Composable
fun CommonResults(result: MutableState<String>, nextResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.common_result),
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(start = 15.dp, end = 5.dp)) {
        Text(text = result.value,
            fontSize = 6.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = nextResultType) {
            Text(stringResource(id = R.string.next_button))
        }
    }
}

@Composable
fun TournamentResults(
    resultData: MutableState<String>,
    nextResultType: () -> Unit = { },
    previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.competitive_result),
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {
        Text(text = resultData.value)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = previousResultType) {
            Text(stringResource(id = R.string.previous_button))
        }
        Button(onClick = nextResultType) {
            Text(stringResource(id = R.string.next_button))
        }
    }
}

@Composable
fun BlockingResults(
    resultData: MutableState<String>,
    nextResultType: () -> Unit = { },
    previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.blocking_result),
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {
        Text(text = resultData.value)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = previousResultType) {
            Text(stringResource(id = R.string.previous_button))
        }
        Button(onClick = nextResultType) {
            Text(stringResource(id = R.string.next_button))
        }
    }
}

@Composable
fun KMaxResults(
    resultData: MutableState<String>,
    nextResultType: () -> Unit = { },
    previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.k_max_result),
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {
        Text(text = resultData.value)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = previousResultType) {
            Text(stringResource(id = R.string.previous_button))
        }
        Button(onClick = nextResultType) {
            Text(stringResource(id = R.string.next_button))
        }
    }
}

@Composable
fun DominanceResults(resultData: MutableState<String>,
                     nextResultType: () -> Unit = { },
                     previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.dominante_result),
        fontSize = 24.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 5.dp, end = 5.dp)) {
        Text(text = resultData.value)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = previousResultType) {
            Text(stringResource(id = R.string.previous_button))
        }
        Button(onClick = nextResultType) {
            Text(stringResource(id = R.string.next_button))
        }
    }
}
