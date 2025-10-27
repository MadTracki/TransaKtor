package de.madtracki.transaktor.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Utility functions for formatting various data types into displayable strings.
 */

@OptIn(ExperimentalTime::class)
fun formatDateTime(timestamp: Long): String {
    return Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .format(LocalDateTime.Format {
            day(Padding.ZERO); char('.'); monthNumber(Padding.ZERO); char('.'); year(Padding.ZERO)
            char(' ')
            hour(); char(':'); minute()
        })
}

fun formatAmount(amount: Double, currency: String): String {
    // TODO implement proper DecimalFormat
    return "${if (amount >= 0.0) "+" else ""}$amount$currency"
}
