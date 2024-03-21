package es.udc.apm.swimchrono.ui.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R

class ClubDataAdapter(
    private val dataSet: Array<String>
) :
    RecyclerView.Adapter<ClubDataAdapter.ViewHolder>() {

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val nombreClub: TextView = view.findViewById(R.id.nombre_club)
        val direccion: TextView = view.findViewById(R.id.direccion)
        val telefono: TextView = view.findViewById(R.id.telefono)
        val miembros: TextView = view.findViewById(R.id.miembros)
        val url: TextView = view.findViewById(R.id.pagina_web)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_club_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Parsear datos de item_club_card")
        // val clubData = dataSet[position]
        // holder.name.text = clubMember
    }

    override fun getItemCount() = dataSet.size
}