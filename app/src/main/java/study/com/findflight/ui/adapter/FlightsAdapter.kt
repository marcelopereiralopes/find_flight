package study.com.findflight.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.flight_item.view.*
import study.com.findflight.R
import study.com.findflight.domain.FlightModel
import study.com.findflight.util.Converter


class FlightsAdapter(
    private val flights: MutableList<FlightModel> = mutableListOf()) : RecyclerView.Adapter<FlightsAdapter.FlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FlightViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent,false)
    )

    override fun getItemCount() = flights.size

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(flights[position])
    }

    fun update(flights: List<FlightModel>) {
        notifyItemRangeRemoved(0, this.flights.size)
        this.flights.clear()
        this.flights.addAll(flights)
        notifyItemRangeInserted(0, flights.size)
    }


    inner class FlightViewHolder(private val flightView: View) :
        RecyclerView.ViewHolder(flightView) {

        fun bind(flight: FlightModel) = with(flightView) {
            from.text = flight.from
            to.text = flight.to
            value.text = flight.value.toString()
            departure.text = Converter.calendarExtractHourAndMinute(flight.departure)
            arrival.text = Converter.calendarExtractHourAndMinute(flight.arrival)
            val timeAndStopText =
                Converter.calendarDiffHoursAndMinutes(
                    flight.departure,
                    flight.arrival
                ) + ", ${flight.stops} parada"
            timeAndNumberStops.text = timeAndStopText
        }

    }
}