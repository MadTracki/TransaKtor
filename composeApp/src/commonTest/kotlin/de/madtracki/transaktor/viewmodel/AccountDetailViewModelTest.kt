package de.madtracki.transaktor.viewmodel

import de.madtracki.transaktor.data.model.Result
import de.madtracki.transaktor.data.usecase.GetAccountDetailsUseCase
import de.madtracki.transaktor.di.vmModule
import de.madtracki.transaktor.fake.FakeGetAccountDetailsUseCase
import de.madtracki.transaktor.injectAs
import de.madtracki.transaktor.ui.screens.detail.account.AccountDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AccountDetailViewModelTest : KoinTest {
    private val testDispatcher = StandardTestDispatcher()

    private val overrideModule = module {
        single<GetAccountDetailsUseCase> { FakeGetAccountDetailsUseCase() }
    }

    private val useCase by injectAs<GetAccountDetailsUseCase, FakeGetAccountDetailsUseCase>()

    val viewModel: AccountDetailViewModel by inject<AccountDetailViewModel>()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(vmModule + overrideModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadAccountDetail emits Loading then Success when use case succeeds`() = runTest {
        useCase.succeed = true
        viewModel.loadAccountDetail(1)

        val firstResult = viewModel.uiState.first()
        val secondResult = viewModel.uiState.drop(1).first()
        assertTrue(firstResult is Result.Loading)
        assertTrue(secondResult is Result.Success)
        assertTrue(secondResult.data == useCase.result)
    }

    @Test
    fun `loadAccountDetail emits Loading then Error when use case fails`() = runTest {
        useCase.succeed = false
        viewModel.loadAccountDetail(1)

        val firstResult = viewModel.uiState.first()
        val secondResult = viewModel.uiState.drop(1).first()
        assertTrue(firstResult is Result.Loading)
        assertTrue(secondResult is Result.Error)
    }
}