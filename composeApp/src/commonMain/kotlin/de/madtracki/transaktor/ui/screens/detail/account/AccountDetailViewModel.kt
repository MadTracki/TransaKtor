package de.madtracki.transaktor.ui.screens.detail.account

import androidx.lifecycle.ViewModel
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// For now, this is a dummy view model with static data
class AccountDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AccountDetailState(
            accountName = "Business Account",
            balance = "$8,763.22",
            transactions = listOf(
                TransactionItem(
                    id = "1",
                    reference = "Office Supplies",
                    datetime = "Today, 11:20 AM",
                    amount = "-$156.78",
                    cardNumber = "****5678"
                ),
                TransactionItem(
                    id = "2",
                    reference = "Client Payment",
                    datetime = "Yesterday, 4:30 PM",
                    amount = "+$2500.00",
                    cardNumber = "****5678"
                ),
                TransactionItem(
                    id = "3",
                    reference = "Software Subscription",
                    datetime = "October 22, 12:00 PM",
                    amount = "-$99.99",
                    cardNumber = "****5678"
                )
            )
        )
    )
    val uiState: StateFlow<AccountDetailState> = _uiState.asStateFlow()

    fun loadAccountDetail(accountId: String) {
        //TODO("Not yet implemented")
    }
}