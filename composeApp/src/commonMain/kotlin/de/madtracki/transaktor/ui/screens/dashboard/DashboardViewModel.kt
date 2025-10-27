package de.madtracki.transaktor.ui.screens.dashboard

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.madtracki.transaktor.data.model.Result
import de.madtracki.transaktor.data.repository.BankingRepository
import de.madtracki.transaktor.ui.screens.dashboard.model.DashboardUiState
import de.madtracki.transaktor.ui.screens.dashboard.model.TotalFunds
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountItem
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class DashboardViewModel(
    private val bankingRepository: BankingRepository
) : ViewModel() {

    init {
        load()
    }

    private val _uiState: MutableStateFlow<Result<DashboardUiState>> = MutableStateFlow(
        Result.Loading
    )

    val uiState: StateFlow<Result<DashboardUiState>> = _uiState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading
    )

    @OptIn(ExperimentalTime::class)
    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Execute both calls in parallel
                val accountsDeferred = async { bankingRepository.getAccounts() }
                val turnoversDeferred = async { bankingRepository.getTurnovers() }

                // Await both results
                val accounts = accountsDeferred.await()
                val turnovers = turnoversDeferred.await()

                // TODO extract this into a separate UseCase

                // Map accounts to AccountItem
                val accountItems = accounts.map { account ->
                    AccountItem(
                        id = account.id.toString(),
                        name = account.name,
                        balance = account.balance.toString(), // TODO: Format balance
                        color = Color(0xFF000000) // TODO: Generate or get color for account
                    )
                }

                // Map turnovers to TransactionItem
                val transactionItems = turnovers.map { turnover ->
                    TransactionItem(
                        id = turnover.id,
                        reference = turnover.reference,
                        datetime = Instant
                            .fromEpochMilliseconds(turnover.timestamp)
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .toString(), // TODO: Format date
                        amount = turnover.amount.toString(), // TODO: Format amount
                        cardNumber = "**** ${turnover.senderIban.takeLast(4)}"
                    )
                }

                val totalFunds = TotalFunds(
                    balance = accounts.sumOf { it.balance }.toString(), // TODO: Format balance
                    name = "Max Mustermann" // Placeholder. Not available in API
                )

                _uiState.value = Result.Success(
                    DashboardUiState(
                        totalFunds = totalFunds,
                        accounts = accountItems,
                        transactions = transactionItems
                    )
                )
            } catch (exception: Exception) {
                _uiState.value = Result.Error(exception)
            }
        }
    }
}