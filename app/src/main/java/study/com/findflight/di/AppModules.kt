package study.com.findflight.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import study.com.findflight.ApplicationSchedulerProvider
import study.com.findflight.SchedulerProvider
import study.com.findflight.data.repository.FlightsRepository
import study.com.findflight.ui.adapter.FlightsAdapter
import study.com.findflight.ui.viewmodel.FlightsViewModel

val appModules = module {

    factory<FlightsAdapter> {
        FlightsAdapter()
    }

    single<FlightsRepository> {
        FlightsRepository(get())
    }

    single<SchedulerProvider> {
        ApplicationSchedulerProvider()
    }

    viewModel<FlightsViewModel> {
        FlightsViewModel(get(), get())
    }
}