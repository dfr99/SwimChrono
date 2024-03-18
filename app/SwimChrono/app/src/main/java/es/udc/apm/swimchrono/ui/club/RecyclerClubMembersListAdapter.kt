package es.udc.apm.swimchrono.ui.club

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R

class RecyclerClubMembersListAdapter(
    private val dataSet: Array<String>
) :
    RecyclerView.Adapter<RecyclerClubMembersListAdapter.ViewHolder>() {

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {


        val clubMemberName: TextView = view.findViewById(R.id.club_member_list)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_card, parent, false)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clubMember = dataSet[position]
        holder.clubMemberName.text = clubMember[0]
        TODO("Not yet implemented")
    }

    override fun getItemCount() = dataSet.size
}