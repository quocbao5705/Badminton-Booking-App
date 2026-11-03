package com.example.badmintonbookingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.badmintonbookingapp.ui.navigation.BadmiAppScaffold
import com.example.badmintonbookingapp.ui.theme.BadmintonBookingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BadmintonBookingAppTheme {
                BadmiAppScaffold()
            }
        }
    }
}