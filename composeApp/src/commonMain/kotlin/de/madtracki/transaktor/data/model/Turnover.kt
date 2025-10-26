package de.madtracki.transaktor.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Turnover(
    val id: String,
    @SerialName("account_id")
    val accountId: Long,
    val amount: Double,
    @SerialName("sender_name")
    val senderName: String,
    @SerialName("sender_iban")
    val senderIban: String,
    val reference: String,
    val timestamp: Long
)
