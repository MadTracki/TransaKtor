package de.madtracki.transaktor.usecase

import de.madtracki.transaktor.data.model.Turnover
import de.madtracki.transaktor.data.usecase.MapTurnoversToTransactionItemsUseCase
import de.madtracki.transaktor.data.usecase.MapTurnoversToTransactionItemsUseCaseImpl
import de.madtracki.transaktor.util.formatAmount
import de.madtracki.transaktor.util.formatDateTime
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MapTurnoversToTransactionItemsUseCaseTest {
    private lateinit var useCase: MapTurnoversToTransactionItemsUseCase

    @BeforeTest
    fun setup() {
        useCase = MapTurnoversToTransactionItemsUseCaseImpl()
    }

    @Test
    fun `map single turnover to transaction item with uncensored card number`() = runTest {
        // Given
        val turnover = Turnover(
            id = "123",
            accountId = 1,
            amount = -50.0,
            senderName = "Test Sender",
            senderIban = "DE12345678901234567890",
            reference = "Test Transaction",
            timestamp = 1730064000000 // A fixed timestamp for consistent testing
        )
        val currency = "€"
        val censorCardNumber = false

        // When
        val result = useCase(turnover, currency, censorCardNumber)

        // Then
        assertEquals(turnover.id, result.id)
        assertEquals(turnover.reference, result.reference)
        assertEquals(formatDateTime(turnover.timestamp), result.datetime)
        assertEquals(formatAmount(turnover.amount, currency), result.amount)
        assertEquals(turnover.senderIban, result.cardNumber)
    }

    @Test
    fun `map single turnover to transaction item with censored card number`() = runTest {
        // Given
        val turnover = Turnover(
            id = "123",
            accountId = 1,
            amount = 100.0,
            senderName = "Test Sender",
            senderIban = "DE12345678901234567890",
            reference = "Salary",
            timestamp = 1730064000000
        )
        val currency = "$"
        val censorCardNumber = true

        // When
        val result = useCase(turnover, currency, censorCardNumber)

        // Then
        assertEquals(turnover.id, result.id)
        assertEquals(turnover.reference, result.reference)
        assertEquals(formatDateTime(turnover.timestamp), result.datetime)
        assertEquals(formatAmount(turnover.amount, currency), result.amount)
        assertEquals("**** ${turnover.senderIban.takeLast(4)}", result.cardNumber)
    }

    @Test
    fun `map multiple turnovers to transaction items`() = runTest {
        // Given
        val turnovers = listOf(
            Turnover(
                id = "1",
                accountId = 1,
                amount = -30.0,
                senderName = "Café",
                senderIban = "DE1111222233334444",
                reference = "Lunch",
                timestamp = 1730064000000
            ),
            Turnover(
                id = "2",
                accountId = 1,
                amount = 1000.0,
                senderName = "Employer",
                senderIban = "DE5555666677778888",
                reference = "Salary",
                timestamp = 1729977600000
            )
        )
        val currency = "€"
        val censorCardNumber = true

        // When
        val results = useCase(turnovers, currency, censorCardNumber)

        // Then
        assertEquals(turnovers.size, results.size)

        // Verify first turnover
        assertEquals(turnovers[0].id, results[0].id)
        assertEquals(turnovers[0].reference, results[0].reference)
        assertEquals(formatDateTime(turnovers[0].timestamp), results[0].datetime)
        assertEquals(formatAmount(turnovers[0].amount, currency), results[0].amount)
        assertEquals("**** ${turnovers[0].senderIban.takeLast(4)}", results[0].cardNumber)

        // Verify second turnover
        assertEquals(turnovers[1].id, results[1].id)
        assertEquals(turnovers[1].reference, results[1].reference)
        assertEquals(formatDateTime(turnovers[1].timestamp), results[1].datetime)
        assertEquals(formatAmount(turnovers[1].amount, currency), results[1].amount)
        assertEquals("**** ${turnovers[1].senderIban.takeLast(4)}", results[1].cardNumber)
    }
}