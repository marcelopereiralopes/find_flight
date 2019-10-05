package study.com.findflight.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_filter_flights.*
import kotlinx.android.synthetic.main.activity_flights.toolbar
import study.com.findflight.R
import study.com.findflight.util.NumberStopEnum
import study.com.findflight.util.PeriodDayEnum
import study.com.findflight.util.SortFlightEnum
import java.lang.StringBuilder

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
        val period = intent.getStringExtra(PERIODS_OF_THE_DAY)
        period?.split("/")?.forEach { it ->
            when(it){
                PeriodDayEnum.MORNING.period -> morningFlight.isChecked = true
                PeriodDayEnum.EVENING.period -> eveningFlight.isChecked = true
                PeriodDayEnum.NIGHT.period -> nightFlight.isChecked = true
                PeriodDayEnum.DAWN.period -> dawnFlight.isChecked = true
            }
        }

        val stops = intent.getStringExtra(NUMBER_OF_STOPS)
        stops?.split("/")?.forEach { it ->
            when(it){
                NumberStopEnum.ZERO.value -> directFlight.isChecked = true
                NumberStopEnum.ONE.value -> oneStopFlight.isChecked = true
            }
        }

        val sortBy = intent.getStringExtra(SORT)
        sortBy?.let {
            when(it) {
                SortFlightEnum.BIGGESTPRICE.name -> biggestPrice.isChecked = true
                SortFlightEnum.LOWESTPRICE.name -> lowestPrice.isChecked = true
                SortFlightEnum.LOWESTPRICEANDTIME.name -> lowestTimeAndPrice.isChecked = true
            }
        }
    }

    private fun action() {
        val i = intent

        val sbPeriodDay = StringBuilder()
        val sbNumberStops = StringBuilder()

        if (morningFlight.isChecked) sbPeriodDay.append(PeriodDayEnum.MORNING.period).append("/")
        if (eveningFlight.isChecked) sbPeriodDay.append(PeriodDayEnum.EVENING.period).append("/")
        if (nightFlight.isChecked) sbPeriodDay.append(PeriodDayEnum.NIGHT.period).append("/")
        if (dawnFlight.isChecked) sbPeriodDay.append(PeriodDayEnum.DAWN.period).append("/")

        if (directFlight.isChecked) sbNumberStops.append(NumberStopEnum.ZERO.value).append("/")
        if (oneStopFlight.isChecked) sbNumberStops.append(NumberStopEnum.ONE.value).append("/")

        i.putExtra(PERIODS_OF_THE_DAY, sbPeriodDay.toString())
        i.putExtra(NUMBER_OF_STOPS, sbNumberStops.toString())

        when (radioGroupSort.checkedRadioButtonId) {
            R.id.biggestPrice -> {i.putExtra(SORT, SortFlightEnum.BIGGESTPRICE.name)}
            R.id.lowestPrice -> {i.putExtra(SORT, SortFlightEnum.LOWESTPRICE.name)}
            R.id.lowestTimeAndPrice -> {i.putExtra(SORT, SortFlightEnum.LOWESTPRICEANDTIME.name)}
        }

        setResult(FILTER_CODE, i)
        finish()
    }
}
