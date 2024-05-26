package es.udc.apm.swimchrono.ui.tournaments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.model.Race
import java.text.SimpleDateFormat
import java.util.Locale


class RecyclerRaceAdapter(
    private val dataSet: List<Race>,
) :
    RecyclerView.Adapter<RecyclerRaceAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        val raceNumberTextView: TextView = view.findViewById(R.id.race_number)
        val raceDateTextView: TextView = view.findViewById(R.id.race_date)
        val raceStyleTextView: TextView = view.findViewById(R.id.race_style)
        val raceTotalTextView: TextView = view.findViewById(R.id.race_total)
        val tableLayout: TableLayout =
            view.findViewById(R.id.race_table_layout) // Asegúrate de tener un TableLayout en el layout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_race_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val race = dataSet[position]
        holder.raceNumberTextView.text = race.id.toString()

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = timeFormat.format(race.hour!!)

        holder.raceDateTextView.text = formattedTime
        holder.raceStyleTextView.text = race.style
        holder.raceTotalTextView.text = race.distance

        holder.tableLayout.removeAllViews()

        val headerRow = TableRow(holder.itemView.context).apply {
            addView(TextView(holder.itemView.context).apply {
                text = holder.itemView.context.getString(R.string.uid)
                setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            })
            addView(TextView(holder.itemView.context).apply {
                text = holder.itemView.context.getString(R.string.time)
                setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            })
        }
        holder.tableLayout.addView(headerRow)

        // Agregar las filas dinámicamente a la tabla
        for ((uid, time) in race.times) {
            addRowToTable(holder.tableLayout, uid, time, holder.itemView.context)
        }
    }


    override fun getItemCount() = dataSet.size


    fun addRowToTable(tableLayout: TableLayout, uid: String, time: String, context: Context) {
        val tableRow = TableRow(context)

        val uidTextView = TextView(context).apply {
            text = uid
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setBackgroundColor(ContextCompat.getColor(context, R.color.forest_blue_700))
            setPadding(8, 8, 8, 8)
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }

        val timeTextView = TextView(context).apply {
            text = time
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setBackgroundColor(ContextCompat.getColor(context, R.color.forest_blue_700))
            setPadding(8, 8, 8, 8)
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }

        tableRow.addView(uidTextView)
        tableRow.addView(timeTextView)

        tableLayout.addView(tableRow)
    }
}