package es.udc.apm.swimchrono.ui.referee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.model.Race
import java.text.SimpleDateFormat
import java.util.Locale


class RecyclerRacesListAdapter(
    private val dataSet: Array<Race>
) :
    RecyclerView.Adapter<RecyclerRacesListAdapter.ViewHolder>() {
    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val swimmer : TextView = view.findViewById(R.id.swimmer)
        val club : TextView = view.findViewById(R.id.club)
        val race : TextView = view.findViewById(R.id.race)
        val heat : TextView = view.findViewById(R.id.heat)
        val lane : TextView = view.findViewById(R.id.lane)
        val hour : TextView = view.findViewById(R.id.hour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_race_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataRace = dataSet[position]
        holder.swimmer.text = dataRace.swimmer
        holder.club.text = dataRace.club
        holder.race.text = dataRace.race
        holder.heat.text = dataRace.heat.toString()
        holder.lane.text = dataRace.lane.toString()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.hour.text = dateFormat.format(dataRace.hour)
    }

    override fun getItemCount() = dataSet.size
}