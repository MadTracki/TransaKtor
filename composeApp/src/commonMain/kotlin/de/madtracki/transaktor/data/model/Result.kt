package de.madtracki.transaktor.data.model

sealed class Result<out T : Any> {
    data class Success<out R : Any>(val data: R) : Result<R>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}