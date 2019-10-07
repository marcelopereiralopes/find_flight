package study.com.findflight

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import study.com.findflight.di.appModules
import study.com.findflight.di.networkModules


open class FindFlightApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startComponent()
    }

    open fun startComponent() {
        startKoin {
            androidContext(this@FindFlightApplication)
            modules(listOf(appModules, networkModules))
        }
    }
}