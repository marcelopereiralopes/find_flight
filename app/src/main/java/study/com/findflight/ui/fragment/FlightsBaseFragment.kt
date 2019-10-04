package study.com.findflight.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_flights.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import study.com.findflight.R
import study.com.findflight.domain.FlightModel
import study.com.findflight.ui.SuccessState
import study.com.findflight.ui.adapter.FlightsAdapter
import study.com.findflight.ui.viewmodel.FlightsViewModel
import kotlin.collections.emptyList


open class FlightsBaseFragment : Fragment() {

    protected val adapter: FlightsAdapter by inject()

    protected val model: FlightsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_list_flights, container, false)

        recyclerViewConfig(rootView, adapter)

        return rootView
    }

    private fun recyclerViewConfig(view: View, adapter: FlightsAdapter) {
        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        val divisor = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(divisor)
        recyclerView.adapter = adapter
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