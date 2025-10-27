package de.madtracki.transaktor.ui.navigation.destinations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    val route: String

    /** A separate
     * Onboarding Screen abstraction can be made if more
     * onboarding screens get added. KISS.  */
    @Serializable
    @SerialName("onboarding")
    object Onboarding : Screen {
        override val route: String by lazy { serializer().descriptor.serialName }
    }

    @Serializable
    @SerialName("dashboard")
    object Dashboard : Screen {
        override val route: String by lazy { serializer().descriptor.serialName }
    }

    @Serializable
    @SerialName("accountdetail")
    data class AccountDetail(val accountId: Long) : Screen {
        override val route: String by lazy { serializer().descriptor.serialName }
    }

    @Serializable
    @SerialName("transactiondetail")
    data class TransactionDetail(val transactionId: String) : Screen {
        override val route: String by lazy { serializer().descriptor.serialName }
    }
}
