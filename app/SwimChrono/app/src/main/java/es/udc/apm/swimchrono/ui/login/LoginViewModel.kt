package es.udc.apm.swimchrono.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.udc.apm.swimchrono.model.User
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback
import es.udc.apm.swimchrono.util.Logger

class LoginViewModel : ViewModel(), ApiServiceCallback {

    private val tag = this.javaClass.name
    private val apiService = ApiService()


    private val _userMutable = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userMutable

    private lateinit var fragmentContext: Context

    fun setFragmentContext(context: Context) {
        fragmentContext = context
    }

    init {
        apiService.onCreate()
    }

    fun getUserData(uid: String) {
        apiService.getUserData(uid, this)
    }

    override fun onDataReceived(response: Any?) {
        Logger.debug(tag, "onDataReceived called. Response: $response")
        val user = parseResponse(response)
        _userMutable.postValue(user!!)

        val sharedPreferences =
            fragmentContext.getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("rol", user.role)
        editor.apply()

        Logger.info(tag, "Updated shared preferences: ${sharedPreferences.all}")
    }

    private fun parseResponse(response: Any?): User {
        var user = User()
        if (response is Map<*, *>) {
            user = User(
                response["UID"] as? String ?: "",
                response["nombre"] as? String ?: "",
                response["apellido"] as? String ?: "",
                response["numero_telefono"] as? String ?: "",
                response["fecha_nacimiento"] as? String ?: "",
                response["rol"] as? String ?: "",
                response["DNI"] as? String ?: "",
                response["email"] as? String ?: "",
            )
        }
        return user
    }


}
