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

    private val _trainers = MutableLiveData<List<HashMap<String, String>>>()
    val trainers: LiveData<List<HashMap<String, String>>> = _trainers

    private val _members = MutableLiveData<List<HashMap<String, String>>>()
    val members: LiveData<List<HashMap<String, String>>> = _members

    private var apiService = ApiService()

    init {
        // Inicializamos el ApiService en el init del ViewModel
        apiService.onCreate()
    }

    fun getClub(uid: String) {
        apiService.isUserMember(uid, this)
    }

    fun getUsers(cid: Int, userType: String) {
        apiService.getUsers(cid, userType, this)
    }

    override fun onDataReceived(response: Any?) {
        when (response) {
            is HashMap<*, *> -> {
                val clubData = parseClub(response)
                _club.postValue(clubData)
                Logger.debug(
                    tag, "On ClubViewModel.onDataReceived(response)," +
                            "after invocation of parseResponse(): $clubData"
                )
            }

            is List<*> -> {
                val usersData = parseUsers(response)
                if (usersData[0]["rol"] == "trainer") {
                    _trainers.postValue(usersData)
                } else if (usersData[0]["rol"] == "swimmer") {
                    _members.postValue(usersData)
                }
                Logger.debug(
                    tag, "On ClubViewModel.onDataReceived(response)," +
                            "after invocation of parseResponse(): $usersData"
                )
            }

            else -> {
                Logger.error(tag, "Cannot parse response: $response")
            }
        }
    }

    private fun parseUsers(response: Any?): List<HashMap<String, String>> {
        val users = mutableListOf<HashMap<String, String>>()

        if (response is List<*>) {
            response.forEach { user ->
                if (user is HashMap<*, *>) {
                    users.add(
                        hashMapOf(
                            "nombre" to (user["nombre"] as? String ?: ""),
                            "apellido" to (user["apellido"] as? String ?: ""),
                            "email" to (user["email"] as? String ?: ""),
                            "rol" to (user["rol"] as? String ?: "")
                        )
                    )
                } else {
                    Logger.error(
                        tag,
                        "Response does not contains HashMap<*,*> items"
                    )
                }
            }
        }
        return users
    }

    private fun parseClub(response: Any?): Club {
        val res: HashMap<*, *>? = response as? HashMap<*, *>
        val trainersList: MutableList<String> = mutableListOf()
        val membersList: MutableList<String> = mutableListOf()

        val id = (res?.get("id") as? Long)?.toInt() ?: 0
        val name = res?.get("name") as? String ?: ""
        val city = res?.get("city") as? String ?: ""
        val address = res?.get("address") as? String ?: ""
        val phone = res?.get("phone") as? String ?: ""
        val url = res?.get("url") as? String ?: ""
        val membersNumber = (res?.get("members_number") as? Long)?.toInt() ?: 0

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

        val clubData =
            Club(id, name, city, address, phone, url, trainersList, membersList, membersNumber)
        return clubData
    }
}