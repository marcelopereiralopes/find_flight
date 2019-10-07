package study.com.findflight

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner


class MockRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, FindFlightTestApplication::class.java.name, context)
    }
}