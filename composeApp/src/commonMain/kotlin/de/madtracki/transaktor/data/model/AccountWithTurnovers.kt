package de.madtracki.transaktor.data.model

data class AccountWithTurnovers(
    val account: Account,
    val turnovers: List<Turnover>
)
