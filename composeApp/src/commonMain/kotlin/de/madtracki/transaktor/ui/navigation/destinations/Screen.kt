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
    @SerialName("newsdetail")
    data class TransactionDetail(val transactionId: Int) : Screen {
        override val route: String by lazy { serializer().descriptor.serialName }
    }
}

