package study.com.findflight.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import study.com.findflight.ui.State
import study.com.findflight.ui.SuccessState
import study.com.findflight.ui.filter.Filter
import study.com.findflight.ui.filter.SortByPrice
import study.com.findflight.ui.viewmodel.resource.MockedFlightsForUnitTest.flightsMockList
import study.com.findflight.ui.viewmodel.resource.MockedFlightsForUnitTest.flightsMockListWithNightPeriod
import study.com.findflight.ui.viewmodel.resource.MockedFlightsForUnitTest.flightsOrderByHighestPriceMockList
import study.com.findflight.ui.viewmodel.resource.MockedFlightsForUnitTest.flightsOrderByLowestPriceMockList
import study.com.findflight.util.NumberStopEnum
import study.com.findflight.util.PeriodDayEnum
import study.com.findflight.util.SortFlightEnum


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

        coEvery { repository.getFlights() } returns flightsMockList

        viewModel.getFlights()

        coVerify(exactly = 1) { repository.getFlights() }

        Assert.assertEquals(SuccessState(flightsMockList), viewModel.state.value)
    }

    @Test
    fun `when I get internet connection error`() {

        val throwable = Throwable("Internet connection fail.")

        coEvery { repository.getFlights() } throws throwable

        viewModel.getFlights()

        coVerify(exactly = 1) { repository.getFlights() }

        Assert.assertEquals(
            ErrorState(throwable),
            viewModel.state.value
        )
    }

    @Test
    fun `when I order available flights by the lowest price`() {
        val observer = Observer<State> {}

        try {

            coEvery { repository.getFlights() } returns flightsMockList

            viewModel.filterState.observeForever(observer)

            viewModel.getFlights()

            viewModel.filterSortFlight(
                Pair(
                    listOf(
                        Filter.PeriodDayFilter(mutableListOf()),
                        Filter.NumberStopsFilter(mutableListOf())
                    ),
                    SortByPrice(SortFlightEnum.LOWESTPRICE.name)
                )
            )

            coVerify(exactly = 1) { repository.getFlights() }

            Assert.assertEquals(
                SuccessState(flightsOrderByLowestPriceMockList),
                viewModel.filterState.value
            )
        } finally {
            viewModel.filterState.removeObserver(observer)
        }
    }

    @Test
    fun `when I order available flights by the highest price`() {

        val observer = Observer<State> {}

        try {
            coEvery { repository.getFlights() } returns flightsMockList

            viewModel.filterState.observeForever(observer)

            viewModel.getFlights()

            viewModel.filterSortFlight(
                Pair(
                    listOf(
                        Filter.PeriodDayFilter(mutableListOf()),
                        Filter.NumberStopsFilter(mutableListOf())
                    ),
                    SortByPrice(SortFlightEnum.BIGGESTPRICE.name)
                )
            )

            coVerify(exactly = 1) { repository.getFlights() }

            Assert.assertEquals(
                SuccessState(flightsOrderByHighestPriceMockList),
                viewModel.filterState.value
            )

        } finally {
            viewModel.filterState.removeObserver(observer)
        }
    }

    @Test
    fun `when I want to filter for available night flights`() {

        val observer = Observer<State> {}

        try {
            coEvery { repository.getFlights() } returns flightsMockList

            viewModel.filterState.observeForever(observer)

            viewModel.getFlights()

            viewModel.filterSortFlight(
                Pair(
                    listOf(
                        Filter.PeriodDayFilter(mutableListOf(PeriodDayEnum.NIGHT.period)),
                        Filter.NumberStopsFilter(mutableListOf(NumberStopEnum.ONE.value))
                    ),
                    SortByPrice(SortFlightEnum.LOWESTPRICE.name)
                )
            )

            coVerify(exactly = 1) { repository.getFlights() }

            Assert.assertEquals(
                SuccessState(flightsMockListWithNightPeriod),
                viewModel.filterState.value
            )
        } finally {
            viewModel.filterState.removeObserver(observer)
        }
    }
}
