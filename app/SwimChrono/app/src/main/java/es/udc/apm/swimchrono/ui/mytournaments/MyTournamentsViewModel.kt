package es.udc.apm.swimchrono.ui.mytournaments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.udc.apm.swimchrono.model.Tournament
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import es.udc.apm.swimchrono.util.Logger
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Locale

class MyTournamentsViewModel : ViewModel(), ApiServiceCallback {

    private val tag = this.javaClass.name

    private val _tournaments = MutableLiveData<List<Tournament>>()
    val tournaments: LiveData<List<Tournament>> = _tournaments

    private val apiService = ApiService()

    fun getTournaments() {
        apiService.getTournaments(this)
    }

    override fun onTournamentsReceived(response: String) {
        val tournamentsList = parseResponse(response)
        _tournaments.postValue(tournamentsList)
        Logger.debug(tag, "Response from ApiService: $tournamentsList")
    }


    private fun parseResponse(response: String): List<Tournament> {
        val tournamentList = mutableListOf<Tournament>()

        val jsonArray = JSONArray(response)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("ID")
            val type = jsonObject.getString("TIPO")
            val name = jsonObject.getString("NOMBRE")
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            val date = dateFormat.parse(jsonObject.getString("FECHA"))
            val participants = jsonObject.getInt("NÚMERO PARTICIPANTES")
            val location = jsonObject.getString("LUGAR")
            val races = emptyList<String>() //FIXME: Crear un objeto Race

            val tournament = Tournament(id, type, name, date, participants, location, races)
            tournamentList.add(tournament)
        }

        return tournamentList
    }

}
