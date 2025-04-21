package com.example.moodsync.ui.theme

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.Calendar

data class PeriodLog(
    val date: LocalDate,
    val flow: String,
    val symptoms: List<String>,
    val mood: String,
    val temperature: Float?,
    val weight: Float?
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PeriodTrackerScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var flow by remember { mutableStateOf(TextFieldValue()) }
    var symptoms by remember { mutableStateOf(TextFieldValue()) }
    var mood by remember { mutableStateOf(TextFieldValue()) }
    var temperature by remember { mutableStateOf(TextFieldValue()) }
    var weight by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button
        IconButton(onClick = { onBack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text("Period Tracker", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val today = Calendar.getInstance()
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    selectedDate = LocalDate.of(year, month + 1, day)
                },
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text("Select Date: $selectedDate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = flow,
            onValueChange = { flow = it },
            label = { Text("Menstrual Flow") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = symptoms,
            onValueChange = { symptoms = it },
            label = { Text("Symptoms (comma separated)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mood,
            onValueChange = { mood = it },
            label = { Text("Mood") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = temperature,
            onValueChange = { temperature = it },
            label = { Text("Temperature (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val log = PeriodLog(
                date = selectedDate,
                flow = flow.text,
                symptoms = symptoms.text.split(",").map { it.trim() },
                mood = mood.text,
                temperature = temperature.text.toFloatOrNull(),
                weight = weight.text.toFloatOrNull()
            )

            // You can later store this log in a database
            Toast.makeText(context, "Entry saved for ${log.date}", Toast.LENGTH_SHORT).show()
        }) {
            Text("Save Entry")
        }
    }
}
