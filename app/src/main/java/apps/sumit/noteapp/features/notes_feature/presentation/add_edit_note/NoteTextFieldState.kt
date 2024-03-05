package apps.sumit.noteapp.features.notes_feature.presentation.add_edit_note

data class NoteTextFieldState(
    val text: String = String(),
    val hint: String = String(),
    val isHintVisible: Boolean = true
)