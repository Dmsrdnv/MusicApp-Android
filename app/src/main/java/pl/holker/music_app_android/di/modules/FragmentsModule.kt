package pl.holker.music_app_android.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.holker.music_app_android.functionalities.location_notes.LocationNotesFragment
import pl.holker.music_app_android.functionalities.typical_notes.TypicalNotesFragment
import pl.holker.music_app_android.functionalities.location_letters.LocationLettersFragment
import pl.holker.music_app_android.functionalities.letters.LettersFragment

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector
    abstract fun contributeTypicalNoteFragment(): TypicalNotesFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationNoteFragment(): LocationNotesFragment

    @ContributesAndroidInjector
    abstract fun contributeLettersFragment(): LettersFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationLettersFragment(): LocationLettersFragment
}