package es.udc.apm.swimchrono.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.model.Tournament
import es.udc.apm.swimchrono.util.Logger

interface OnTournamentItemClickListener {
    fun onItemClick(tournamentName: String)
}

class RecyclerTournamentAdapter(
    private val dataSet: List<Tournament>,
    private val listener: OnTournamentItemClickListener?
) :
    RecyclerView.Adapter<RecyclerTournamentAdapter.ViewHolder>() {

    inner class ViewHolder(
        view: View,
        private val listener: OnTournamentItemClickListener?
    ) : RecyclerView.ViewHolder(view) {

        val tournamentNameTextView: TextView = view.findViewById(R.id.tournament_name)
        val tournamentTagTextView: TextView = view.findViewById(R.id.tournament_tag)
        val tournamentDateTextView: TextView = view.findViewById(R.id.tournament_date)
        val tournamentPeopleTextView: TextView = view.findViewById(R.id.tournament_people)
        val tournamentLocationTextView: TextView = view.findViewById(R.id.tournament_location)


        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // FIXME: This should be with the ID not the name
                    val tournamentName =
                        dataSet[position].name

                    listener?.onItemClick(tournamentName)
                    Logger.debug(
                        this.javaClass.name,
                        "Listened click with args: [tournamentName = \"$tournamentName\"]"
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tournament_card, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tournament = dataSet[position]
        holder.tournamentTagTextView.text = tournament.type
        holder.tournamentNameTextView.text = tournament.name
        holder.tournamentDateTextView.text = tournament.date
        holder.tournamentPeopleTextView.text = tournament.participants.toString()
        holder.tournamentLocationTextView.text = tournament.location
    }


    override fun getItemCount() = dataSet.size
}