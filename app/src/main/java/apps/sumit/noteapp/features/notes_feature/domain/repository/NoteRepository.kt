package apps.sumit.noteapp.features.notes_feature.domain.repository

import apps.sumit.noteapp.features.notes_feature.domain.models.Note
import kotlinx.coroutines.flow.Flow

// use this fake repo to get data from api / cache

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}