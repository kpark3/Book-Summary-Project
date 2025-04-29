package com.example.book_summary_project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.book_summary_project.api.db.SavedSummary
import com.example.book_summary_project.api.db.fetchSavedSummaries
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * SavedBooksScreen Composable:
 *  This composable function represents the saved books screen of the application.
 *  It displays a list of saved books with their details, including the book name,
 *  prompt, length, and summary.
 *  The summaries are fetched from the database using the fetchSavedSummaries function.
 *  The saved summaries are sorted based on the selected sort option.
 *  The sort options are "ID", "Name", "Length", and "Date".
 *  The sort option is initially set to "ID".
 *  The saved summaries are displayed in a lazy column.
 *  Each saved summary is represented by a card with the book name, prompt, length,
 *  and summary details.
 *  The summary details are displayed in a scrollable text field.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedBooksScreen(modifier: Modifier = Modifier) {

    var summaries by remember { mutableStateOf<List<SavedSummary>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("ID") }
    val sortOptions = listOf("ID", "Name", "Length", "Date")
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid ?: ""

    // Fetch summaries once loaded only if the userId is not null
    if (userId.isNotEmpty()) {
        LaunchedEffect(true) {
            fetchSavedSummaries(userId) { fetched ->
                summaries = fetched
            }
        }
    }

    val sortedSummaries = when (selectedSort) {
        "Name" -> summaries.sortedBy { it.bookName.lowercase() }
        "Date" -> summaries.sortedByDescending { it.timestampRaw }
        "Length" -> summaries.sortedBy {
            when (it.length) {
                "Short" -> 1
                "Medium" -> 2
                "Long" -> 3
                else -> Int.MAX_VALUE // not expected but just in case
            }
        }
        else -> summaries.sortedBy { it.summaryId }
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {

        // Title and Sort Dropdown Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Saved Summaries",
                style = MaterialTheme.typography.headlineSmall
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedSort,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sort By") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    sortOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedSort = option
                                expanded = false
                            }
                        )
                    }
                }

                // Toggle on field tap
                LaunchedEffect(Unit) {
                    expanded = false
                }
            }
        }

        // List of Cards
        LazyColumn {
            items(sortedSummaries) { summary ->
                SavedSummaryCard(summary = summary)
            }
        }
    }
}


@Composable
fun SavedSummaryCard(summary: SavedSummary) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Book Name: ${summary.bookName}", style = MaterialTheme.typography.titleMedium)
            Text("Prompt: ${summary.addPrompt}", style = MaterialTheme.typography.bodySmall)
            Text("Length: ${summary.length}", style = MaterialTheme.typography.bodySmall)
            Text(summary.timestampFormatted, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            if (expanded) {
                Text(
                    text = summary.summary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = summary.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    )
                }
            }

            TextButton(onClick = { expanded = !expanded }) {
                Text(if (expanded) "Show Less" else "Show More")
            }
        }
    }
}