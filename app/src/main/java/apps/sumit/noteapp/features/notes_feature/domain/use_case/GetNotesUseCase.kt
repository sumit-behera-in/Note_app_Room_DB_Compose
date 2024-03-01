package apps.sumit.noteapp.features.notes_feature.domain.use_case

import apps.sumit.noteapp.features.notes_feature.domain.models.Note
import apps.sumit.noteapp.features.notes_feature.domain.repository.NoteRepository
import apps.sumit.noteapp.features.notes_feature.domain.util.NoteOrder
import apps.sumit.noteapp.features.notes_feature.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> {
                            notes.sortedBy { it.title.lowercase() }
                        }

                        is NoteOrder.Date -> {
                            notes.sortedBy { it.timeStamp }
                        }

                        is NoteOrder.Color -> {
                            notes.sortedBy { it.color }
                        }
                    }
                }

                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> {
                            notes.sortedByDescending { it.title.lowercase() }
                        }

                        is NoteOrder.Date -> {
                            notes.sortedByDescending { it.timeStamp }
                        }

                        is NoteOrder.Color -> {
                            notes.sortedByDescending { it.color }
                        }
                    }
                }
            }
        }
    }
}