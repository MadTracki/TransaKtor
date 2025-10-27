package de.madtracki.transaktor.usecase

import de.madtracki.transaktor.data.model.Account
import de.madtracki.transaktor.data.model.AccountWithTurnovers
import de.madtracki.transaktor.data.model.Turnover
import de.madtracki.transaktor.data.repository.BankingRepository
import de.madtracki.transaktor.data.usecase.GetAccountDetailsUseCase
import de.madtracki.transaktor.data.usecase.MapTurnoversToTransactionItemsUseCase
import de.madtracki.transaktor.di.useCaseModule
import de.madtracki.transaktor.fake.FakeBankingRepository
import de.madtracki.transaktor.injectAs
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.util.formatAmount
import de.madtracki.transaktor.util.formatDateTime
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GetAccountDetailsUseCaseTest : KoinTest {
    private val overrideModule = module {
        single<BankingRepository> { FakeBankingRepository() }
    }

    private val repository by injectAs<BankingRepository, FakeBankingRepository>()
    private val mapTurnoversUseCase by inject<MapTurnoversToTransactionItemsUseCase>()
    private val getAccountDetailsUseCase by inject<GetAccountDetailsUseCase>()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(useCaseModule + overrideModule)
        }
    }

    @Test
    fun `getAccountDetails returns correct AccountDetailState`() = runTest {
        // Given
        val accountId = 1L
        val account = Account(
            id = accountId,
            ownerName = "John Doe",
            name = "Test Account",
            balance = 1000.0,
            currency = "EUR",
            iban = "DE12345678901234567890",
        )

        val turnovers = listOf(
            Turnover(
                id = "1",
                accountId = accountId,
                amount = -50.0,
                senderName = "Test Sender",
                senderIban = "DE98765432109876543210",
                reference = "Test Transaction",
                timestamp = 1730064000000
            )
        )

        val accountWithTurnovers = AccountWithTurnovers(account, turnovers)

        // Mock the repository to return our test data
        repository.accountDetails = accountWithTurnovers

        // When
        val result = getAccountDetailsUseCase(accountId, false)

        // Then
        assertEquals(account.name, result.accountName)
        assertEquals(formatAmount(account.balance, account.currency), result.balance)
        assertEquals(
            listOf(
                TransactionItem(
                    turnovers[0].id,
                    turnovers[0].reference,
                    formatDateTime(turnovers[0].timestamp),
                    formatAmount(turnovers[0].amount, account.currency),
                    turnovers[0].senderIban
                )
            ), result.transactions
        )
    }

    @Test
    fun `getAccountDetails returns censored CardNumber`() = runTest {
        // Given
        val accountId = 1L
        val account = Account(
            id = accountId,
            ownerName = "John Doe",
            name = "Test Account",
            balance = 1000.0,
            currency = "EUR",
            iban = "DE12345678901234567890",
        )

        val turnovers = listOf(
            Turnover(
                id = "1",
                accountId = accountId,
                amount = -50.0,
                senderName = "Test Sender",
                senderIban = "DE98765432109876543210",
                reference = "Test Transaction",
                timestamp = 1730064000000
            )
        )

        val accountWithTurnovers = AccountWithTurnovers(account, turnovers)

        // Mock the repository to return our test data
        repository.accountDetails = accountWithTurnovers

        // When
        val result = getAccountDetailsUseCase(accountId, true)

        // Then
        assertEquals(
            listOf(
                TransactionItem(
                    turnovers[0].id,
                    turnovers[0].reference,
                    formatDateTime(turnovers[0].timestamp),
                    formatAmount(turnovers[0].amount, account.currency),
                    "**** ${turnovers[0].senderIban.takeLast(4)}"
                )
            ), result.transactions
        )
    }

    @Test
    fun `getAccountDetails throws when account not found`() = runTest {
        // Given
        val nonExistentAccountId = 999L
        repository.accountDetails = null

        // Then - Expects IllegalArgumentException to be thrown
        assertFails {
            getAccountDetailsUseCase(nonExistentAccountId)
        }

    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}
