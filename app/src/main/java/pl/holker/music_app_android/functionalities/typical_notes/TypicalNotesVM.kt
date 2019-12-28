package pl.holker.music_app_android.functionalities.typical_notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import pl.holker.music_app_android.data.persistance.Note
import pl.holker.music_app_android.data.persistance.NoteDatabase
import pl.holker.music_app_android.functionalities.typical_notes.model.TypicalNoteEvent
import javax.inject.Inject

class TypicalNotesVM @Inject constructor(application: Application) : AndroidViewModel(application) {
    var datasource = NoteDatabase.getInstance(application.applicationContext).noteDao()
    var event = MutableLiveData<TypicalNoteEvent>()

    fun insertNote(title: String, content: String): Completable {
        val note = Note(0, title, content)
        return datasource.addNote(note)
    }

    fun getAmount(): Flowable<List<Note>> {
        val list = datasource.getAllNotes()
        return list.map { listNote: List<Note> ->
            listNote
        }
    }

    fun deleteById(id: Int): Completable {
        return datasource.deleteNoteById(id)
    }

    fun updateNote(note: Note): Completable {
        return datasource.updateNote(notes = *arrayOf(note))
    }

}