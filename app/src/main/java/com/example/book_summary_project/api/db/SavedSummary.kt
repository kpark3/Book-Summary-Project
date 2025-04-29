package com.example.book_summary_project.api.db

import com.google.firebase.Timestamp

/**
 * SavedSummary Data Class:
 *  This data class represents a saved summary.
 *  It contains properties such as summaryId, bookName, userId, addPrompt, length, summary,
 *  timestampFormatted, and timestampRaw.
 *  The summaryId property is an integer and is initialized to 0.
 *  The bookName property is a string and is initialized to an empty string.
 *  The userId property is a string and is initialized to an empty string.
 *  The addPrompt property is a string and is initialized to an empty string.
 *  The length property is a string and is initialized to an empty string.
 *  The summary property is a string and is initialized to an empty string.
 *  The timestampFormatted property is a string and is initialized to an empty string.
 *  The timestampRaw property is a Timestamp object and is initialized to the current timestamp.
 */
data class SavedSummary (
    val summaryId: Int = 0,
    val bookName: String = "",
    val userId: String = "",
    val addPrompt: String = "",
    val length: String = "",
    val summary: String = "",
    val timestampFormatted: String = "",
    val timestampRaw: Timestamp = Timestamp.now()
)