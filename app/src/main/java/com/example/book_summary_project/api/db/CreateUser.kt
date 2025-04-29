package com.example.book_summary_project.api.db

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

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