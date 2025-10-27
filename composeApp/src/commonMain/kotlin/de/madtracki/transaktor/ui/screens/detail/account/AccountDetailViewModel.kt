package de.madtracki.transaktor.ui.screens.detail.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.madtracki.transaktor.data.model.Result
import de.madtracki.transaktor.data.usecase.GetAccountDetailsUseCase
import de.madtracki.transaktor.ui.screens.detail.account.model.AccountDetailState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlin.time.ExperimentalTime

class AccountDetailViewModel(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<Result<AccountDetailState>> =
        MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<AccountDetailState>> = _uiState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading
    )

    @OptIn(ExperimentalTime::class)
    fun loadAccountDetail(accountId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accountDetails = getAccountDetailsUseCase(accountId)

                _uiState.value = Result.Success(accountDetails)
            } catch (exception: IOException) {
                _uiState.value = Result.Error(exception)
            }
        }
    }
}