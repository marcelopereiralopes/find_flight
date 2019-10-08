package study.com.findflight.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_filter_flights.*
import kotlinx.android.synthetic.main.activity_flights.toolbar
import study.com.findflight.R
import study.com.findflight.ui.filter.ManagerFilter
import study.com.findflight.util.NumberStopEnum
import study.com.findflight.util.PeriodDayEnum
import study.com.findflight.util.SortFlightEnum

class FilterFlightsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_flights)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.filtros)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        listener()

        configureLayout()
    }

    private fun listener() {
        applyFilter.setOnClickListener {
            action()
        }
    }

    private fun configureLayout() {
        val period = ManagerFilter.periodDayFilter.parts
        period.forEach {
            when (it) {
                PeriodDayEnum.MORNING.period -> morningFlight.isChecked = true
                PeriodDayEnum.EVENING.period -> eveningFlight.isChecked = true
                PeriodDayEnum.NIGHT.period -> nightFlight.isChecked = true
                PeriodDayEnum.DAWN.period -> dawnFlight.isChecked = true
            }
        }

        val stops = ManagerFilter.numberStopsFilter.number
        stops.forEach {
            when (it) {
                NumberStopEnum.ZERO.value -> directFlight.isChecked = true
                NumberStopEnum.ONE.value -> oneStopFlight.isChecked = true
            }
        }

        val sortBy = ManagerFilter.sortByPrice.value
        sortBy.let {
            when (it) {
                SortFlightEnum.BIGGESTPRICE.name -> biggestPrice.isChecked = true
                SortFlightEnum.LOWESTPRICE.name -> lowestPrice.isChecked = true
                SortFlightEnum.LOWESTPRICEANDTIME.name -> lowestTimeAndPrice.isChecked = true
            }
        }
    }

    private fun action() {
        val i = intent

        val updatePeriodDay = mutableListOf<String>()
        val updateNumberStops = mutableListOf<String>()

        if (morningFlight.isChecked) updatePeriodDay.add(PeriodDayEnum.MORNING.period)
        if (eveningFlight.isChecked) updatePeriodDay.add(PeriodDayEnum.EVENING.period)
        if (nightFlight.isChecked) updatePeriodDay.add(PeriodDayEnum.NIGHT.period)
        if (dawnFlight.isChecked) updatePeriodDay.add(PeriodDayEnum.DAWN.period)

        if (directFlight.isChecked) updateNumberStops.add(NumberStopEnum.ZERO.value)
        if (oneStopFlight.isChecked) updateNumberStops.add(NumberStopEnum.ONE.value)

        ManagerFilter.periodDayFilter.parts = updatePeriodDay
        ManagerFilter.numberStopsFilter.number = updateNumberStops

        when (radioGroupSort.checkedRadioButtonId) {
            R.id.biggestPrice -> {
                ManagerFilter.sortByPrice.value = SortFlightEnum.BIGGESTPRICE.name
            }
            R.id.lowestPrice -> {
                ManagerFilter.sortByPrice.value = SortFlightEnum.LOWESTPRICE.name
            }
            R.id.lowestTimeAndPrice -> {
                ManagerFilter.sortByPrice.value = SortFlightEnum.LOWESTPRICEANDTIME.name
            }
        }

        setResult(FILTER_CODE, i)
        finish()
    }
}
