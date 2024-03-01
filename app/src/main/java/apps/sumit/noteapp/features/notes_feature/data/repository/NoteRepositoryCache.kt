package apps.sumit.noteapp.features.notes_feature.data.repository

import apps.sumit.noteapp.features.notes_feature.data.data_source.NoteDAO
import apps.sumit.noteapp.features.notes_feature.domain.models.Note
import apps.sumit.noteapp.features.notes_feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryCache(private val dao: NoteDAO) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}