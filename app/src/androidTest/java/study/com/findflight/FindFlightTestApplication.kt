package study.com.findflight

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import study.com.findflight.di.appTestModules
import study.com.findflight.di.networkTestModules

class FindFlightTestApplication : FindFlightApplication() {

    override fun setupKoin() {
        startKoin {
            androidContext(this@FindFlightTestApplication)
            modules(listOf(appTestModules, networkTestModules))
        }
    }
}