package study.com.findflight

import kotlinx.coroutines.Dispatchers

class ApplicationSchedulerProvider: SchedulerProvider {
    override fun ui() = Dispatchers.Main
}
