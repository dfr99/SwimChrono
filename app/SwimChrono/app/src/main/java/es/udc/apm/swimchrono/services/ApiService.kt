package es.udc.apm.swimchrono.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import es.udc.apm.swimchrono.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

interface ApiServiceCallback {
    fun onTournamentsReceived(response: String)
}


class ApiService : Service() {

    private val tag = this.javaClass.name

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun getTournaments(callback: ApiServiceCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://swimcrhono-api-55mh.onrender.com/tournaments")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }
                    inputStream.close()
                    callback.onTournamentsReceived(response)
                    Logger.debug(tag, "Response: $response")
                } else {
                    Logger.error(tag, "Error en la respuesta: $responseCode")
                }
            } catch (e: Exception) {
                Logger.error(tag, "Error al realizar la solicitud GET $e")
            }
        }
    }

}
