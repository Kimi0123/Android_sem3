package com.example.ai36.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Correct way to enable edge to edge:
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    MainHeading()
                }
            }
        }
    }
}

@Composable
fun MainHeading() {
    Scaffold { innerPadding ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .background(color = Color.Red)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .background(color = Color.Gray)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .background(color = Color.Magenta)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewHeading() {
    MainHeading()
}
