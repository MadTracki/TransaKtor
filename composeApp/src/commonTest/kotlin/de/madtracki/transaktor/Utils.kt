package de.madtracki.transaktor

import org.koin.test.KoinTest

inline fun <reified T : Any, reified R : T> KoinTest.injectAs(): Lazy<R> = lazy {
    val obj = getKoin().get<T>()
    obj as R
}