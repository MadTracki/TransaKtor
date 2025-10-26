package de.madtracki.transaktor.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Long,
    val name: String,
    val ownerName: String,
    val balance: Double,
    val currency: String,
    val iban: String
)
