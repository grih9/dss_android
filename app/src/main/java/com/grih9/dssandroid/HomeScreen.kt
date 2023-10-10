package com.grih9.dssandroid
import com.grih9.dssandroid.ui.theme.DSSAndroidTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Box(contentAlignment = Center, modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
//        Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null)
//        Box(modifier = Modifier
//            .width(160.dp)
//            .height(120.dp)
//            .background(
//                brush = Brush.linearGradient(colors = listOf(Color.Green, Color.Yellow, Color.Red))
//            )
//            .border(
//                width = 2.dp,
//                brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Yellow, Color.Green)),
//                shape = RoundedCornerShape(16.dp)
//            )
//
//        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    DSSAndroidTheme(darkTheme = true) {
        Text(text = "2131")
        HomeScreen()
    }
}