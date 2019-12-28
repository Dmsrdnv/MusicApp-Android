package pl.holker.music_app_android.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.holker.music_app_android.di.ActivityScope
import pl.holker.music_app_android.functionalities.main.MainActivity
import pl.holker.music_app_android.functionalities.start.StartActivity

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun contributeStartActivity(): StartActivity
}