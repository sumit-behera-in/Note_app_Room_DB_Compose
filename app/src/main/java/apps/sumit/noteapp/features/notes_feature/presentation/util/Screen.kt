package apps.sumit.noteapp.features.notes_feature.presentation.util

sealed class Screen(val route: String) {
    data object NoteScreen : Screen("notes_screen")
    data object AddEditNoteScreen : Screen("add_edit_note_screen")
}