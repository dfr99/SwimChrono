package es.udc.apm.swimchrono.ui.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.model.Club

class ClubDataAdapter(
    private val dataSet: List<Club>
) :
    RecyclerView.Adapter<ClubDataAdapter.ViewHolder>() {

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val clubName: TextView = view.findViewById(R.id.nombre_club)
        val address: TextView = view.findViewById(R.id.direccion)
        val phone: TextView = view.findViewById(R.id.telefono)
        val url: TextView = view.findViewById(R.id.pagina_web)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_club_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clubData = dataSet[position]

        holder.clubName.text = clubData.name
        holder.address.text = clubData.address
        holder.phone.text = clubData.phone
        holder.url.text = clubData.url
    }

    override fun getItemCount() = dataSet.size
}