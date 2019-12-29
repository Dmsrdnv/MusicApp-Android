package pl.holker.music_app_android.functionalities.letters.model

import pl.holker.music_app_android.data.persistance.Letter

sealed class LetterEvent {
    data class InsertLetter(val title: String, val content: String) : LetterEvent()
    data class ShowEditDialog(val letter: Letter) : LetterEvent()
    data class EditLetter(val letter: Letter) : LetterEvent()
}