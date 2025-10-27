package de.madtracki.transaktor.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.madtracki.transaktor.presentation.dashboard.DashboardViewModel
import de.madtracki.transaktor.ui.screens.dashboard.model.TotalFunds
import de.madtracki.transaktor.ui.screens.detail.account.AccountCard
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItem
import de.madtracki.transaktor.ui.screens.detail.transaction.TransactionCard
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItemListPreviewParameterProvider
import de.madtracki.transaktor.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import transaktor.composeapp.generated.resources.Res
import transaktor.composeapp.generated.resources.dashboard_see_all
import transaktor.composeapp.generated.resources.dashboard_transactions
import transaktor.composeapp.generated.resources.dashboard_your_accounts
import transaktor.composeapp.generated.resources.person

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    outerPadding: PaddingValues = PaddingValues(),
    navigateToProfile: () -> Unit,
    navigateToAccountDetail: (id: String) -> Unit,
    navigateToTransactionDetail: (id: String) -> Unit,
    navigateToAllTransactions: () -> Unit
) {
    val totalFunds by viewModel.totalFunds.collectAsStateWithLifecycle()
    val accounts by viewModel.accounts.collectAsStateWithLifecycle()
    val transactionItems by viewModel.transactions.collectAsStateWithLifecycle()

    DashboardContent(
        outerPadding = outerPadding,
        totalFunds = totalFunds,
        accountItems = accounts,
        transactionItems = transactionItems,
        navigateToProfile = navigateToProfile,
        navigateToAccountDetail = navigateToAccountDetail,
        navigateToTransactionDetail = navigateToTransactionDetail,
        navigateToAllTransactions = navigateToAllTransactions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    outerPadding: PaddingValues = PaddingValues(),
    totalFunds: TotalFunds,
    accountItems: List<AccountItem>,
    transactionItems: List<TransactionItem>,
    navigateToProfile: () -> Unit,
    navigateToAccountDetail: (id: String) -> Unit,
    navigateToTransactionDetail: (id: String) -> Unit,
    navigateToAllTransactions: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(outerPadding),
        topBar = {
            TopAppBar(
                title = { Text("TransaKtor") },
                actions = {
                    IconButton(
                        onClick = navigateToProfile,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = CircleShape
                    ) {
                        Icon(
                            painterResource(Res.drawable.person),
                            contentDescription = "Profile"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            //verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TotalBalanceCard(balance = totalFunds.balance, name = totalFunds.name) }
            item { Spacer(modifier = Modifier.height(32.dp)) }
            YourAccounts(accountItems, navigateToAccountDetail)
            item { Spacer(modifier = Modifier.height(32.dp)) }
            Transactions(
                transactionItems,
                navigateToTransactionDetail,
                navigateToAllTransactions
            )
        }
    }
}

fun LazyListScope.YourAccounts(
    accounts: List<AccountItem>,
    navigateToAccountDetail: (id: String) -> Unit
) {
    item {
        Text(
            text = stringResource(Res.string.dashboard_your_accounts),
            style = MaterialTheme.typography.titleLarge
        )
    }
    item { Spacer(modifier = Modifier.height(16.dp)) }
    item {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            /*
             * I am constraining the height of the grid to avoid nested infinite-height scrollables
             * (and hence a crash). This isn't the best solution, but for demonstration purposes
             * this suffices.
             * We could add a "See all" like for the transactions as well and truncate the list of
             * accounts at 4 items.
             *
             * Alternatively, a workaround could be to let the parent be a LazyVerticalGrid:
             * ```kt
             * LazyVerticalGrid(cells = GridCells.Fixed(2)) {
             *     item(span = { GridItemSpan(2) }) { TotalBalanceCard() }
             *     YourAccounts()
             *     Transactions()
             * }
             * ```
             */
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .heightIn(max = 500.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = false
        ) {
            items(accounts) { account ->
                AccountCard(accountItem = account, onClick = navigateToAccountDetail)
            }
        }
    }
}

fun LazyListScope.Transactions(
    transactionItems: List<TransactionItem>,
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
                text = stringResource(Res.string.dashboard_transactions),
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = navigateToAllTransactions) {
                Text(text = stringResource(Res.string.dashboard_see_all))
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
fun DashboardScreenPreview(
    @PreviewParameter(TransactionItemListPreviewParameterProvider::class) transactionItems: List<TransactionItem>
) {
    AppTheme {
        DashboardContent(
            outerPadding = PaddingValues(),
            totalFunds = TotalFunds(balance = "42,899.03", name = "Max Mustermann"),
            accountItems = listOf(
                AccountItem(
                    id = "1",
                    name = "Account 1",
                    balance = "100.00",
                    color = Color(0xFF000000)
                )
            ),
            transactionItems = transactionItems,
            navigateToProfile = {},
            navigateToAccountDetail = {},
            navigateToTransactionDetail = {},
            navigateToAllTransactions = {},
        )
    }
}