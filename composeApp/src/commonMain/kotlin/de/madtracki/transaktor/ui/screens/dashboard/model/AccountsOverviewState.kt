package de.madtracki.transaktor.ui.screens.dashboard.model

import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem

data class AccountsOverviewState(
    val totalFunds: TotalFunds,
    val accounts: List<AccountItem>,
    val transactions: List<TransactionItem>,
)
