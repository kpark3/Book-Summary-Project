package com.example.book_summary_project.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.book_summary_project.R
import com.example.book_summary_project.api.db.performSignIn
import com.example.book_summary_project.api.db.createUserWithEmail
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * SplashScreen Composable:
 *  This composable function represents the splash screen of the application.
 *  It displays a welcome message and provides options for user authentication
 *  using Google Sign-In and email/password login.
 */
@Composable
fun SplashScreen(
    context: Context,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //var accountState by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val firebaseAuth  = FirebaseAuth.getInstance()
    val user = firebaseAuth.currentUser

    val ic_login = painterResource(id = R.drawable.baseline_login_24)

    // Check if user is already logged in and navigate to the main screen if true
    // LaunchedEffect is used to perform side effects in a composable function.
    LaunchedEffect(user) {
        if (user != null) {
            onLoginSuccess()
        }
    }

    // Configure Google Sign-In options.
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    // Create a GoogleSignInClient instance.
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    // Launcher for Google Sign-In intent
    // rememberLauncherForActivityResult is used to launch an activity for a result.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(Exception::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Google Sign-In Successful", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Google Sign-In Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, "Google Sign-In Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Book Summaries on Demand",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(250.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Username / Email") },
                keyboardOptions =  KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )
        }

        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Please enter both email and password",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        performSignIn(email, password, context, keyboardController, onLoginSuccess)
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp, end = 32.dp)
            ) {
                Icon(
                    painter = ic_login,
                    contentDescription = "Login Icon",
                    modifier = Modifier.size(96.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(200.dp))

        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Sign in with Google", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    createUserWithEmail(
                        context = context,
                        email = email,
                        password = password,
                        onSuccess = {
                            onLoginSuccess() // or navigate to home
                        },
                        onFailure = { exception ->
                            Toast.makeText(context, "Sign up failed: ${exception.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Email and password must not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Create Account", fontSize = 18.sp)
        }
    }
}
