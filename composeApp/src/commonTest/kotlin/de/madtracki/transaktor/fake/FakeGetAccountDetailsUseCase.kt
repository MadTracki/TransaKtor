package de.madtracki.transaktor.fake

import de.madtracki.transaktor.data.usecase.GetAccountDetailsUseCase
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountDetailState
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import kotlinx.coroutines.delay
import kotlinx.io.IOException

class FakeGetAccountDetailsUseCase : GetAccountDetailsUseCase {
    var succeed: Boolean = true
    var result: AccountDetailState = AccountDetailState(
        accountName = "Test Account",
        balance = "1000.00€",
        transactions = listOf(
            TransactionItem(
                id = "1",
                reference = "Test Transaction",
                datetime = "01.01.2025 12:00",
                amount = "+100.00€",
                cardNumber = "DE12345678901234567890"
            )
        )
    )

    override suspend fun invoke(
        accountId: Long,
        censorCardNumber: Boolean
    ): AccountDetailState {
        delay(2000)
        if (!succeed) {
            throw IOException("Failed to get account details")
        }
        return result
    }
}