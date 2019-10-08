package study.com.findflight

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import study.com.findflight.di.appModules
import study.com.findflight.di.networkModules

class FindFlightTestApplication : FindFlightApplication() {

    override fun setupKoin() {
        startKoin {
            androidContext(this@FindFlightTestApplication)
            modules(listOf(appModules, networkModules))
            properties(mapOf("SERVER_BASE_URL" to "http://localhost:8180/"))
        }
    }
}