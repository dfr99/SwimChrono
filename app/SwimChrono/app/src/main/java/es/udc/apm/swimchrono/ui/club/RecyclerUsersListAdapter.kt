package es.udc.apm.swimchrono.ui.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R

class RecyclerUsersListAdapter(
    private val dataSet: List<HashMap<String,String>>
) :
    RecyclerView.Adapter<RecyclerUsersListAdapter.ViewHolder>() {

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.text_user_name)
        val email: TextView = view.findViewById(R.id.text_user_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clubMember = dataSet[position]

        holder.name.text = clubMember["nombre"].plus(" ").plus(clubMember["apellido"])
        holder.email.text = clubMember["email"]
    }

    override fun getItemCount() = dataSet.size
}



