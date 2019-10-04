package study.com.findflight.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_list_flights.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import study.com.findflight.R
import study.com.findflight.domain.FlightModel
import study.com.findflight.ui.adapter.FlightsAdapter
import study.com.findflight.ui.viewmodel.FlightsViewModel


open class FlightsBaseFragment : Fragment() {

    private val adapter: FlightsAdapter by inject()

    protected val model: FlightsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_list_flights, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewConfig()
    }

    private fun recyclerViewConfig() {
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    protected fun errorConfig() {
        numberOfFlights.visibility = View.GONE
        progressBar.visibility = View.GONE
        emptyList.visibility = View.VISIBLE
    }

    protected fun loadingConfig() {
        progressBar.visibility = View.VISIBLE
        emptyList.visibility = View.GONE
        numberOfFlights.visibility = View.GONE
    }

    protected fun successConfig(flightModelList: List<FlightModel>) {
        val flights = flightModelList.size
        progressBar.visibility = View.GONE
        numberOfFlights.visibility = View.VISIBLE

        if (flights > 0) {
            emptyList.visibility = View.GONE
        } else {
            emptyList.visibility = View.VISIBLE
            numberOfFlights.visibility = View.GONE
        }
        val textInput = "Encontramos $flights voos"
        numberOfFlights.text = textInput

        adapter.update(flightModelList)
    }
}