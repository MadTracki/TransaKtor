package de.madtracki.transaktor.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.madtracki.transaktor.data.model.Result
import de.madtracki.transaktor.data.usecase.GetAccountsOverviewUseCase
import de.madtracki.transaktor.ui.screens.dashboard.model.AccountsOverviewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class DashboardViewModel(
    private val getAccountsOverviewUseCase: GetAccountsOverviewUseCase
) : ViewModel() {

    init {
        load()
    }

    private val _uiState: MutableStateFlow<Result<AccountsOverviewState>> = MutableStateFlow(
        Result.Loading
    )

    val uiState: StateFlow<Result<AccountsOverviewState>> = _uiState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading
    )

    @OptIn(ExperimentalTime::class)
    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accountsOverviewState = getAccountsOverviewUseCase()

                _uiState.value = Result.Success(accountsOverviewState)
            } catch (exception: Exception) {
                _uiState.value = Result.Error(exception)
            }
        }
    }
}