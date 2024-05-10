package es.udc.apm.swimchrono.ui.club

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.local.IndexManager.IndexType
import es.udc.apm.swimchrono.model.Club
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import es.udc.apm.swimchrono.util.Logger

class ClubViewModel : ViewModel(), ApiServiceCallback {

    private val tag = this.javaClass.name

    private val _club = MutableLiveData<Club>()
    val club: LiveData<Club> = _club

    private var apiService = ApiService()

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
        Logger.debug(tag, "On ClubViewModel.onDataReceived(response)," +
                "after invocation of parseResponse(): $clubData")
    }

    private fun parseResponse(response: Any?): Club {
        val res: HashMap<*, *>? = response as? HashMap<*, *>
        val trainersList: MutableList<String> = mutableListOf()
        val membersList: MutableList<String> = mutableListOf()

        val id = (res?.get("id") as? Long)?.toInt() ?: 0
        val name = res?.get("name") as? String ?: ""
        val city = res?.get("city") as? String ?: ""
        val address = res?.get("address") as? String ?: ""
        val phone = res?.get("phone") as? String ?: ""
        val url = res?.get("url") as? String ?: ""
        val membersNumber = (res?.get("members_number") as? Long)?.toInt()  ?: 0

        val trainers = res?.get("trainers") as? List<*> ?: listOf<String>()
        trainers.forEach { trainer ->
            val tmp = trainer as? HashMap<*, *>
            trainersList.add(tmp?.get("UID").toString())
        }

        val members = res?.get("members") as? List<*> ?: listOf<String>()
        members.forEach { member ->
            val tmp = member as? HashMap<*, *>
            membersList.add(tmp?.get("UID").toString())
        }

        val clubData = Club(id, name, city, address, phone, url, trainersList, membersList, membersNumber)
        return clubData
    }
}