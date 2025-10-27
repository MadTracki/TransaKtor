package de.madtracki.transaktor.di

import de.madtracki.transaktor.config.AppConfig
import de.madtracki.transaktor.data.api.BankingApi
import de.madtracki.transaktor.data.api.BankingApiImpl
import de.madtracki.transaktor.data.api.configureApi
import de.madtracki.transaktor.data.network.getHttpClient
import de.madtracki.transaktor.data.repository.BankingRepository
import de.madtracki.transaktor.data.repository.BankingRepositoryImpl
import de.madtracki.transaktor.data.usecase.GetAccountDetailsUseCase
import de.madtracki.transaktor.data.usecase.GetAccountDetailsUseCaseImpl
import de.madtracki.transaktor.data.usecase.GetAccountsOverviewUseCase
import de.madtracki.transaktor.data.usecase.GetAccountsOverviewUseCaseImpl
import de.madtracki.transaktor.data.usecase.MapTurnoversToTransactionItemsUseCase
import de.madtracki.transaktor.data.usecase.MapTurnoversToTransactionItemsUseCaseImpl
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
    
    single<BankingApi> { BankingApiImpl(client = get()) }

    single<BankingRepository> { BankingRepositoryImpl(api = get()) }
}

val useCaseModule = module {
    single<MapTurnoversToTransactionItemsUseCase> { MapTurnoversToTransactionItemsUseCaseImpl() }
    single<GetAccountDetailsUseCase> {
        GetAccountDetailsUseCaseImpl(
            bankingRepository = get(),
            mapTurnoversToTransactionItemsUseCase = get()
        )
    }
    single<GetAccountsOverviewUseCase> {
        GetAccountsOverviewUseCaseImpl(
            bankingRepository = get(),
            mapTurnoversToTransactionItemsUseCase = get()
        )
    }
}

val vmModule = module {
    factory<DashboardViewModel> {
        DashboardViewModel(getAccountsOverviewUseCase = get())
    }
    factory<AccountDetailViewModel> {
        AccountDetailViewModel(getAccountDetailsUseCase = get())
    }
}