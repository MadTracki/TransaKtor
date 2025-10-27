package de.madtracki.transaktor.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.madtracki.transaktor.ui.navigation.destinations.Screen
import de.madtracki.transaktor.ui.screens.dashboard.DashboardScreen
import de.madtracki.transaktor.ui.screens.detail.account.AccountDetailScreen
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

        composable<Screen.Onboarding> { backStackEntry ->
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

        composable<Screen.Dashboard> { backStackEntry ->
            DashboardScreen(
                navigateToProfile = {},
                navigateToAccountDetail = {
                    navController.navigate(Screen.AccountDetail(it))
                },
                navigateToTransactionDetail = {
                    navController.navigate(Screen.TransactionDetail(it))
                },
                navigateToAllTransactions = {},
            )
        }

        composable<Screen.AccountDetail> { backStackEntry ->
            AccountDetailScreen(
                accountId = backStackEntry.toRoute<Screen.AccountDetail>().accountId,
                onBack = { navController.popBackStack() },
                navigateToTransaction = {
                    navController.navigate(Screen.TransactionDetail(it))
                }
            )
        }

        composable<Screen.TransactionDetail> { backStackEntry ->
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { Text("Transaction Detail for id:\n\n${backStackEntry.toRoute<Screen.TransactionDetail>().transactionId}") }
            //TransactionDetailScreen(
            //    transactionId = backStackEntry.toRoute<Screen.TransactionDetail>().transactionId,
            //    onBack = { navController.popBackStack() }
            //)
        }
    }
}