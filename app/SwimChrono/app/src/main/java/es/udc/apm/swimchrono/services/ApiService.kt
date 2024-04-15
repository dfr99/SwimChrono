package es.udc.apm.swimchrono.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.udc.apm.swimchrono.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface ApiServiceCallback {
    fun onTournamentsReceived(response: Any?)
}

class ApiService : Service() {

    private val tag = this.javaClass.name
    private lateinit var database: DatabaseReference

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        // Obtener referencia a la instancia de Firebase Database
        database = FirebaseDatabase.getInstance().reference

    }

    fun getTournaments(callback: ApiServiceCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Obtener referencia al nodo "tournaments" en Firebase Database
                val tournamentsRef = database.child("tournaments")

                tournamentsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // Convertir el snapshot a JSON
                        val response = snapshot.value
                        // Enviar los datos al callback
                        callback.onTournamentsReceived(response)
                        Logger.debug(tag, "Response: $response")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Logger.error(tag, "Error al obtener datos de Firebase: ${error.message}")
                    }
                })
            } catch (e: Exception) {
                Logger.error(tag, "Error $e")
            }
        }
    }
}