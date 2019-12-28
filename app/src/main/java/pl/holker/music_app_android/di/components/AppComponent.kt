package pl.holker.music_app_android.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import pl.holker.music_app_android.musicApp
import pl.holker.music_app_android.di.modules.ActivityModule
import pl.holker.music_app_android.di.modules.AppModule
import pl.holker.music_app_android.di.modules.FragmentsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class, FragmentsModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: musicApp)
}