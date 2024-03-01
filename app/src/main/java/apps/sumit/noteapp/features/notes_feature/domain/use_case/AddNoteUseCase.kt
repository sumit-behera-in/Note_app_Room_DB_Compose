package apps.sumit.noteapp.features.notes_feature.domain.use_case

import apps.sumit.noteapp.features.notes_feature.domain.models.InvalidNotesException
import apps.sumit.noteapp.features.notes_feature.domain.models.Note
import apps.sumit.noteapp.features.notes_feature.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNotesException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNotesException("The Title cant be blank.")
        }
        if (note.content.isBlank()) {
            throw InvalidNotesException("The content cant be blank.")
        }
        repository.insertNote(note)
    }
}