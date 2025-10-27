package de.madtracki.transaktor.ui.screens.detail.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.madtracki.transaktor.ui.screens.dashboard.TotalBalanceCard
import de.madtracki.transaktor.ui.screens.dashboard.TransactionList
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItemListPreviewParameterProvider
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.account_available_balance
import transaktor.composeapp.generated.resources.account_balance_wallet
import transaktor.composeapp.generated.resources.arrow_back

@Composable
fun AccountDetailScreen(
    accountId: String,
    viewModel: AccountDetailViewModel = koinViewModel(),
    onBack: () -> Unit,
    navigateToTransaction: (id: String) -> Unit
) {
    LaunchedEffect(accountId) {
        viewModel.loadAccountDetail(accountId)
    }

    val uiState by viewModel.uiState.collectAsState()

    AccountDetailContent(
        accountName = uiState.accountName,
        balance = uiState.balance,
        transactions = uiState.transactions,
        onBack = onBack,
        onTransactionClick = navigateToTransaction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailContent(
    accountName: String,
    balance: String,
    transactions: List<TransactionItem>,
    onBack: () -> Unit,
    onTransactionClick: (id: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(Res.drawable.account_balance_wallet),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = accountName,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = CircleShape
                    ) {
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            item {
                TotalBalanceCard(
                    balance = balance,
                    name = accountName,
                    title = stringResource(Res.string.account_available_balance)
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
            TransactionList(
                transactions,
                showSeeAllButton = false,
                navigateToTransactionDetail = {},
                navigateToAllTransactions = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AccountDetailContentPreview(
    @PreviewParameter(TransactionItemListPreviewParameterProvider::class) transactionItems: List<TransactionItem>
) {
    AppTheme {
        AccountDetailContent(
            accountName = "Student Account",
            balance = "1.234,56 €",
            transactions = transactionItems,
            onBack = {},
            onTransactionClick = {}
        )
    }
}
