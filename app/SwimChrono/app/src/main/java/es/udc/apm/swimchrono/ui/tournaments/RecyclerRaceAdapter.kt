package es.udc.apm.swimchrono.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R


class RecyclerRaceAdapter(
    private val dataSet: Array<Array<String>>
) :
    RecyclerView.Adapter<RecyclerRaceAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        val raceNumberTextView: TextView = view.findViewById(R.id.race_number)
        val raceDateTextView: TextView = view.findViewById(R.id.race_date)
        val raceStyleTextView: TextView = view.findViewById(R.id.race_style)
        val raceTotalTextView: TextView = view.findViewById(R.id.race_total)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_race_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tournament = dataSet[position]
        holder.raceNumberTextView.text = tournament[0]
        holder.raceDateTextView.text = tournament[1]
        holder.raceStyleTextView.text = tournament[2]
        holder.raceTotalTextView.text = tournament[3]
    }


    override fun getItemCount() = dataSet.size
}