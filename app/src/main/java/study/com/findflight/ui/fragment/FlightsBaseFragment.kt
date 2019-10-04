package study.com.findflight.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import study.com.findflight.R
import study.com.findflight.ui.adapter.FlightsAdapter
import study.com.findflight.ui.viewmodel.FlightsViewModel


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
}