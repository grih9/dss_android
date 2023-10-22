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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun NewDssScreenPreview() {
    DSSAndroidTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            NewDssScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}


@Composable
fun NewDssScreen(
    screenNameChange: () -> Unit = { },
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddParameterComposable()
        Spacer(modifier = Modifier.height(50.dp))
        AddCandidateComposable()
    }
    Row(
        modifier = modifier
            .fillMaxHeight()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = screenNameChange) {
            Text(
                text = stringResource(id = R.string.show_result)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCandidate(input: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = R.string.candidate_to_add)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        modifier = modifier.padding(top = 3.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddParameter(
    input: String,
    onValueChange: (String) -> Unit,
    parameterName: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
//    Column (
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//    ) {
//        Text(text = parameterName)
    TextField(
        value = input,
        onValueChange = onValueChange,
        label = { Text(parameterName) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.padding(top = 3.dp)
    )
//    }
}


@Composable
fun AddCandidateComposable(modifier: Modifier = Modifier) {
    var candidate by remember { mutableStateOf("") }
//    Text(
//        text = "${stringResource(id = R.string.candidate_to_add)} / name")
    AddCandidate(
        input = candidate,
        onValueChange = { candidate = it },
        modifier = modifier.fillMaxWidth()
    )

    var text by remember { mutableStateOf("") }
    Text(
        text = text
    )
    Button(
        onClick = { text += "|\n$candidate" },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(text = stringResource(id = R.string.add_candidate))
    }
}

@Composable
fun AddParameterComposable(modifier: Modifier = Modifier) {
    var inputParameter by remember { mutableStateOf("") }
    var inputCoefficient by remember { mutableStateOf("0.3") }
    var inputPriority by remember { mutableStateOf("1") }
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        AddParameter(
            input = inputParameter,
            onValueChange = { inputParameter = it },
            parameterName = stringResource(id = R.string.parameter_to_add),
            keyboardType = KeyboardType.Ascii,
            modifier = modifier.weight(1f)
        )
        Spacer(Modifier.width(5.dp))
        AddParameter(
            input = inputCoefficient,
            onValueChange = { inputCoefficient = it },
            parameterName = stringResource(id = R.string.coefficient_to_add),
            keyboardType = KeyboardType.Decimal,
            modifier = modifier.weight(1f)
        )
        Spacer(Modifier.width(5.dp))
        RadioButton(selected =false, onClick = { inputPriority = "0" })
        RadioButton(selected =true, onClick = {
            inputPriority = "1"
        })
//        AddParameter(
//            input = inputPriority,
//            onValueChange = ,
//            parameterName = "preposition or ${stringResource(id = R.string.priority_direction)}",
//            keyboardType = KeyboardType.NumberPassword,
//            modifier = modifier.weight(1f)
//        )
    }
    val inputCoefficientNumber = inputCoefficient.toDoubleOrNull() ?: 0.0
    val inputPriorityNumber = inputPriority.toInt()
    var text by remember {
        mutableStateOf(
            "$inputParameter " +
                    "$inputCoefficientNumber " +
                    "$inputPriorityNumber")
    }
    Text(text = text)
    Button(onClick = { text += "|\n $inputParameter $inputCoefficientNumber $inputPriorityNumber" }) {
        Text(
            text = stringResource(id = R.string.add_parameter)
        )
    }

}