package de.madtracki.transaktor.ui.screens.detail.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItemPreviewParameterProvider
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.account_balance_wallet

@Composable
fun TransactionCard(
    transactionItem: TransactionItem,
    onClick: (id: String) -> Unit
) {
    Card(
        modifier = Modifier.wrapContentHeight().fillMaxWidth()
            .clickable(onClick = { onClick(transactionItem.id) }),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ).padding(8.dp),
                painter = painterResource(Res.drawable.account_balance_wallet),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = transactionItem.reference,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = transactionItem.datetime,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(horizontalAlignment = Alignment.Companion.End) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = transactionItem.amount,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Companion.Bold
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = transactionItem.cardNumber,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
@Preview()
fun TransactionCardPreview(
    @PreviewParameter(TransactionItemPreviewParameterProvider::class) transactionItem: TransactionItem
) {
    AppTheme {
        TransactionCard(transactionItem = transactionItem, onClick = {})
    }
}