package pl.holker.music_app_android.functionalities.typical_notes.model

sealed class NoteType {
    object EDIT : NoteType()
    object ADD : NoteType()
}