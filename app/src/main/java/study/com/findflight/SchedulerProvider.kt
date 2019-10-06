package study.com.findflight

import kotlinx.coroutines.CoroutineDispatcher

interface SchedulerProvider {
    fun ui(): CoroutineDispatcher
}
