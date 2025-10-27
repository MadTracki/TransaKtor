package de.madtracki.transaktor.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.dashboard_total_available_funds

@Composable
fun TotalBalanceCard(balance: String, name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            Text(
                text = stringResource(Res.string.dashboard_total_available_funds),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = balance,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}