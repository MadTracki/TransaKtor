package de.madtracki.transaktor.data.api

import de.madtracki.transaktor.config.AppConfig
import de.madtracki.transaktor.data.model.Account
import de.madtracki.transaktor.data.model.Turnover
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header

interface BankingApi {
    suspend fun getAccounts(): List<Account>
    suspend fun getTurnovers(): List<Turnover>
}

class BankingApiImpl(private val client: HttpClient) : BankingApi {
    override suspend fun getAccounts(): List<Account> {
        return client.get("accounts").body()
    }

    override suspend fun getTurnovers(): List<Turnover> {
        return client.get("turnovers").body()
    }
}

/**
 * Ktor configuration extension to configure a HttpClient with
 * our API base URL and common headers.
 */
fun HttpClientConfig<*>.configureApi(appConfig: AppConfig) {
    expectSuccess = true
    defaultRequest {
        url(appConfig.baseUrl)
        header("Content-Type", "application/json")
        header("Accept", "application/json")
    }
}
