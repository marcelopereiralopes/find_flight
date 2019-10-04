package study.com.findflight.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.Main


abstract class CoroutineViewModel : ViewModel() {
    private var jobs = listOf<Job>()

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        jobs = jobs + kotlinx.coroutines.GlobalScope.launch(context = Main, block = block)
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}