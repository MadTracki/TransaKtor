package de.madtracki.transaktor.ui.screens.detail.transaction.model

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class TransactionItem(
    val id: String,
    val reference: String,
    val datetime: String,
    val amount: String,
    val cardNumber: String
)

class TransactionItemPreviewParameterProvider : PreviewParameterProvider<TransactionItem> {
    override val values = sequenceOf(
        TransactionItem("1", "Campus Cafeteria", "Yesterday, 12:30 PM", "-$12.50", "****4321"),
        TransactionItem("2", "Part-time Job", "Oct 23, 6:00 PM", "+$450.00", "****4321"),
        TransactionItem("3", "University Bookstore", "Today, 1:00 PM", "-$89.50", "****4321"),
    )
}

class TransactionItemListPreviewParameterProvider :
    PreviewParameterProvider<List<TransactionItem>> {
    override val values = sequenceOf(
        listOf(
            TransactionItem("1", "Campus Cafeteria", "Yesterday, 12:30 PM", "-$12.50", "****4321"),
            TransactionItem("2", "Part-time Job", "Oct 23, 6:00 PM", "+$450.00", "****4321"),
            TransactionItem("3", "University Bookstore", "Today, 1:00 PM", "-$89.50", "****4321"),
        )
    )
}