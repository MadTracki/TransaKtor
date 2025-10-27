package de.madtracki.transaktor.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.madtracki.transaktor.ui.screens.detail.transaction.TransactionCard
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItemListPreviewParameterProvider
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.dashboard_see_all
import transaktor.composeapp.generated.resources.transactions

fun LazyListScope.TransactionList(
    transactionItems: List<TransactionItem>,
    showSeeAllButton: Boolean,
    navigateToTransactionDetail: (id: String) -> Unit,
    navigateToAllTransactions: () -> Unit
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.transactions),
                style = MaterialTheme.typography.titleLarge
            )
            if (showSeeAllButton) {
                TextButton(onClick = navigateToAllTransactions) {
                    Text(text = stringResource(Res.string.dashboard_see_all))
                }
            }
        }
    }
    item { Spacer(modifier = Modifier.height(8.dp)) }
    items(transactionItems) { transaction ->
        TransactionCard(
            transactionItem = transaction,
            onClick = navigateToTransactionDetail
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionListPreview(@PreviewParameter(TransactionItemListPreviewParameterProvider::class) transactionItems: List<TransactionItem>) {
    AppTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
        ) {
            TransactionList(
                transactionItems = transactionItems,
                showSeeAllButton = false,
                navigateToTransactionDetail = {},
                navigateToAllTransactions = {}
            )
        }
    }
}