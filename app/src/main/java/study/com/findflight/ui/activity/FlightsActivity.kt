package study.com.findflight.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_flights.*
import org.koin.android.viewmodel.ext.android.viewModel
import study.com.findflight.R
import study.com.findflight.ui.ErrorState
import study.com.findflight.ui.NumberStopsQuery
import study.com.findflight.ui.PeriodDayQuery
import study.com.findflight.ui.SuccessState
import study.com.findflight.ui.adapter.ViewPagerAdapter
import study.com.findflight.ui.fragment.InboundFlightsFragment
import study.com.findflight.ui.fragment.OutboundFlightsFragment
import study.com.findflight.ui.viewmodel.FlightsViewModel


class FlightsActivity : AppCompatActivity() {

    private val viewModel: FlightsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.voos)

        configureFragmentsWithViewPager()

        configureListenerFAB()

        viewModel.flightState.observe(this, Observer { state ->
            when (state) {
                is ErrorState -> {
                    showError(state.error)
                    fabFilter.hide()
                }
                is SuccessState -> fabFilter.show()
            }
        })

        viewModel.getFights()
    }

    private fun configureListenerFAB() {
        fabFilter.setOnClickListener {
            val i = Intent(this, QueryFlightsActivity::class.java)
            i.putExtra(PERIODS_OF_THE_DAY, periodsDay)
            i.putExtra(NUMBER_OF_STOPS, numberStops)
            startActivityForResult(i, QUERY_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> {
                periodsDay = data?.getStringExtra(PERIODS_OF_THE_DAY) ?: ""
                numberStops = data?.getStringExtra(NUMBER_OF_STOPS) ?: ""

                viewModel.searchFlightByQuery(
                    Pair(
                        PeriodDayQuery(periodsDay.split("/")),
                        NumberStopsQuery(numberStops.split("/"))
                    )
                )
            }
            2 -> {
            }
            else -> {
                Log.e("FlightsActivity", "Operation not allow!")
            }
        }
    }

    private fun showError(error: Throwable) {
        Log.e("FlightsActivity", error.toString())
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.indisponivel),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.retentar)) { viewModel.getFights() }
            .show()
    }


    private fun configureFragmentsWithViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(InboundFlightsFragment(), getString(R.string.ida))
        adapter.addFragment(OutboundFlightsFragment(), getString(R.string.volta))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private var periodsDay: String = ""
    private var numberStops: String = ""

}

const val SORT_CODE = 2
const val QUERY_CODE = 1

const val PERIODS_OF_THE_DAY = "PERIODS_OF_THE_DAY"
const val NUMBER_OF_STOPS = "NUMBER_OF_STOPS"