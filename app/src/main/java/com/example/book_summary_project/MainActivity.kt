package com.example.book_summary_project

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.book_summary_project.screens.*
import com.example.book_summary_project.destinations.Destination
import com.example.book_summary_project.navigation.BottomNav
import com.example.book_summary_project.ui.theme.Book_summary_projectTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Book_summary_projectTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

/**
 * Main screen with bottom navigation bar
 *
 * @param navController
 *
 * @return Unit
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val context = LocalContext.current

    // State variable to track the user's login status, initialized based on Firebase authentication.
    var isLoggedIn by rememberSaveable { mutableStateOf(FirebaseAuth.getInstance().currentUser != null) }

    /* Use LaunchedEffect to monitor changes in the Firebase
     * authentication state and update the login status accordingly.
     */
    LaunchedEffect(FirebaseAuth.getInstance().currentUser) {
        isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    }

    /* If the user is logged in and on the splash screen,
     * navigate to the summary screen, removing the splash screen from the back stack.
     */
    if (isLoggedIn && currentRoute == Destination.Splash.route) {
        navController.navigate(Destination.Summary.route) {
            popUpTo(Destination.Splash.route) { inclusive = true }
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != Destination.Splash.route) {
                BottomNav(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Splash.route) {
                SplashScreen(
                    context = context,
                    onLoginSuccess = {
                        isLoggedIn = true
                    }
                )
            }

            composable(Destination.Summary.route) { SummaryScreen() }
            composable(Destination.SavedBooks.route) { SavedBooksScreen() }
            composable(Destination.Profile.route) {
                ProfileScreen(
                    onLogout = {
                        FirebaseAuth.getInstance().signOut() // Sign out the user from Firebase.
                        isLoggedIn = false // Update the login status.
                        navController.navigate(Destination.Splash.route) {
                            popUpTo(0) { inclusive = true } // Clear the back stack and navigate to the splash screen.
                        }
                    }
                )
            }
        }
    }
}