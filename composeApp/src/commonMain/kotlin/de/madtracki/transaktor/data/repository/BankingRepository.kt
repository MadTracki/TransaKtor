package de.madtracki.transaktor.data.repository

import de.madtracki.transaktor.data.api.BankingApi
import de.madtracki.transaktor.data.model.Account
import de.madtracki.transaktor.data.model.AccountWithTurnovers
import de.madtracki.transaktor.data.model.Turnover


interface BankingRepository {
    suspend fun getAccounts(): List<Account>
    suspend fun getAccountDetails(accountId: String): AccountWithTurnovers
    suspend fun getTurnovers(): List<Turnover>

    suspend fun getTurnover(id: String): Turnover
}

class BankingRepositoryImpl(private val api: BankingApi) : BankingRepository {

    override suspend fun getAccounts(): List<Account> {
        return api.getAccounts()
    }

    override suspend fun getAccountDetails(accountId: String): AccountWithTurnovers {
        val accounts = api.getAccounts()
        val account = accounts.firstOrNull { it.id.toString() == accountId }
            ?: throw NoSuchElementException("Account with id $accountId not found")

        val allTurnovers = api.getTurnovers()
        val accountTurnovers = allTurnovers.filter { it.accountId == account.id }

        return AccountWithTurnovers(account, accountTurnovers)
    }

    override suspend fun getTurnovers(): List<Turnover> {
        return api.getTurnovers()
    }

    override suspend fun getTurnover(id: String): Turnover {
        return api.getTurnovers().firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Turnover with id $id not found")
    }
}