package study.com.findflight.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import study.com.findflight.data.repository.FlightsRepository
import study.com.findflight.ui.State


@ExtendWith(MockKExtension::class)
internal class FlightsViewModelTest {

    lateinit var viewModel: FlightsViewModel

    @MockK
    lateinit var statesView: Observer<State>

    @MockK
    lateinit var filterStatesView: Observer<State>

    @MockK
    lateinit var repository: FlightsRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @DisplayName("Given that I want search for available flights")
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = FlightsViewModel(repository, TestSchedulerProvider())

        viewModel.state.observeForever(statesView)
        viewModel.filterState.observeForever(filterStatesView)
    }

    @Test
    fun `when I get a available flights`() {

        coEvery { repository.getFlights() } returns MockedData.mockList

        viewModel.getFights()

        coVerify(exactly = 1) { repository.getFlights() }


    }
}
