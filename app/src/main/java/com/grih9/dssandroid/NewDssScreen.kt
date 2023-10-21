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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
        modifier = modifier,
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
fun AddCandidate(input: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = R.string.candidate_to_add)) },
        singleLine = true,
        modifier = modifier.padding(top = 3.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddParameter(
    input: String,
    onValueChange: (String) -> Unit,
    parameterName: String,
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
        modifier = Modifier.fillMaxWidth()
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
            modifier = modifier.weight(1f)
        )
        Spacer(Modifier.width(5.dp))
        AddParameter(
            input = inputCoefficient,
            onValueChange = { inputCoefficient = it },
            parameterName = stringResource(id = R.string.coefficient_to_add),
            modifier = modifier.weight(1f)
        )
        Spacer(Modifier.width(5.dp))
        AddParameter(
            input = inputPriority,
            onValueChange = { inputPriority = it },
            parameterName = "preposition or ${stringResource(id = R.string.priority_direction)}",
            modifier = modifier.weight(1f)
        )
    }
    var text by remember { mutableStateOf("$inputParameter $inputCoefficient $inputPriority") }
    Text(text = text)
    Button(onClick = { text += "|\n $inputParameter $inputCoefficient $inputPriority" }) {
        Text(
            text = stringResource(id = R.string.add_parameter)
        )
    }

}