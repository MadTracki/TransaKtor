package de.madtracki.transaktor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.madtracki.transaktor.config.AppConfig
import de.madtracki.transaktor.di.appModule
import de.madtracki.transaktor.di.vmModule
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(appConfig: AppConfig = AppConfig.Prod) {
    KoinApplication(application = {
        modules(
            appModule(appConfig),
            vmModule,
        )
    }) {
        AppTheme {
            var showContent by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text(color = MaterialTheme.colorScheme.onBackground, text = "Compose: $greeting")
                    }
                }
            }
        }
    }
}