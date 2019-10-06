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
import study.com.findflight.ui.*
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

        viewModel.state.observe(this, Observer { state ->
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
            val i = Intent(this, FilterFlightsActivity::class.java)
            i.putExtra(PERIODS_OF_THE_DAY, periodsDay)
            i.putExtra(NUMBER_OF_STOPS, numberStops)
            i.putExtra(SORT, sortBy)
            startActivityForResult(i, FILTER_CODE)
        }
    }

    private fun configureFragmentsWithViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(InboundFlightsFragment(), getString(R.string.ida))
        adapter.addFragment(OutboundFlightsFragment(), getString(R.string.volta))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> {
                periodsDay = data?.getStringExtra(PERIODS_OF_THE_DAY) ?: ""
                numberStops = data?.getStringExtra(NUMBER_OF_STOPS) ?: ""
                sortBy = data?.getStringExtra(SORT) ?: ""

                viewModel.filterSortFlightByParameters(
                    Pair(
                        listOf(
                            PeriodDayFilter(periodsDay.split("/")),
                            NumberStopsFilter(numberStops.split("/"))
                        ),
                        SortByPrice(sortBy)
                    )
                )
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

    private var periodsDay: String = ""
    private var numberStops: String = ""
    private var sortBy: String = ""

}

const val FILTER_CODE = 1

const val PERIODS_OF_THE_DAY = "PERIODS_OF_THE_DAY"
const val NUMBER_OF_STOPS = "NUMBER_OF_STOPS"
const val SORT = "SORT"