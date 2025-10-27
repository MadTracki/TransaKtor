package de.madtracki.transaktor.ui.screens.detail.account.model

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class AccountItem(
    val id: String, val name: String, val balance: String, val color: Color
)

class AccountItemPreviewParameterProvider : PreviewParameterProvider<AccountItem> {
    override val values = sequenceOf(
        AccountItem(
            "1",
            name = "Main Account",
            balance = "€2,450.50",
            color = Color(0xFF4CAF50) // Green
        ),
        AccountItem(
            "2",
            name = "Savings",
            balance = "€5,230.75",
            color = Color(0xFF2196F3) // Blue
        ),
        AccountItem(
            "3",
            name = "Investments",
            balance = "€12,845.20",
            color = Color(0xFF9C27B0) // Purple
        ),
        AccountItem(
            "4",
            name = "Premium Account with a very long name that should wrap",
            balance = "€8,927.35",
            color = Color(0xFFFF9800) // Orange
        ),
        AccountItem(
            "5",
            name = "Gemeinschaftskonto",
            balance = "3.450,99 €",
            color = Color(0xFFF44336) // Red
        ),
    )
}