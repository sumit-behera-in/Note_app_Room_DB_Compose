package apps.sumit.noteapp.features.notes_feature.domain.use_case

import apps.sumit.noteapp.features.notes_feature.domain.models.Note
import apps.sumit.noteapp.features.notes_feature.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}