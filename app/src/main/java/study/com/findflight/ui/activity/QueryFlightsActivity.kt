package study.com.findflight.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_query_flights.*
import kotlinx.android.synthetic.main.activity_flights.toolbar
import study.com.findflight.R
import study.com.findflight.util.PartsDayEnum

class QueryFlightsActivity : AppCompatActivity() {

    private val partsDay = ArrayList<String>()
    private var numberStops = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_flights)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.filtros)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        listener()
    }

    private fun listener() {
        applyQuery.setOnClickListener {
            action()
        }
    }

    private fun action() {
        val i = intent

        if (morningFlight.isChecked) partsDay.add(PartsDayEnum.MORNING.name)
        if (eveningFlight.isChecked) partsDay.add(PartsDayEnum.EVENING.name)
        if (nightFlight.isChecked) partsDay.add(PartsDayEnum.NIGHT.name)
        if (dawnFlight.isChecked) partsDay.add(PartsDayEnum.DAWN.name)
        if (directFlight.isChecked) numberStops.add(0)
        if (oneStopFlight.isChecked) numberStops.add(1)

        i.putExtra(PARTS_DAY, partsDay)
        i.putExtra(NUMBER_STOP, numberStops)

        setResult(QUERY_CODE, i)
        finish()
    }
}
