package de.madtracki.transaktor

import de.madtracki.transaktor.util.formatAmount
import de.madtracki.transaktor.util.formatDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FormattersKtTest {
    // Test timestamps (in milliseconds since epoch)
    private val recentTimestamp = 1730064000000L // 2024-10-26T12:00:00Z
    private val epochTimestamp = 0L // 1970-01-01T00:00:00Z
    private val leapYearTimestamp = 1582934400000L // 2020-02-29T00:00:00Z
    private val nonLeapYearTimestamp =
        1677628800000L // 2023-03-01T00:00:00Z (next day after Feb 28)
    private val yearEndTimestamp = 1672531199000L // 2022-12-31T23:59:59Z
    private val yearStartTimestamp = 1672531200000L // 2023-01-01T00:00:00Z

    @Test
    fun `formatDateTime with a valid recent timestamp`() {
        // 2024-10-26T12:00:00Z in Berlin time (UTC+2 during DST)
        val result = formatDateTime(recentTimestamp)
        // Format: "dd.MM.yyyy HH:mm"
        assertTrue(result.matches(Regex("""\d{2}\.\d{2}\.\d{4} \d{1,2}:\d{2}""")))
    }

    @Test
    fun `formatDateTime with the Unix epoch zero timestamp`() {
        // 1970-01-01T00:00:00Z
        val result = formatDateTime(epochTimestamp)
        // Should be "01.01.1970 01:00" or similar, depending on timezone
        assertTrue(result.startsWith("01.01.1970 "))
    }

    @Test
    fun `formatDateTime with a leap year timestamp`() {
        // 2020-02-29T00:00:00Z
        val result = formatDateTime(leapYearTimestamp)
        // Should be "29.02.2020 01:00" or similar, depending on timezone
        assertTrue(result.startsWith("29.02.2020 "))
    }

    @Test
    fun `formatDateTime with a non leap year timestamp on February 29th`() {
        // 2023-03-01T00:00:00Z (next day after Feb 28)
        val result = formatDateTime(nonLeapYearTimestamp)
        // Should be "01.03.2023 01:00" or similar, depending on timezone
        assertTrue(result.startsWith("01.03.2023 "))
    }

    @Test
    fun `formatDateTime at a year boundary`() {
        // 2022-12-31T23:59:59Z
        val endOfYear = formatDateTime(yearEndTimestamp)
        // 2023-01-01T00:00:00Z
        val startOfYear = formatDateTime(yearStartTimestamp)

        assertTrue(endOfYear.startsWith("31.12.2022 23:"))
        assertTrue(startOfYear.startsWith("01.01.2023 00:"))
    }

    @Test
    fun `formatAmount with a positive amount`() {
        assertEquals("+100.50€", formatAmount(100.5, "€"))
        assertEquals("+42.00$", formatAmount(42.0, "$"))
    }

    @Test
    fun `formatAmount with a negative amount`() {
        assertEquals("-50.25€", formatAmount(-50.25, "€"))
        assertEquals("-10.00$", formatAmount(-10.0, "$"))
    }

    @Test
    fun `formatAmount with a zero amount`() {
        assertEquals("+0.00€", formatAmount(0.0, "€"))
        assertEquals("+0.00$", formatAmount(-0.0, "$"))
    }

    @Test
    fun `formatAmount with a large amount`() {
        assertEquals("+1000000.00€", formatAmount(1_000_000.0, "€"))
    }

    @Test
    fun `formatAmount with a very small fractional amount`() {
        assertEquals("+0.00€", formatAmount(0.000001, "€"))
    }

    @Test
    fun `formatAmount with various currency symbols`() {
        assertEquals("+100.00€", formatAmount(100.0, "€"))
        assertEquals("+100.00$", formatAmount(100.0, "$"))
        assertEquals("+100.00£", formatAmount(100.0, "£"))
        assertEquals("+100.00¥", formatAmount(100.0, "¥"))
        assertEquals("+100.00", formatAmount(100.0, ""))
    }

    @Test
    fun `formatAmount with a currency long string`() {
        assertEquals("+100.00USD", formatAmount(100.0, "USD"))
    }

    @Test
    fun `formatAmount with Double NaN`() {
        assertEquals("NaN", formatAmount(Double.NaN, "€"))
    }

    @Test
    fun `formatAmount with Double POSITIVE INFINITY`() {
        assertEquals("+Infinity€", formatAmount(Double.POSITIVE_INFINITY, "€"))
    }

    @Test
    fun `formatAmount with Double NEGATIVE INFINITY`() {
        assertEquals("-Infinity€", formatAmount(Double.NEGATIVE_INFINITY, "€"))
    }
}