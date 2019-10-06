package study.com.findflight.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import study.com.findflight.data.repository.FlightsRepository
import study.com.findflight.ui.ErrorState
import study.com.findflight.ui.SuccessState
import study.com.findflight.ui.viewmodel.MockedData.mockList


@ExtendWith(MockKExtension::class)
internal class FlightsViewModelTest {

    lateinit var viewModel: FlightsViewModel

    @MockK
    lateinit var repository: FlightsRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @DisplayName("Given that I want search for available flights")
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = FlightsViewModel(repository, TestSchedulerProvider())

    }

    @Test
    fun `when I get a available flights`() {

        coEvery { repository.getFlights() } returns mockList

        viewModel.getFights()

        coVerify(exactly = 1) { repository.getFlights() }

        Assert.assertEquals(SuccessState(mockList), viewModel.state.value)
    }

    @Test
    fun `when I get internet connection error`() {

        val throwable = Throwable("Internet connection fail.")

        coEvery { repository.getFlights() } throws throwable

        viewModel.getFights()

        coVerify(exactly = 1) { repository.getFlights() }

        Assert.assertEquals(ErrorState(throwable), viewModel.state.value)
    }
}
