package es.udc.apm.swimchrono.ui.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R

class RecyclerItemTrainerAdapter(
    private val dataSet: List<Array<String>>
) :
    RecyclerView.Adapter<RecyclerItemTrainerAdapter.ViewHolder>() {
    inner class ViewHolder(
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
        val itemTrainer = dataSet[position]

        holder.name.text = itemTrainer[0].plus(" ").plus(itemTrainer[1])
        holder.email.text = itemTrainer[2]
    }

    override fun getItemCount() = dataSet.size
}