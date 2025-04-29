package com.example.book_summary_project.api.db

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.example.book_summary_project.MainActivity
import com.google.firebase.auth.FirebaseAuth

/** performSignIn function:
 * This function performs the sign-in process using Firebase Authentication.
 * It takes the email, password, context, and keyboard controller as parameters.
 * If the sign-in is successful, it shows a toast message and navigates to the main activity.
 * If the sign-in fails, it shows an error message.
 *
 * @param email The email address of the user.
 * @param password The password of the user.
 * @param context The application context.
 * @param keyboardController The software keyboard controller.
 * @param onLoginSuccess A callback function to be executed when the login is successful.
 *
 *  @return Unit
 */
fun performSignIn(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController?,
    onLoginSuccess: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
                onLoginSuccess()
            } else {
                val message = task.exception?.message ?: "Unknown error"
                Toast.makeText(context, "Login Failed: $message}", Toast.LENGTH_LONG).show()
            }
        }
}




