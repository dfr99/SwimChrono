package es.udc.apm.swimchrono.ui.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.udc.apm.swimchrono.model.Club
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import es.udc.apm.swimchrono.util.Logger

class ClubViewModel : ViewModel(), ApiServiceCallback {

    private val tag = this.javaClass.name

    private val _club = MutableLiveData<Club>()
    val club: LiveData<Club> = _club

    private val apiService = ApiService()

    init {
        // Inicializamos el ApiService en el init del ViewModel
        apiService.onCreate()
    }

    fun getClub(uid: String) {
        apiService.isUserMember(uid, this)
    }

    override fun onDataReceived(response: Any?) {
        val clubData = parseResponse(response)
        _club.postValue(clubData)
        Logger.debug(tag, "Response from ApiService: $clubData")
    }

    private fun parseResponse(response: Any?): Club {
        var id: Int = -1
        var name = ""
        var city = ""
        var address = ""
        var phone = ""
        var url = ""
        var trainersList: List<String> = listOf()
        var membersList: List<String> = listOf()
        var membersNumber: Int = -1

        if (response is ArrayList<*>) {
            for (item in response) {
                if (item is HashMap<*, *>) {
                    id = (item["id"] as? Long)?.toInt() ?: -1
                    name = item["name"] as? String ?: ""
                    city = item["city"] as? String ?: ""
                    address = item["address"] as? String ?: ""
                    phone = item["phone"] as? String ?: ""
                    url = item["url"] as? String ?: ""
                    trainersList =
                        (item["trainers"] as? List<*>)?.mapNotNull { it as? String } ?: listOf()
                    membersList =
                        (item["miembros"] as? List<*>)?.mapNotNull { it as? String } ?: listOf()

                    membersNumber = membersList.size
                }
            }
        }
        val clubData =
            Club(id, name, city, address, phone, url, trainersList, membersList, membersNumber)
        return clubData
    }
}