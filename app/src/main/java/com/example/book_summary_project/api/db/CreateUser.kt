package com.example.book_summary_project.api.db

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


/** createUserWithEmail function:
 * This function creates a new user account using Firebase Authentication.
 * It takes the context, email, password, onSuccess callback, and onFailure callback as parameters.
 * If the account creation is successful, it shows a toast message and calls the onSuccess callback.
 *
 * @param context The application context.
 * @param email The email address of the new user.
 * @param password The password of the new user.
 * @param onSuccess A callback function to be executed when the account creation is successful.
 * @param onFailure A callback function to be executed when the account creation fails.
 *
 * @return Unit
 */
fun createUserWithEmail(
    context: Context,
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                onSuccess()
            } else {
                onFailure(task.exception ?: Exception("Unknown error"))
            }
        }
}