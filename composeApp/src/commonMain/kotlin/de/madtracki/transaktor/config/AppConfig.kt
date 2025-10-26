package de.madtracki.transaktor.config

sealed interface AppConfig {
    val baseUrl: String
    
    data object Dev : AppConfig {
        override val baseUrl: String = "https://testapi.io/api/jpt/v1/"
    }
    
    data object Prod : AppConfig {
        override val baseUrl: String = "https://testapi.io/api/jpt/v1/"
    }
}
