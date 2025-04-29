package com.example.book_summary_project.api.db

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

/**
 * getNextSummaryId Function:
 *  This function retrieves the next available summary ID from the Firestore database.
 *  It increments the "summaryId" counter in the "counters" collection.
 *  If the counter document doesn't exist, it initializes it with a value of 1.
 *  The onComplete callback function is invoked with the next available summary ID.
 *  If an error occurs during the retrieval process, the onComplete callback is invoked with null.
 */
fun getNextSummaryId(onComplete: (Int?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val counterRef = db.collection("counters").document("summaryId")

    db.runTransaction { transaction ->
        val snapshot = transaction.get(counterRef)

        val next: Long = if (!snapshot.exists()) {
            // Document doesn't exist: initialize it
            transaction.set(counterRef, mapOf("value" to 1))
            1
        } else {
            val current = snapshot.getLong("value") ?: 0L
            val updated = current + 1
            transaction.update(counterRef, "value", updated)
            updated
        }

        next.toInt()
    }.addOnSuccessListener { newId ->
        onComplete(newId)
    }.addOnFailureListener { e ->
        Log.e("Firestore", "Failed to increment summaryId", e)
        onComplete(null)
    }
}