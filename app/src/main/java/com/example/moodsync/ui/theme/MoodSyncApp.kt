package com.example.moodsync.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoodSyncApp(navController: NavHostController) {
    // Create a NavController
    val navController = rememberNavController()

    // Set up the navigation logic using NavHost
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("period_tracker") {
            PeriodTrackerScreen(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val moods = listOf("😊 Happy", "😢 Sad", "😠 Angry", "😌 Calm", "😰 Anxious")
    var selectedMood by remember { mutableStateOf<String?>(null) }
    val moodHistory = remember { mutableStateListOf<Pair<String, String>>() }

    val recommendations = mapOf(
        "😊 Happy" to listOf(
            "Keep engaging in activities that bring joy.",
            "Maintain positive social connections.",
            "Share your happiness with others."
        ),
        "😢 Sad" to listOf(
            "Express emotions through journaling or talking.",
            "Consider gentle exercise and staying active.",
            "Speak to a counselor or trusted person."
        ),
        "😠 Angry" to listOf(
            "Take deep breaths or use mindfulness techniques.",
            "Avoid impulsive actions; pause and reflect.",
            "Journaling or physical activity may help release tension."
        ),
        "😌 Calm" to listOf(
            "Continue with relaxing routines like meditation.",
            "Support others who may be struggling.",
            "Build positive habits and practice gratitude."
        ),
        "😰 Anxious" to listOf(
            "Practice grounding exercises (like 5-4-3-2-1).",
            "Focus on tasks within your control.",
            "Consider speaking with a mental health professional."
        )
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                "MoodSync",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0D47A1)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "How are you feeling today?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1565C0)
            )

            Spacer(modifier = Modifier.height(24.dp))

            moods.forEach { mood ->
                Button(
                    onClick = {
                        selectedMood = mood
                        val timestamp = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
                        moodHistory.add(0, mood to timestamp)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Text(mood, fontSize = 18.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("period_tracker") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60))
            ) {
                Text("Track Period", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            selectedMood?.let { mood ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recommendations",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        recommendations[mood]?.forEach { point ->
                            Text(
                                text = "• $point",
                                fontSize = 16.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (moodHistory.isNotEmpty()) {
                Text(
                    text = "Mood History",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp) // Avoid overflow
                ) {
                    items(moodHistory) { (mood, time) ->
                        Text(
                            text = "$mood - $time",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}
