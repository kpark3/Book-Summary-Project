package com.example.book_summary_project.api.db

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * fetchSavedSummaries Function:
 *  This function fetches saved summaries from the Firestore database for a specific user.
 *  It takes a userId parameter and an onComplete callback function.
 *  The function queries the "summaries" collection in the Firestore database and retrieves
 *  documents where the "userId" field matches the provided userId.
 *  The retrieved documents are converted to SavedSummary objects and added to a list.
 *  The onComplete callback function is invoked with the list of saved summaries.
 *  If an error occurs during the retrieval process, the onComplete callback is invoked with an empty list.
 *
 *  @param userId The ID of the user for whom to fetch saved summaries.
 *  @param onComplete A callback function to be invoked with the list of saved summaries.
 *
 *  @return Unit
 */
fun fetchSavedSummaries(userId: String, onComplete: (List<SavedSummary>) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("summaries")
        .whereEqualTo("userId", userId)
        .orderBy("timestampRaw", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { result ->
            val summaries = result.mapNotNull { it.toObject(SavedSummary::class.java) }
            onComplete(summaries)
        }
        .addOnFailureListener { exception ->
            Log.e("Firestore", "Error getting documents", exception)
            onComplete(emptyList())
        }
}