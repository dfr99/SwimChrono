package es.udc.apm.swimchrono.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.util.Logger

interface OnTournamentItemClickListener {
    fun onItemClick(tournamentName: String)
}

class RecyclerTournamentAdapter(
    private val dataSet: Array<Array<String>>,
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
                        dataSet[position][2]

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
        holder.tournamentTagTextView.text = tournament[1]
        holder.tournamentNameTextView.text = tournament[2]
        holder.tournamentDateTextView.text = tournament[3]
        holder.tournamentPeopleTextView.text = tournament[4]
        holder.tournamentLocationTextView.text = tournament[5]
    }


    override fun getItemCount() = dataSet.size
}