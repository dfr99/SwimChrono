package es.udc.apm.swimchrono.ui.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R

class RecyclerItemTrainerAdapter(
    private val dataSet: Array<String>
) :
    RecyclerView.Adapter<RecyclerItemTrainerAdapter.ViewHolder>() {
    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.text_user_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTrainer = dataSet[position]
        holder.name.text = itemTrainer
    }

    override fun getItemCount() = dataSet.size
}