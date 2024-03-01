package apps.sumit.noteapp.features.notes_feature.domain.util

sealed class OrderType {
    data object Ascending : OrderType()
    data object Descending : OrderType()
}