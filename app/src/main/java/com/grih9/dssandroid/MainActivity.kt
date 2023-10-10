package com.grih9.dssandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSSAndroidTheme {
            Text("Hello Compose World!")
                HomeScreen()
            }
        }
    }
}