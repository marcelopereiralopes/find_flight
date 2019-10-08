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
import study.com.findflight.ui.SuccessState
import study.com.findflight.ui.adapter.ViewPagerAdapter
import study.com.findflight.ui.filter.ManagerFilter
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

        viewModel.getFlights()
    }

    private fun configureListenerFAB() {
        fabFilter.setOnClickListener {
            val i = Intent(this, FilterFlightsActivity::class.java)
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
                viewModel.filterSortFlight(
                    Pair(
                        listOf(
                            ManagerFilter.periodDayFilter,
                            ManagerFilter.numberStopsFilter
                        ),
                        ManagerFilter.sortByPrice
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
            .setAction(getString(R.string.retentar)) { viewModel.getFlights() }
            .show()
    }

}
const val FILTER_CODE = 1