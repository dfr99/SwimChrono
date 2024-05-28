package es.udc.apm.swimchrono.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.udc.apm.swimchrono.model.Race
import es.udc.apm.swimchrono.model.Tournament
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import es.udc.apm.swimchrono.util.Logger
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DashboardViewModel : ViewModel(), ApiServiceCallback {

    private val tag = this.javaClass.name

    private val _tournaments = MutableLiveData<List<Tournament>>()
    val tournaments: LiveData<List<Tournament>> = _tournaments

    private val apiService = ApiService()

    init {
        // Inicializamos el ApiService en el init del ViewModel
        apiService.onCreate()
    }

    fun getTournaments() {
        apiService.getTournaments(this)
    }

    override fun onDataReceived(response: Any?) {
        val tournamentsList = parseResponse(response)
        _tournaments.postValue(tournamentsList)
        Logger.debug(tag, "Response from ApiService: $tournamentsList")
    }


    private fun parseResponse(response: Any?): List<Tournament> {
        val tournamentList = mutableListOf<Tournament>()

        if (response is ArrayList<*>) {
            for (item in response) {
                if (item is HashMap<*, *>) {
                    val id = (item["id"] as? Long)?.toInt() ?: 0
                    val type = item["tipo"] as? String ?: ""
                    val name = item["nombre"] as? String ?: ""
                    val location = item["lugar"] as? String ?: ""
                    val participants = (item["num_participantes"] as? Long)?.toInt() ?: 0
                    val dateString = item["fecha"] as? String ?: ""

                    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
                    val date = dateFormat.parse(dateString)

                    val races = mutableListOf<Race>()
                    val racesData = item["carreras"] as? ArrayList<*>
                    racesData?.let {
                        for (raceItem in it) {
                            if (raceItem is HashMap<*, *>) {
                                val raceId = (raceItem["id"] as? Long)?.toInt() ?: 0
                                val style = raceItem["estilo"] as? String ?: ""
                                val category = raceItem["categoria"] as? String ?: ""
                                val distance = raceItem["distancia"] as? String ?: ""
                                val heat = (raceItem["heat"] as? Long)?.toInt() ?: 0
                                val lane = (raceItem["lane"] as? Long)?.toInt() ?: 0
                                val hourString = raceItem["hour"] as? String ?: "00:00:00"
                                val hourFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
                                val hour = hourFormat.parse(hourString)
                                val times =
                                    raceItem["tiempos"] as? Map<String, String> ?: emptyMap()

                                val race =
                                    Race(raceId, style, category, distance, heat, lane, hour, times)
                                races.add(race)
                            }
                        }
                    }

                    val tournament =
                        Tournament(id, type, name, date, participants, location, emptyList())


                    tournamentList.add(tournament)
                }
            }
        }

        return tournamentList
    }


    // Nueva función para extraer componentes de fecha y hora
    fun extractDateTimeComponents(date: Date): List<Int> {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return listOf(year, month, day, hour, minute, second)
    }
}

