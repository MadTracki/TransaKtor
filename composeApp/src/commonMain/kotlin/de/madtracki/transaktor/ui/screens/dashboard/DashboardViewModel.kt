package de.madtracki.transaktor.ui.screens.dashboard

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import de.madtracki.transaktor.ui.screens.dashboard.model.TotalFunds
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DashboardViewModel : ViewModel() {
    private val _totalFunds = MutableStateFlow(
        TotalFunds(
            balance = "100.00",
            name = "Max Mustermann"
        )
    )
    val totalFunds: StateFlow<TotalFunds> = _totalFunds

    private val _accounts: MutableStateFlow<List<AccountItem>> = MutableStateFlow(
        listOf(
            AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            ), AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            ), AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            ), AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            ), AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            ), AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            ), AccountItem(
                id = "1",
                name = "Account 1",
                balance = "100.00",
                color = Color(0xFF000000)
            )
        )
    )

    val accounts: StateFlow<List<AccountItem>> = _accounts

    private val _transactions: MutableStateFlow<List<TransactionItem>> = MutableStateFlow(
        listOf(
            TransactionItem(
                id = "1",
                reference = "Transaction 1",
                datetime = "20.10.2025",
                amount = "100.00€",
                cardNumber = "**** 1234"
            ),
            TransactionItem(
                id = "1",
                reference = "Transaction 1",
                datetime = "20.10.2025",
                amount = "100.00€",
                cardNumber = "**** 1234"
            ),
            TransactionItem(
                id = "1",
                reference = "Transaction 1",
                datetime = "20.10.2025",
                amount = "100.00€",
                cardNumber = "**** 1234"
            ),
            TransactionItem(
                id = "1",
                reference = "Transaction 1",
                datetime = "20.10.2025",
                amount = "100.00€",
                cardNumber = "**** 1234"
            ),
            TransactionItem(
                id = "1",
                reference = "Transaction 1",
                datetime = "20.10.2025",
                amount = "100.00€",
                cardNumber = "**** 1234"
            ),
            TransactionItem(
                id = "1",
                reference = "Transaction 1",
                datetime = "20.10.2025",
                amount = "100.00€",
                cardNumber = "**** 1234"
            ),
        )
    )

    val transactions: StateFlow<List<TransactionItem>> = _transactions

}