package com.example.book_summary_project.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import com.example.book_summary_project.destinations.Destination
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext

/** ProfileScreen Composable:
 * This composable function represents the profile screen of the application.
 * It displays the user's information and provides an option to log out.
 * The onLogout callback function is triggered when the user clicks the "Log Out" button.
 * It performs the necessary logout actions and navigates to the login screen.
 * The user's information includes the username and email.
 *
 * @param onLogout A callback function to be triggered when the user clicks the "Log Out" button.
 *
 * @return Unit
 */
@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    //val usernameEmail = "user@example.com"
    val user = FirebaseAuth.getInstance().currentUser
    val usernameEmail = user?.email ?: "No email"
    val maskedPassword = "password123" // to be masked with asterisks
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User Info",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = usernameEmail,
            onValueChange = {},
            label = { Text("Username / Email") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = maskedPassword,
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                // call the onLogout callback function when the user clicks the "Log Out" button
                onLogout()
                // show a toast message when the user logs out
                Toast.makeText(context, "Log Out Successful", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
        ) {
            Text("Log Out")
        }
    }
}