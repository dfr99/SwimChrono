package es.udc.apm.swimchrono.ui.tournaments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.udc.apm.swimchrono.model.Tournament
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import es.udc.apm.swimchrono.util.Logger
import java.text.SimpleDateFormat
import java.util.Locale

class TournamentInfoViewModel : ViewModel(), ApiServiceCallback {

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

    override fun onTournamentsReceived(response: Any?) {
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

                    val tournament =
                        Tournament(id, type, name, date, participants, location, emptyList())
                    tournamentList.add(tournament)
                }
            }
        }

        return tournamentList
    }
}

