package es.udc.apm.swimchrono.ui.referee

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.util.Logger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit


class TimerActivity : AppCompatActivity() {

    private var isRunning = false

    //private lateinit var chronometer: Chronometer
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var startTime: Long = 0
    private var timeElapsed: Long = 0

    private var tournamentId = -1;
    private var raceId = -1;

    private lateinit var database: DatabaseReference

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_referee_start)

        tournamentId = intent.getIntExtra("TOURNAMENT_ID", -1)
        raceId = intent.getIntExtra("RACE_ID", -1)

        if (tournamentId != -1 && raceId != -1) {
            Logger.debug("TimerActivity", "Received Tournament ID: $tournamentId, Race ID: $raceId")
            // Use the tournament ID and race ID as needed
        } else {
            Logger.debug("TimerActivity", "No Tournament ID or Race ID received")
        }

        database = FirebaseDatabase.getInstance().reference


        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)
        val chronometer = findViewById<TextView>(R.id.chronometer)
        val startStopButton = findViewById<Button>(R.id.startStopButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val saveButton = findViewById<Button>(R.id.saveButton)


        buttonExit.setOnClickListener {
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show()
            finish()
        }

        startStopButton.setOnClickListener {
            if (!isRunning) {
                Toast.makeText(this, "Start Chrono", Toast.LENGTH_SHORT).show()
            } else if (isRunning) {
                Toast.makeText(this, "Stop Chrono", Toast.LENGTH_SHORT).show()
            }
            toggleStartStop(chronometer, startStopButton)
        }

        resetButton.setOnClickListener {

            val positiveButtonClick: (DialogInterface, Int) -> Unit =
                { dialogInterface: DialogInterface, i: Int ->
                    stopChronometer()
                    timeElapsed = 0
                    startTime = System.currentTimeMillis()
                    chronometer.text = "00:00:000" // Actualiza el texto del cronómetro a cero

                    // Cambiamos el estado del boton
                    isRunning = false
                    startStopButton.text = getString(R.string.start)
                    startStopButton.setBackgroundColor(
                        Color.argb(
                            255,
                            9,
                            135,
                            151
                        )
                    ) // @color/chrono_play
                    startStopButton.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_play,
                        0,
                        0,
                        0
                    )

                }

            val negativeButtonClick = { dialog: DialogInterface, which: Int ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.cancel, Toast.LENGTH_SHORT
                ).show()
            }

            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle("Save Alert")
                setMessage("Want to reset the timer?")
                setPositiveButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener(function = positiveButtonClick)
                )
                setNegativeButton(android.R.string.cancel, negativeButtonClick)
                show()
            }

        }

        saveButton.setOnClickListener {

            val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)

            val swimmerUID = sharedPreferences.getString("swimmerUID", null);
            Toast.makeText(this, swimmerUID, Toast.LENGTH_SHORT).show()

            stopChronometer()
            startStopButton.text = getString(R.string.start)
            startStopButton.setBackgroundColor(
                Color.argb(
                    255,
                    9,
                    135,
                    151
                )
            ) // @color/chrono_play
            startStopButton.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.icon_play,
                0,
                0,
                0
            )


            val builder = AlertDialog.Builder(this)

            val positiveButtonClick: (DialogInterface, Int) -> Unit =
                { dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed)
                            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60
                            val millis = timeElapsed % 1000

                            val result = String.format("%02d:%02d:%03d", minutes, seconds, millis)
                            addTimeToFirebase(swimmerUID!!, result)

                        } catch (_: Exception) {
                        }
                    }
                }

            val negativeButtonClick = { dialog: DialogInterface, which: Int ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.cancel, Toast.LENGTH_SHORT
                ).show()
            }

            with(builder)
            {
                setTitle("Save Alert")
                setMessage("Want to save the current time?")
                setPositiveButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener(function = positiveButtonClick)
                )
                setNegativeButton(android.R.string.cancel, negativeButtonClick)
                show()
            }

        }

        initQRScanner()
    }

    private suspend fun addTimeToFirebase(uid: String, newTime: String) {
        val tiemposRef =
            database.child("tournaments").child(tournamentId.toString()).child("carreras").child(
                raceId.toString()
            ).child("tiempos")
                .child(uid)

        val snapshot = tiemposRef.get().await()
        if (snapshot.exists()) {
            tiemposRef.setValue(newTime).await()
        } else {
            tiemposRef.setValue(newTime).await()
        }
    }


    private fun toggleStartStop(chronometer: TextView, startStopButton: Button) {

        if (isRunning) {
            stopChronometer()
            startStopButton.text = getString(R.string.start)
            startStopButton.setBackgroundColor(
                Color.argb(
                    255,
                    9,
                    135,
                    151
                )
            ) // @color/chrono_play
            startStopButton.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.icon_play,
                0,
                0,
                0
            )
        } else {
            startTime = System.currentTimeMillis() - timeElapsed
            startChronometer(chronometer)
            startStopButton.text = getString(R.string.stop)
            startStopButton.setBackgroundColor(
                Color.argb(
                    255,
                    246,
                    117,
                    117
                )
            ) // @color/chrono_red
            startStopButton.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.icon_pause,
                0,
                0,
                0
            )
        }
        isRunning = !isRunning

    }


    private fun startChronometer(chronometer: TextView) {
        if (!isRunning) {
            // Inicializar el Handler y el Runnable solo si aún no se han inicializado
            if (!::handler.isInitialized) {
                handler = Handler()
            }
            if (!::runnable.isInitialized) {
                runnable = object : Runnable {
                    override fun run() {
                        val currentTime = System.currentTimeMillis()
                        timeElapsed = currentTime - startTime
                        val hms = String.format(
                            //"%02d:%02d:%02d", // Horas:Minutos:Segundos
                            "%02d:%02d", // Minutos:Segundos
                            //TimeUnit.MILLISECONDS.toHours(timeElapsed),
                            TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % TimeUnit.HOURS.toMinutes(
                                1
                            ),
                            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % TimeUnit.MINUTES.toSeconds(
                                1
                            )
                        )
                        val ms = String.format("%03d", timeElapsed % 1000)
                        chronometer.text = "$hms:$ms"
                        handler.postDelayed(this, 1) // Actualizar cada milisegundo
                    }
                }
            }

            //chronometer.base = SystemClock.elapsedRealtime()
            //chronometer.start()
            // Iniciar el Handler
            handler.post(runnable)
        }
    }

    private fun stopChronometer() {
        if (isRunning) {
            //chronometer.stop()
            // Detener el Handler
            handler.removeCallbacks(runnable)
        }
    }


    // Scan QR functions
    private fun initQRScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Establecer el tipo de codigo de barras
        integrator.setPrompt("Acerque la cámara al código QR")
        integrator.setTorchEnabled(false) // Encender flash
        integrator.setBeepEnabled(false) // Sonido de confirmacion de codigo escaneado
        integrator.initiateScan()
    }

    fun extractUID(input: String): String? {
        // Definimos el prefijo esperado
        val prefix = "UID: "

        // Verificamos si el input comienza con el prefijo
        return if (input.startsWith(prefix)) {
            // Extraemos el UID usando substring
            input.substring(prefix.length).trim { it <= ' ' }
        } else {
            null
        }
    }

    //Este método se le llama cada vez que vuelve de un activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null) {
            Toast.makeText(this, "Exception?", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
            finish()
        }
        var content = result.contents
        if (content == null) {
            content = ""
        }
        // Check valid QR
        val uid = extractUID(content)

        if (uid != null) {
            val sharedPreferences =
                getSharedPreferences("user_data", Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.putString("swimmerUID", uid)
            editor.apply()

        } else {
            Toast.makeText(this, "Please scan a valid QR", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}