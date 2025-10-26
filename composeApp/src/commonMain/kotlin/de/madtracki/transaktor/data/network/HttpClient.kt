package de.madtracki.transaktor.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun getHttpClient(configuration: HttpClientConfig<*>.() -> Unit = {}): HttpClient