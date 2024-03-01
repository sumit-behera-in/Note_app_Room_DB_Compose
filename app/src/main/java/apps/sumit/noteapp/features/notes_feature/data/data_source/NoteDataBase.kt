package apps.sumit.noteapp.features.notes_feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import apps.sumit.noteapp.features.notes_feature.domain.models.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDataBase : RoomDatabase() {

    abstract val noteDAO: NoteDAO

    companion object {
        const val DATABASE_NAME: String = "NoteDB"
    }
}