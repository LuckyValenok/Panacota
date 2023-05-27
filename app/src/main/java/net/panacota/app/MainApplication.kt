package net.panacota.app

import android.app.Application
import net.panacota.app.di.AppComponent
import net.panacota.app.di.DaggerAppComponent

class MainApplication : Application() {
    val component: AppComponent = DaggerAppComponent.factory().create(this)
}