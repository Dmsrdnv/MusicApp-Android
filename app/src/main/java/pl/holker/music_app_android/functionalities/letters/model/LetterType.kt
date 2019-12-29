package pl.holker.music_app_android.functionalities.letters.model

sealed class LetterType {
    object EDIT : LetterType()
    object ADD : LetterType()
}