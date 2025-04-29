package com.example.book_summary_project.destinations

/**
 * Sealed class representing the different destinations in the navigation graph.
 * Each destination has a unique route string that identifies it.
 * Splash, Summary, SavedBooks, and Profile are the possible destinations.
 */
sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object Summary : Destination("summary")
    object SavedBooks : Destination("saved_books")
    object Profile : Destination("profile")
}