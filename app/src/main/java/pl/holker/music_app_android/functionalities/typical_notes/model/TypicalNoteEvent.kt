package pl.holker.music_app_android.functionalities.typical_notes.model

import pl.holker.music_app_android.data.persistance.Note

sealed class TypicalNoteEvent {
    data class InsertNote(val title: String, val content: String) : TypicalNoteEvent()
    data class ShowEditDialog(val note: Note) : TypicalNoteEvent()
    data class EditNote(val note: Note) : TypicalNoteEvent()
}