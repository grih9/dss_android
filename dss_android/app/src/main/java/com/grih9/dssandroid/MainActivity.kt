package com.grih9.dssandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DSSAndroidApp() {
    DSSAndroidTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center))
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSSAndroidApp()
        }
    }
}