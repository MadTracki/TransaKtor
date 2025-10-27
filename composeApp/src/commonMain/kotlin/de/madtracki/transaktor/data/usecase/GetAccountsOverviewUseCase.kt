package de.madtracki.transaktor.data.usecase

import androidx.compose.ui.graphics.Color
import de.madtracki.transaktor.data.model.AccountWithTurnovers
import de.madtracki.transaktor.data.model.Turnover
import de.madtracki.transaktor.data.repository.BankingRepository
import de.madtracki.transaktor.ui.screens.dashboard.model.AccountsOverviewState
import de.madtracki.transaktor.ui.screens.dashboard.model.TotalFunds
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItem
import de.madtracki.transaktor.util.formatAmount
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Use case for retrieving and converting all accounts and their turnovers to [AccountsOverviewState] object.
 */
interface GetAccountsOverviewUseCase {
    /**
     * Retrieves all account details and the three newest transactions.
     *
     * @return A [AccountsOverviewState] object.
     */
    suspend operator fun invoke(censorCardNumber: Boolean = true): AccountsOverviewState
}

class GetAccountsOverviewUseCaseImpl(
    private val bankingRepository: BankingRepository,
    private val mapTurnoversToTransactionItemsUseCase: MapTurnoversToTransactionItemsUseCase
) : GetAccountsOverviewUseCase {

    data class TurnoverWithCurrency(
        val turnover: Turnover,
        val currency: String
    )

    override suspend fun invoke(censorCardNumber: Boolean): AccountsOverviewState = coroutineScope {
        // Fetch accounts and turnovers concurrently
        val accountsDeferred = async { bankingRepository.getAccounts() }
        val turnoversDeferred = async { bankingRepository.getTurnovers() }

        // Await both results
        val accounts = accountsDeferred.await()
        val allTurnovers = turnoversDeferred.await()

        // Group turnovers by account ID for faster lookup
        val turnoversByAccountId = allTurnovers.groupBy { it.accountId }

        // Process accounts in parallel
        val accountsWithTurnovers = accounts.map { account ->
            val accountTurnovers = turnoversByAccountId[account.id] ?: emptyList()
            AccountWithTurnovers(account, accountTurnovers)
        }

        // Map accounts to AccountItem
        val accountItems = accounts.map { account ->
            AccountItem(
                id = account.id,
                name = account.name,
                balance = formatAmount(account.balance, account.currency),
                color = Color(0xFF000000) // TODO: Generate or get color for account
            )
        }

        // get the newest 3 turnovers
        val newestThreeTransactions = accountsWithTurnovers.flatMap { details ->
            details.turnovers.map {
                TurnoverWithCurrency(it, details.account.currency)
            }
        }
            .sortedByDescending { it.turnover.timestamp }
            .take(3)
            .map {
                mapTurnoversToTransactionItemsUseCase(it.turnover, it.currency, censorCardNumber)
            }
            .toList()

        // Sum the balances of all accounts
        // For demonstration purposes use € -> would need currency conversion first
        val totalFunds = TotalFunds(
            balance = formatAmount(
                accounts.sumOf { it.balance },
                accounts.firstOrNull()?.currency ?: "€"
            ),
            name = "Max Mustermann" // Placeholder. Not available in API
        )

        return@coroutineScope AccountsOverviewState(
            totalFunds,
            accounts = accountItems,
            transactions = newestThreeTransactions
        )
    }
}



