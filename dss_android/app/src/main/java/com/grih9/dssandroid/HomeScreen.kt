package com.grih9.dssandroid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DSSAndroidApp2() {
    DSSAndroidTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var screen by remember { mutableStateOf("NewDssScreen") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(text = stringResource(id = R.string.app_name), fontSize = 24.sp)
//                        Image(
//                            painter = painterResource(id = R.drawable.dss_logo),
//                            contentDescription = "logo",
//                            modifier = modifier.size(15.dp)
//                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        when (screen) {
            "NewDssScreen" -> NewDssScreen(
                screenNameChange = { screen = "ResultsScreen" },
                modifier = modifier.padding(innerPadding)
            )

            "ResultsScreen" -> ResultsScreen(
                screenNameChange = { screen = "NewDssScreen" },
                modifier = modifier.padding(innerPadding)
            )

            else -> throw Error("Invalid screen")
        }
    }
}