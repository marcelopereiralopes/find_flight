package study.com.findflight.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_query_flights.*
import kotlinx.android.synthetic.main.activity_flights.toolbar
import study.com.findflight.R
import study.com.findflight.util.PartsDayEnum
import java.lang.StringBuilder

class QueryFlightsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_flights)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.filtros)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        listener()

        configureFilters()
    }

    private fun listener() {
        applyQuery.setOnClickListener {
            action()
        }
    }

    private fun configureFilters() {
        val period = intent.getStringExtra(PERIODS_OF_THE_DAY)
        period?.split("/")?.forEach { it ->
            when(it){
                PartsDayEnum.MORNING.name -> morningFlight.isChecked = true
                PartsDayEnum.EVENING.name -> eveningFlight.isChecked = true
                PartsDayEnum.NIGHT.name -> nightFlight.isChecked = true
                PartsDayEnum.DAWN.name -> dawnFlight.isChecked = true
            }
        }

        val stops = intent.getStringExtra(NUMBER_OF_STOPS)
        stops?.split("/")?.forEach { it ->
            when(it){
                "0" -> directFlight.isChecked = true
                "1" -> oneStopFlight.isChecked = true
            }
        }
    }

    private fun action() {
        val i = intent

        val sbPeriodDay = StringBuilder()
        val sbNumberStops = StringBuilder()

        if (morningFlight.isChecked) sbPeriodDay.append(PartsDayEnum.MORNING.name).append("/")
        if (eveningFlight.isChecked) sbPeriodDay.append(PartsDayEnum.EVENING.name).append("/")
        if (nightFlight.isChecked) sbPeriodDay.append(PartsDayEnum.NIGHT.name).append("/")
        if (dawnFlight.isChecked) sbPeriodDay.append(PartsDayEnum.DAWN.name).append("/")
        if (directFlight.isChecked) sbNumberStops.append("0").append("/")
        if (oneStopFlight.isChecked) sbNumberStops.append("1").append("/")

        i.putExtra(PERIODS_OF_THE_DAY, sbPeriodDay.toString())
        i.putExtra(NUMBER_OF_STOPS, sbNumberStops.toString())

        setResult(QUERY_CODE, i)
        finish()
    }
}
