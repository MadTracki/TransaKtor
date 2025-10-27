package de.madtracki.transaktor.fake

import de.madtracki.transaktor.data.model.Account
import de.madtracki.transaktor.data.model.AccountWithTurnovers
import de.madtracki.transaktor.data.model.Turnover
import de.madtracki.transaktor.data.repository.BankingRepository

class FakeBankingRepository : BankingRepository {
    var accounts: List<Account>? = null
    var accountDetails: AccountWithTurnovers? = null
    var turnovers: List<Turnover>? = null
    var turnover: Turnover? = null

    override suspend fun getAccounts(): List<Account> {
        return accounts.orEmpty()
    }

    override suspend fun getAccountDetails(accountId: Long): AccountWithTurnovers {
        return accountDetails
            ?: throw IllegalArgumentException("Account with ID $accountId not found")
    }

    override suspend fun getTurnovers(): List<Turnover> {
        return turnovers.orEmpty()
    }

    override suspend fun getTurnover(id: String): Turnover {
        return turnover ?: throw IllegalArgumentException("Turnover with ID $id not found")
    }
}