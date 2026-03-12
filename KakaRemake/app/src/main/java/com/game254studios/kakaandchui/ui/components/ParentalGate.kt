package com.game254studios.kakaandchui.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ParentalGate(
    onPassed: () -> Unit,
    onDismissed: () -> Unit
) {
    val num1 = remember { (10..50).random() }
    val num2 = remember { (10..50).random() }
    val correctAnswer = remember(num1, num2) { num1 + num2 }
    var userAnswer by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissed,
        title = {
            Text(
                text = "Parent Zone \uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                Text(
                    text = "To continue, solve this problem:",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "What is $num1 + $num2?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = {
                        userAnswer = it.filter { c -> c.isDigit() }
                        showError = false
                    },
                    label = { Text("Your answer") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                if (showError) {
                    Text(
                        text = "Try again",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (userAnswer.toIntOrNull() == correctAnswer) {
                    onPassed()
                } else {
                    showError = true
                    userAnswer = ""
                }
            }) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissed) {
                Text("Cancel")
            }
        }
    )
}
