package de.madtracki.transaktor.data.usecase

import de.madtracki.transaktor.data.repository.BankingRepository
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountDetailState
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.util.formatAmount

/**
 * Use case for retrieving and converting account turnovers to a list of [TransactionItem] objects.
 */
interface GetAccountDetailsUseCase {
    /**
     * Retrieves account details and converts the turnovers to a list of transaction items.
     *
     * @param accountId The ID of the account to get transactions for.
     * @return A list of [TransactionItem] objects.
     */
    suspend operator fun invoke(
        accountId: Long,
        censorCardNumber: Boolean = true
    ): AccountDetailState
}

class GetAccountDetailsUseCaseImpl(
    private val bankingRepository: BankingRepository,
    private val mapTurnoversToTransactionItemsUseCase: MapTurnoversToTransactionItemsUseCase
) : GetAccountDetailsUseCase {
    override suspend fun invoke(accountId: Long, censorCardNumber: Boolean): AccountDetailState {
        val accountDetails = bankingRepository.getAccountDetails(accountId)

        val transactions = mapTurnoversToTransactionItemsUseCase(
            accountDetails.turnovers,
            accountDetails.account.currency,
            censorCardNumber
        )

        return AccountDetailState(
            accountName = accountDetails.account.name,
            balance = formatAmount(accountDetails.account.balance, accountDetails.account.currency),
            transactions = transactions
        )
    }
}



