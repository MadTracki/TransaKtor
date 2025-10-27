package de.madtracki.transaktor.ui.screens.detail.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.madtracki.transaktor.data.model.Result
import de.madtracki.transaktor.data.repository.BankingRepository
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.io.IOException
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// For now, this is a dummy view model with static data
class AccountDetailViewModel(
    private val bankingRepository: BankingRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<Result<AccountDetailState>> =
        MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<AccountDetailState>> = _uiState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading
    )

    @OptIn(ExperimentalTime::class)
    fun loadAccountDetail(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accountDetails = bankingRepository.getAccountDetails(accountId)

                // Map turnovers to TransactionItem
                val transactionItems = accountDetails.turnovers.map { turnover ->
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

                val result = AccountDetailState(
                    accountName = accountDetails.account.name,
                    balance = accountDetails.account.balance.toString(), // TODO implement proper DecimalFormat
                    transactions = transactionItems
                )
                _uiState.value = Result.Success(result)
            } catch (exception: IOException) {
                _uiState.value = Result.Error(exception)
            }
        }
    }
}