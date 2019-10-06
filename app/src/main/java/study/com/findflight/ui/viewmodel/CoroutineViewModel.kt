package study.com.findflight.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import study.com.findflight.SchedulerProvider


abstract class CoroutineViewModel(private val scheduledProvider: SchedulerProvider) : ViewModel() {
    private var jobs = listOf<Job>()

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        jobs = jobs + kotlinx.coroutines.GlobalScope.launch(
            context = scheduledProvider.ui(),
            block = block
        )
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}