package de.madtracki.transaktor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.madtracki.transaktor.ui.navigation.destinations.Screen
import de.madtracki.transaktor.ui.screens.dashboard.DashboardScreen
import de.madtracki.transaktor.ui.screens.onboarding.OnboardingScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    isOnboardingDone: Boolean = false
) {

    NavHost(
        navController = navController,
        startDestination = if (isOnboardingDone) Screen.Dashboard else Screen.Onboarding,
    ) {

        composable<Screen.Onboarding> {
            OnboardingScreen {
                navController.navigate(
                    route = Screen.Dashboard,
                    navOptions = NavOptions.Builder().setPopUpTo(
                        route = Screen.Onboarding,
                        inclusive = true
                    ).build()
                )
            }
        }

        composable<Screen.Dashboard> {
            DashboardScreen(
                navigateToProfile = {},
                navigateToAccountDetail = {},
                navigateToTransactionDetail = {
                    navController.navigate(
                        route = Screen.TransactionDetail,
                    )
                },
                navigateToAllTransactions = {},
            )
        }
    }
}