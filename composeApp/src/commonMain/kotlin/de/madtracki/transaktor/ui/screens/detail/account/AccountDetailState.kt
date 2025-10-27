package de.madtracki.transaktor.ui.screens.detail.account

import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem

data class AccountDetailState(
    val accountName: String = "",
    val balance: String = "",
    val transactions: List<TransactionItem> = emptyList()
)