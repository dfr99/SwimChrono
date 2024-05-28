# Elección de los componentes

## Actividades

<!--  Intro -->

En nuestra aplicación _SwimChrono_ se han implementado las siguientes actividades por donde el usuario podrá navegar.

<!--  Actividad principal -->

### MainActivity

En el MainActivity se ha decidido implementar una pantalla principal en nuestra aplicación para que el usuario pueda ver los fragmentos que se explicarán en el siguiente apartado. Esta actividad contiene en la parte inferior una barra de navegación (_navigation_bar_) donde poder cambiar de fragment a otro sin salir del menú principal.

<!-- Cada unha das opcións de perfil -->
### Actividades Profile

Estas son las actividades utilizadas para configurar los datos del usuario:

- Pantalla de _Información personal_
- Pantalla de _Mi QR_
- Pantalla de _Configuración_
- Pantalla de _Cronómetro_

## Fragmentos

En la aplicación _SwimChrono_ se han decidido implementar las pantallas
principales de la aplicación como fragmentos, siendo cada pantalla un fragmento:

- Pantalla de _Próximos Torneos_
- Pantalla de _Mis Torneos_
- Pantalla de _Mi Club_
- Pantalla de _Mi Perfil_

Estos fragmentos son utilizados por la actividad principal, que dispone de un
_navController_ para gestionar la navegación entre y la disposición de las
pantallas en el menú principal de la aplicación.

![Fragmentos usados en la configuración de la navegación](../images/components/fragments.png)

El equipo ha optado por esta solución por los siguientes motivos:

- El uso de fragmentos favorece la **reutilización** y **modularidad** de los
recursos.
- Con la utilización de fragmentos, es posible comunicarse con la actividad
anfitriona sin necesidad de crear componentes adicionales, como los _Intents_.
Además, estos fragmentos, al estar contenidos dentro de la misma actividad, se
podrían llegar a comunicar entre ellos.
- La gestión de los estados de los fragmentos es más precisa y controlada en
comparación con la gestión de los estados de las actividades

Esta solución también presenta inconvenientes, que se detallan a continuación:

- La gestión de los estados, a pesar de ser más precisa que con el uso de
actividades, se vuelve más compleja al tener que ser manejada explícitamente
por los desarrolladores, con el uso de la función [_addToBackStack()_][backstack].
- El ciclo de vida de los fragmentos depende de la actividad anfitriona, mientras
que las actividades son independientes y tienen sus propio ciclo de vida.

## Servicios

La aplicación obtendrá datos de Firebase Realtime Database, aquí es donde tenemos todos los datos relativos a torneos, carreras, nadadores, entrenadores y tiempos. También se utiliza FirebaseAuth que permite gestión de cuentas.

### Implementación del Servicio

Se implementará un servicio llamado APIService, que nos permitirá comunicarnos con Firebase y todas sus funcionalidades. Una vez que el servicio ApiService reciba los datos de nadadores, los procesará según sea necesario.

Este enfoque permite separar las operaciones de red de la lógica de la interfaz de usuario, lo que hace que el código sea más modular y fácil de mantener.

Se han implementado las siguientes funcionalidades:

- Autenticación: Conexión con FireBase Auth
- ObtenerUsuarios
- ObtenerTorneos
- IsUserMember: Saber si un usuario pertenece a un Club.
- ObtenerClubs

A continuación se muestra la primera implementación del servicio dn la aplicación. Se muestra la llamada getTournaments() que es la encargada de obtener la lista de todos los torneos. También se puede ver la implementación del login de la aplicación.

```kotlin
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
```

## Corutinas

En primera instancia hemos intentado utiliza el cronómetro que nos proporciona de forma nativa Android, pero para nuestro caso de uso requeríamos una precisión de milisegundos.

Al no servirnos hemos optado por hacer nosotros mismos la gestión del cronómetro mediante una corrutina y un handler. Esto nos permite obtener una precisión de milisegundos y poder bloquear el móvil o incluso coger llamadas con el cronómetro corriendo en segundo plano.

También se han utilizado estas corrutinas junto con callbacks para la gestión de llamadas a la Api de servicios. Esto nos permite que la aplicación no se quede esperando respuesta y no de una sensación pobre de navegación al usuario.

<!-- Variables -->
[backstack]: https://developer.android.com/reference/androidx/fragment/app/FragmentTransaction#addToBackStack(java.lang.String)
