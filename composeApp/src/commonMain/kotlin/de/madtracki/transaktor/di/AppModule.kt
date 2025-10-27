package de.madtracki.transaktor.di

import de.madtracki.transaktor.config.AppConfig
import de.madtracki.transaktor.data.api.BankingApi
import de.madtracki.transaktor.data.api.BankingApiImpl
import de.madtracki.transaktor.data.api.configureApi
import de.madtracki.transaktor.data.network.getHttpClient
import de.madtracki.transaktor.ui.screens.dashboard.DashboardViewModel
import de.madtracki.transaktor.ui.screens.detail.account.AccountDetailViewModel
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun appModule(appConfig: AppConfig) = module {
    single<HttpClient> {
        getHttpClient {
            configureApi(appConfig)
        }
    }
    
    // Banking API
    single<BankingApi> { BankingApiImpl(client = get()) }
    
    //single<BankingRepository> { BankingRepositoryImpl(api = get()) }
}

val vmModule = module {
    factory<DashboardViewModel> {
        DashboardViewModel(/*bankingRepository = get()*/)
    }
    factory<AccountDetailViewModel> {
        AccountDetailViewModel(/*bankingRepository = get()*/)
    }
}