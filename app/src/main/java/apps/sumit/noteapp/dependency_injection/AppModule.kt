package apps.sumit.noteapp.dependency_injection

import android.app.Application
import androidx.room.Room
import apps.sumit.noteapp.features.notes_feature.data.data_source.NoteDataBase
import apps.sumit.noteapp.features.notes_feature.data.repository.NoteRepositoryCache
import apps.sumit.noteapp.features.notes_feature.domain.repository.NoteRepository
import apps.sumit.noteapp.features.notes_feature.domain.use_case.DeleteNoteUseCase
import apps.sumit.noteapp.features.notes_feature.domain.use_case.GetNotesUseCase
import apps.sumit.noteapp.features.notes_feature.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDataBase): NoteRepository {
        return NoteRepositoryCache(db.noteDAO)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository)
        )
    }
}