package com.example.moodsync

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.moodsync.ui.theme.MoodSyncApp

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Set up the NavController and pass it to the MoodSyncApp
            val navController = rememberNavController()
            Surface(color = MaterialTheme.colorScheme.background) {
                MoodSyncApp(navController = navController)
            }
        }
    }
}
