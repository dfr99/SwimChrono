package es.udc.apm.swimchrono.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import es.udc.apm.swimchrono.model.LoginResult
import es.udc.apm.swimchrono.model.User
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
                                Logger.debug(tag, "On ApiService.getUserData(): $userData")
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


    fun getClub(id: Int?, callback: ApiServiceCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val clubsRef = database.child("clubs")

                clubsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { clubSnapshot ->
                            // Obtener el ID del club
                            val clubId = clubSnapshot.child("id").getValue(Int::class.java)
                            // Verificar si el id del club coincide con el club buscado
                            if (clubId == id) {
                                // Obtener los datos del club y enviar al callback
                                val clubData = clubSnapshot.value
                                Logger.debug(tag, "On ApiService.getClub(): Called callback with : $clubData")
                                callback.onDataReceived(clubData)
                                return // Salir del bucle forEach después de encontrar el club
                            }
                        }
                        // Si no se encontró ningún usuario con el UID especificado
                        Logger.error(tag, "No se encontró ningún club con el ID: $id")
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


    fun isUserMember(uid: String, callback: ApiServiceCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val clubsRef = database.child("clubs")
                clubsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { clubSnapshot ->
                            val genericType = object :
                                GenericTypeIndicator<List<HashMap<String, String>>>() {}

                            clubSnapshot.child("members").getValue(genericType)?.forEach { user ->
                                if (user.containsValue(uid)) {
                                    val clubId = clubSnapshot.child("id").getValue(Int::class.java)
                                    Logger.debug(tag,
                                        "Encontrado el usuario ".plus(uid).plus(" en el club ").plus(clubId).plus(" como deportista"))
                                    getClub(clubId, callback)
                                    return
                                }
                            }

                            clubSnapshot.child("trainers").getValue(genericType)?.forEach { trainer ->
                                if (trainer.containsValue(uid)) {
                                    val clubId = clubSnapshot.child("id").getValue(Int::class.java)
                                    Logger.debug(tag,
                                        "Encontrado el usuario ".plus(uid).plus(" en el club ").plus(clubId).plus(" como entrenador"))
                                    getClub(clubId, callback)
                                    return
                                }
                            }
                        }

                        Logger.error(tag, "El usuario".plus(uid).plus("no pertenece a ningún club"))
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

    fun getUsers(id: Int?, userType: String, callback: ApiServiceCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val clubsRef = database.child("clubs")
                val trainersRef = database.child("users")
                val users = mutableListOf<Any?>()
                val genericType = object : GenericTypeIndicator<List<HashMap<String, String>>>() {}

                clubsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { clubSnapshot ->
                            // Obtener el ID del club
                            val clubId = clubSnapshot.child("id").getValue(Int::class.java)
                            // Verificar si el id del club coincide con el club buscado
                            if (clubId == id) {
                                if (userType == "trainers") {
                                    clubSnapshot.child("trainers").getValue(genericType)
                                        ?.forEach { user ->
                                            trainersRef.addListenerForSingleValueEvent(object :
                                                ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    snapshot.children.forEach { userSnapshot ->
                                                        val userId = userSnapshot.child("UID")
                                                            .getValue(String::class.java)
                                                        if (userId == user["UID"]) {
                                                            // Obtener los datos del usuario y añadirlos a la lista
                                                            val userData = userSnapshot.value
                                                            users.add(userData)
                                                            Logger.debug(
                                                                tag,
                                                                "On ApiService.getUsers(trainers): Called callback with : $userData"
                                                            )
                                                        }
                                                    }
                                                    callback.onDataReceived(users)
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    Logger.error(
                                                        tag,
                                                        "Error al obtener datos de Firebase: ${error.message}"
                                                    )
                                                }
                                            })
                                        }
                                } else if (userType == "members") {
                                    clubSnapshot.child("members").getValue(genericType)
                                        ?.forEach { user ->
                                            trainersRef.addListenerForSingleValueEvent(object :
                                                ValueEventListener {
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    snapshot.children.forEach { userSnapshot ->
                                                        val userId = userSnapshot.child("UID")
                                                            .getValue(String::class.java)
                                                        if (userId == user["UID"]) {
                                                            // Obtener los datos del usuario y añadirlos a la lista
                                                            val userData = userSnapshot.value
                                                            users.add(userData)
                                                            Logger.debug(
                                                                tag,
                                                                "On ApiService.getUsers(members): Called callback with : $userData"
                                                            )
                                                        }
                                                    }
                                                    callback.onDataReceived(users)
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    Logger.error(
                                                        tag,
                                                        "Error al obtener datos de Firebase: ${error.message}"
                                                    )
                                                }
                                            })
                                        }
                                }
                            }
                        }
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