package apps.sumit.noteapp.features.notes_feature.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvents {
    data class EnteredTitle(val value: String) : AddEditNoteEvents()
    data class ChangeTitleFocus(val focus: FocusState) : AddEditNoteEvents()
    data class EnteredContent(val value: String) : AddEditNoteEvents()
    data class ChangeContentFocus(val focus: FocusState) : AddEditNoteEvents()
    data class ChangeColor(val color: Int) : AddEditNoteEvents()
    data object SaveNote : AddEditNoteEvents()
}