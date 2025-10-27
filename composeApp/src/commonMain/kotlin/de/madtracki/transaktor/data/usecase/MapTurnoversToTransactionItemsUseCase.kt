package de.madtracki.transaktor.data.usecase

import de.madtracki.transaktor.data.model.Turnover
import de.madtracki.transaktor.ui.screens.detail.transaction.model.TransactionItem
import de.madtracki.transaktor.util.formatAmount
import de.madtracki.transaktor.util.formatDateTime

interface MapTurnoversToTransactionItemsUseCase {
    /**
     * Maps a list of [Turnover] objects to a list of [TransactionItem] objects.
     *
     * @param turnovers The list of [Turnover] objects to map.
     * @param censorCardNumber Whether to censor the card number or not.
     * @return A list of [TransactionItem] objects.
     */
    suspend operator fun invoke(
        turnovers: List<Turnover>,
        currency: String,
        censorCardNumber: Boolean
    ): List<TransactionItem>

    /**
     * Maps a [Turnover] object to a [TransactionItem] object.
     *
     * @param turnover The [Turnover] object to map.
     * @param censorCardNumber Whether to censor the card number or not.
     * @return A [TransactionItem] object.
     */
    suspend operator fun invoke(
        turnover: Turnover,
        currency: String,
        censorCardNumber: Boolean
    ): TransactionItem
}

class MapTurnoversToTransactionItemsUseCaseImpl : MapTurnoversToTransactionItemsUseCase {
    override suspend fun invoke(
        turnovers: List<Turnover>,
        currency: String,
        censorCardNumber: Boolean
    ): List<TransactionItem> {
        return turnovers.map { turnover ->
            this(turnover, currency, censorCardNumber)
        }
    }

    override suspend fun invoke(
        turnover: Turnover,
        currency: String,
        censorCardNumber: Boolean
    ): TransactionItem {
        val cardNumber =
            if (censorCardNumber) "**** ${turnover.senderIban.takeLast(4)}" else turnover.senderIban

        return TransactionItem(
            id = turnover.id,
            reference = turnover.reference,
            datetime = formatDateTime(turnover.timestamp),
            amount = formatAmount(turnover.amount, currency),
            cardNumber = cardNumber
        )
    }
}