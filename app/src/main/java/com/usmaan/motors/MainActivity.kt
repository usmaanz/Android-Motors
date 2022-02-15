package com.usmaan.motors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.usmaan.motors.ui.theme.MotorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotorsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SearchScreen()
                }
            }
        }
    }
}