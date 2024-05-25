package es.udc.apm.swimchrono.ui.referee

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import android.widget.Toast
import es.udc.apm.swimchrono.model.Race
import es.udc.apm.swimchrono.model.Tournament

class TournamentExpandableListAdapter(
    private val context: Context,
    private val tournaments: List<Tournament>,
    private val onRaceSelected: (Int) -> Unit, // Callback for race selection
) : BaseExpandableListAdapter() {
    private var selectedRaceId: Int? = null

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return tournaments[groupPosition].races[childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return tournaments[groupPosition].races.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return tournaments[groupPosition]
    }

    override fun getGroupCount(): Int {
        return tournaments.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?,
    ): View {
        val tournament = getGroup(groupPosition) as Tournament
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = tournament.name
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?,
    ): View {
        val race = getChild(groupPosition, childPosition) as Race
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)
        text1.text = race.style
        text2.text =
            "${race.category} - ${race.distance} - ${race.heat} - ${race.lane} - ${race.hour}"



        view.setOnClickListener {
            Toast.makeText(context, "Selected Race: ${race.style}", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(context, TimerActivity::class.java).apply {
                putExtra("TOURNAMENT_ID", tournaments[groupPosition].id - 1)
                putExtra("RACE_ID", race.id - 1)
            }
            context.startActivity(intent)
        }

        return view
    }
}