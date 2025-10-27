package de.madtracki.transaktor

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import de.madtracki.transaktor.config.AppConfig
import de.madtracki.transaktor.di.appModule
import de.madtracki.transaktor.di.useCaseModule
import de.madtracki.transaktor.di.vmModule
import de.madtracki.transaktor.ui.navigation.AppNavHost
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App(appConfig: AppConfig = AppConfig.Prod) {
    KoinApplication(application = {
        modules(
            appModule(appConfig),
            useCaseModule,
            vmModule,
        )
    }) {
        AppTheme {
            val navController = rememberNavController()

            AppNavHost(
                navController = navController,
                isOnboardingDone = false // hard-coding for now, we can add a viewmodel for this later
            )
        }
    }
}