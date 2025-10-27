package de.madtracki.transaktor.ui.screens.detail.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItem
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItemPreviewParameterProvider
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.account_balance_wallet

@Composable
fun AccountCard(accountItem: AccountItem, onClick: (id: String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = { onClick(accountItem.id) }),
        colors = CardDefaults.cardColors(containerColor = accountItem.color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.Companion.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painterResource(Res.drawable.account_balance_wallet),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = accountItem.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Companion.Bold
                )
            }
            Text(
                text = accountItem.balance,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Companion.End)
            )
        }
    }
}

@Composable
@Preview()
fun AccountCardPreview(
    @PreviewParameter(AccountItemPreviewParameterProvider::class) accountItem: AccountItem
) {
    AppTheme {
        AccountCard(accountItem = accountItem, onClick = {})
    }
}