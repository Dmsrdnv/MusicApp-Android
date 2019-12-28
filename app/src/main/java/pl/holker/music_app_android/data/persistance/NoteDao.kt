package pl.holker.music_app_android.data.persistance

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flowable<List<Note>>

    @Query("SELECT * FROM location_notes")
    fun getLocationNotes(): Flowable<List<NoteLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocationNote(noteLocation: NoteLocation): Completable

    @Query("DELETE FROM notes")
    fun deleteAllNotes(): Completable

    @Query("DELETE FROM notes WHERE note_id = :noteId")
    fun deleteNoteById(noteId: Int): Completable

    @Query("DELETE FROM location_notes")
    fun deleteAllLocationNotes()

    @Query("DELETE FROM location_notes WHERE note_location_id = :noteId")
    fun deleteLocationNoteById(noteId: Int)

    @Update
    fun updateNote(vararg notes: Note): Completable
}