package study.com.findflight.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_flights.*
import org.koin.android.viewmodel.ext.android.viewModel
import study.com.findflight.R
import study.com.findflight.ui.ErrorState
import study.com.findflight.ui.NumberStopsQuery
import study.com.findflight.ui.PartsDayQuery
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

        viewModel.flightState.observe(this, Observer { state ->
            when (state) {
                is ErrorState -> showError(state.error)
            }
        })

        viewModel.getFights()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            val i = Intent(this, QueryFlightsActivity::class.java)
            startActivityForResult(i, QUERY_CODE)
            true
        }
        R.id.action_sort -> {
            val i = Intent(this, SortFlightsActivity::class.java)
            startActivityForResult(i, SORT_CODE)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                val partsDay: List<String> =
                    data?.getStringArrayListExtra(PARTS_DAY) ?: emptyList()
                val numberStops: List<Int> =
                    data?.getIntegerArrayListExtra(NUMBER_STOP) ?: emptyList()

                viewModel.searchFlightByQuery(
                    Pair(
                        PartsDayQuery(partsDay),
                        NumberStopsQuery(numberStops)
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

}

const val SORT_CODE = 2
const val QUERY_CODE = 1

const val PARTS_DAY = "partsDay"
const val NUMBER_STOP = "numberStops"