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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
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
    variants: MutableState<List<String>> = mutableStateOf(listOf("f")),
    preferences: MutableState<List<String>> = mutableStateOf(listOf("f")),
    matrix: MutableState<List<List<Float>>> = mutableStateOf(listOf(listOf(1.2f))),
    weightCoefficients: MutableState<List<Float>> = mutableStateOf(listOf(1.0f)),
    choiceFunction: MutableState<List<Boolean>> = mutableStateOf(listOf(true)),
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
        AddParameterComposable(preferences, weightCoefficients, choiceFunction)
        Spacer(modifier = Modifier.height(30.dp))
        AddCandidateComposable(variants)
        Spacer(modifier = Modifier.height(30.dp))
        AddMatrixRowComposable(matrix)
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
private fun AddMatrixRow(input: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = R.string.matrix_row)) },
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
fun AddMatrixRowComposable(matrix: MutableState<List<List<Float>>>,
                           modifier: Modifier = Modifier) {
    var matrix_row by remember { mutableStateOf("") }
    AddMatrixRow(
        input = matrix_row,
        onValueChange = { matrix_row = it },
        modifier = modifier.fillMaxWidth()
    )

    var text by remember { mutableStateOf("") }
    Text(
        text = text
    )
    val matrixList : List<String> = matrix_row.split(" ")
    Button(
        onClick = {
            text += "|\n$matrix_row"
            matrix_row = ""
            val matrixListFloat = matrixList.map { it.toFloat() }
            if (matrix.value[0] == listOf<Float>()) {
                matrix.value = listOf(matrixListFloat)
            } else {
                matrix.value = matrix.value + listOf(matrixListFloat)
            }
        }
    ) {
        Text(text = stringResource(id = R.string.add_matrix_row))
    }
}


@Composable
fun AddCandidateComposable(variants: MutableState<List<String>>,
                           modifier: Modifier = Modifier) {
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
        onClick = {
            text += "|\n$candidate"
            variants.value = variants.value + listOf(candidate)
            candidate = ""
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(text = stringResource(id = R.string.add_candidate))
    }
}

@Composable
fun AddParameterComposable(preferences: MutableState<List<String>>,
                           weightCoefficients: MutableState<List<Float>>,
                           choiceFunction: MutableState<List<Boolean>>,
                           modifier: Modifier = Modifier) {
    var inputParameter by remember { mutableStateOf("") }
    var inputCoefficient by remember { mutableStateOf("0.3") }
    var inputPriority by remember { mutableStateOf(true) }
//    var textPriority by remember { mutableStateOf("T") }
    val radioOptions = listOf("True", "False")

    var selectedItem by remember { mutableStateOf(radioOptions[0]) }
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
        inputPriority = selectedItem.toBoolean()
        Row(modifier = Modifier.selectableGroup()) {
            radioOptions.forEach { label ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .selectable(
                            selected = (selectedItem == label),
                            onClick = { selectedItem = label },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 3.dp),
                ) {
                    Text(text = label)
                    RadioButton(
                        selected = (selectedItem == label),
                        onClick = null
                    )

                }
            }
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "True")
//                RadioButton(
//                    // inside this method we are
//                    // adding selected with a option.
//                    selected = (textPriority == "T"),
//                    onClick = {
//                    }
//                )
//            }
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                //            RadioButton(selected =false, onClick = { inputPriority = "0" })
//                Text(text = "False")
//                RadioButton(selected = true, onClick = {
//                    inputPriority = "1"
//                })
//            }
        }

//        AddParameter(
//            input = inputPriority,
//            onValueChange = ,
//            parameterName = "preposition or ${stringResource(id = R.string.priority_direction)}",
//            keyboardType = KeyboardType.NumberPassword,
//            modifier = modifier.weight(1f)
//        )
    }
    val inputCoefficientNumber = inputCoefficient.toFloatOrNull() ?: 0.0
//    val inputPriorityNumber = inputPriority.toInt()
    var text by remember { mutableStateOf("" ) }
    Text(text = text)
    Button(onClick = {
        text += "|\n $inputParameter $inputCoefficientNumber $inputPriority"
        preferences.value = preferences.value + listOf(inputParameter)
        weightCoefficients.value = weightCoefficients.value + listOf(inputCoefficient.toFloat())
        choiceFunction.value = choiceFunction.value + listOf(inputPriority)
        inputParameter = ""
        selectedItem = "True"
    }) {
        Text(
            text = stringResource(id = R.string.add_parameter)
        )
    }

}