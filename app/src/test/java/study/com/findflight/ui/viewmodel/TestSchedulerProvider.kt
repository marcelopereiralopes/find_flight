package study.com.findflight.ui.viewmodel

import kotlinx.coroutines.Dispatchers
import study.com.findflight.SchedulerProvider

class TestSchedulerProvider: SchedulerProvider {
    override fun ui() = Dispatchers.Unconfined
}
