package com.example.book_summary_project.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.book_summary_project.R
import com.example.book_summary_project.destinations.Destination

/**
 * Bottom Navigation Bar for the app with navigation to Summary, Saved Books, and Profile screens
 *
 * @param navController NavController to navigate between screens
 *
 * @return Unit
 */
@Composable
fun BottomNav(navController: NavController) {
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val ic_book = painterResource(id = R.drawable.baseline_book_24)
        val ic_bookmark = painterResource(id = R.drawable.baseline_bookmarks_24)
        val ic_profile = painterResource(id = R.drawable.baseline_person_24)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Summary.route,
            onClick = { navController.navigate(Destination.Summary.route) {
                    popUpTo(Destination.Summary.route)
                    launchSingleTop = true
            }},
            icon = { Icon(painter = ic_book, contentDescription = "Summary") },
            label = { Text(text = "Summary") }
        ) // End of Summary navigation

        NavigationBarItem(
            selected = currentDestination?.route == Destination.SavedBooks.route,
            onClick = { navController.navigate(Destination.SavedBooks.route) {
                    popUpTo(Destination.SavedBooks.route)
                    launchSingleTop = true
            }},
            icon = { Icon(painter = ic_bookmark, contentDescription = "Saved Books") },
            label = { Text(text = "Saved") }
        ) // End of Saved Books navigation

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Profile.route,
            onClick = { navController.navigate(Destination.Profile.route) {
                    popUpTo(Destination.Profile.route)
                    launchSingleTop = true
            }},
            icon = { Icon(painter = ic_profile, contentDescription = "Profile") },
            label = { Text(text = "Profile") }
        ) // End of Profile navigation
    }
}