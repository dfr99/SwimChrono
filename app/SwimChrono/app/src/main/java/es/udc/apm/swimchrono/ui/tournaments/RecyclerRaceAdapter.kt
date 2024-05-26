package es.udc.apm.swimchrono.ui.tournaments

import android.app.Activity
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
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import java.text.SimpleDateFormat
import java.util.Locale


class RecyclerRaceAdapter(
    private val dataSet: List<Race>,
) :
    RecyclerView.Adapter<RecyclerRaceAdapter.ViewHolder>() {

    private var apiService = ApiService()

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
                text = holder.itemView.context.getString(R.string.dni)
                setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            })

            addView(TextView(holder.itemView.context).apply {
                text = holder.itemView.context.getString(R.string.name)
                setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                setPadding(8, 8, 8, 8)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            })
            addView(TextView(holder.itemView.context).apply {
                text = holder.itemView.context.getString(R.string.surname)
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

        val timeTextView = TextView(context).apply {
            text = time
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setBackgroundColor(ContextCompat.getColor(context, R.color.forest_blue_700))
            setPadding(8, 8, 8, 8)
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        }

        tableRow.addView(timeTextView)

        tableLayout.addView(tableRow)

        apiService = ApiService()
        apiService.onCreate()


        // Obtener los datos del usuario y actualizar la fila de la tabla
        apiService.getUserData(uid, object : ApiServiceCallback {
            override fun onDataReceived(userData: Any?) {
                (userData as? Map<String, Any>)?.let {
                    val name = it["nombre"] as? String ?: "Unknown"
                    val surname = it["apellido"] as? String ?: "Unknown"
                    val dni = it["DNI"] as? String ?: "Unknown"

                    // Actualizar la fila de la tabla con los datos del usuario
                    (context as Activity).runOnUiThread {
                        tableRow.removeAllViews()

                        val dniTextView = TextView(context).apply {
                            text = dni
                            setTextColor(ContextCompat.getColor(context, R.color.white))
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.forest_blue_700
                                )
                            )
                            setPadding(8, 8, 8, 8)
                            layoutParams =
                                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                        }
                        val nameTextView = TextView(context).apply {
                            text = name
                            setTextColor(ContextCompat.getColor(context, R.color.white))
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.forest_blue_700
                                )
                            )
                            setPadding(8, 8, 8, 8)
                            layoutParams =
                                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                        }

                        val surnameTextView = TextView(context).apply {
                            text = surname
                            setTextColor(ContextCompat.getColor(context, R.color.white))
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.forest_blue_700
                                )
                            )
                            setPadding(8, 8, 8, 8)
                            layoutParams =
                                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                        }

                        val updatedTimeTextView = TextView(context).apply {
                            text = time
                            setTextColor(ContextCompat.getColor(context, R.color.white))
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.forest_blue_700
                                )
                            )
                            setPadding(8, 8, 8, 8)
                            layoutParams =
                                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                        }

                        tableRow.addView(dniTextView)
                        tableRow.addView(nameTextView)
                        tableRow.addView(surnameTextView)
                        tableRow.addView(updatedTimeTextView)
                    }
                }
            }
        })
    }
}