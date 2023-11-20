package com.grih9.dssandroid

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grih9.dssandroid.api.sendDominanceRequest
import com.grih9.dssandroid.api.sendLockRequest
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
    val response = remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (resultType) {
            "Common" -> CommonResults(nextResultType = { resultType = "Tournament" })
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
                    nextResultType = { resultType = "Blocking" },
                    previousResultType = { resultType = "Common" }
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
                    nextResultType = { resultType = "KMax" },
                    previousResultType = { resultType = "Tournament" }
                )
            }

            "KMax" -> KMaxResults(
                nextResultType = { resultType = "Dominance" },
                previousResultType = { resultType = "Blocking" }
            )

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
                    nextResultType = { resultType = "Common" },
                    previousResultType = { resultType = "KMax" }
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
            onClick = screenNameChange,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onErrorContainer)
        ) {
            Text(stringResource(id = R.string.back_button))
        }
    }
}

@Composable
fun CommonResults(nextResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.common_result))
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
    Text(text = stringResource(id = R.string.competitive_result))
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = resultData.value)
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
    Text(text = stringResource(id = R.string.blocking_result))
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = resultData.value)
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
fun KMaxResults(nextResultType: () -> Unit = { }, previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.k_max_result))
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
    Text(text = stringResource(id = R.string.dominante_result))
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = resultData.value)
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
fun CommonResults6(previousResultType: () -> Unit = { }) {
    Text("This is common results")
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = previousResultType) {
            Text(stringResource(id = R.string.previous_button))
        }
    }
}
