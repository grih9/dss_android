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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme


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
    screenNameChange: () -> Unit = { },
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var resultType by remember { mutableStateOf("Common") }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (resultType) {
            "Common" -> CommonResults(nextResultType = { resultType = "Competitive" })
            "Competitive" -> CompetitiveResults(
                nextResultType = { resultType = "Blocking" },
                previousResultType = { resultType = "Common" }
            )

            "Blocking" -> BlockingResults(
                nextResultType = { resultType = "KMax" },
                previousResultType = { resultType = "Competitive" }
            )

            "KMax" -> KMaxResults(
                nextResultType = { resultType = "Dominant" },
                previousResultType = { resultType = "Blocking" }
            )

            "Dominant" -> DominanteResults(
                nextResultType = { resultType = "Common" },
                previousResultType = { resultType = "KMax" }
            )

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
            Text("Return")
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
fun CompetitiveResults(nextResultType: () -> Unit = { }, previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.competitive_result))
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
fun BlockingResults(nextResultType: () -> Unit = { }, previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.blocking_result))
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
fun DominanteResults(nextResultType: () -> Unit = { }, previousResultType: () -> Unit = { }) {
    Text(text = stringResource(id = R.string.dominante_result))
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
