package pl.holker.music_app_android

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import dagger.android.*
import pl.holker.music_app_android.di.AppInjector
import pl.holker.music_app_android.di.components.AppComponent
// http
//import pl.holker.music_app_android.data.model.Person
//import pl.holker.music_app_android.data.model.ResponseMany
//import pl.holker.music_app_android.data.model.ResponseOne
//import pl.holker.music_app_android.data.repo.GendXApiService
//
import javax.inject.Inject

open class musicApp : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingAndroidServiceInjector: DispatchingAndroidInjector<Service>
    lateinit var appComponent: AppComponent

    companion object {
        fun getAppComponent(context: Context): AppComponent = (context as musicApp).appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = AppInjector.init(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingAndroidInjector

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingAndroidServiceInjector

}