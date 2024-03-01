package apps.sumit.noteapp.features.notes_feature.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.sumit.noteapp.ui.theme.BabyBlue
import apps.sumit.noteapp.ui.theme.LightBlue
import apps.sumit.noteapp.ui.theme.LightGreen
import apps.sumit.noteapp.ui.theme.RedOrange
import apps.sumit.noteapp.ui.theme.RedPink
import apps.sumit.noteapp.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf<Color>(RedOrange, LightBlue, RedPink, LightGreen, BabyBlue, Violet)
    }
}

class InvalidNotesException(message: String) : Exception(message)
