package es.udc.apm.swimchrono.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R


class TodayTournamentAdapter(private val dataSet: Array<Array<String>>) :
    RecyclerView.Adapter<TodayTournamentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tournamentNameTextView: TextView = view.findViewById(R.id.tournament_name)
        val tournamentTagTextView: TextView = view.findViewById(R.id.tournament_tag)
        val tournamentDateTextView: TextView = view.findViewById(R.id.tournament_date)
        val tournamentPeopleTextView: TextView = view.findViewById(R.id.tournament_people)
        val tournamentLocationTextView: TextView = view.findViewById(R.id.tournament_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tournament_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tournament = dataSet[position]
        holder.tournamentTagTextView.text = tournament[0]
        holder.tournamentNameTextView.text = tournament[1]
        holder.tournamentDateTextView.text = tournament[2]
        holder.tournamentPeopleTextView.text = tournament[3]
        holder.tournamentLocationTextView.text = tournament[4]
    }

    override fun getItemCount() = dataSet.size
}