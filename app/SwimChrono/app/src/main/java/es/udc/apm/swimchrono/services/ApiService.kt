package es.udc.apm.swimchrono.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import es.udc.apm.swimchrono.model.LoginResult
import es.udc.apm.swimchrono.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException

interface ApiServiceCallback {
    fun onDataReceived(response: Any?)
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
                        callback.onDataReceived(response)
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


    fun getUserData(uid: String, callback: ApiServiceCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tournamentsRef = database.child("users")

                tournamentsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { userSnapshot ->
                            // Obtener el UID del usuario actual
                            val userUid = userSnapshot.child("UID").getValue(String::class.java)
                            // Verificar si el UID coincide con el UID buscado
                            if (userUid == uid) {
                                // Obtener los datos del usuario y enviar al callback
                                val userData = userSnapshot.value
                                Logger.debug(tag, "Called callback with : $userData")
                                callback.onDataReceived(userData)
                                return // Salir del bucle forEach después de encontrar el usuario
                            }
                        }
                        // Si no se encontró ningún usuario con el UID especificado
                        Logger.error(tag, "No se encontró ningún usuario con el UID: $uid")
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


    private val auth = FirebaseAuth.getInstance()


    suspend fun login(email: String, password: String): LoginResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            LoginResult.Success(result.user?.uid ?: "")
        } catch (e: FirebaseAuthInvalidUserException) {
            LoginResult.InvalidEmail
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            LoginResult.InvalidPassword
        } catch (e: Exception) {
            if (e is IOException) {
                LoginResult.NetworkError
            } else {
                LoginResult.UnknownError
            }
        }
    }

}