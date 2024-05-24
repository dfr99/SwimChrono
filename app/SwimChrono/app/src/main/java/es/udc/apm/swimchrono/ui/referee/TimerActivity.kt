package es.udc.apm.swimchrono.ui.referee

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import es.udc.apm.swimchrono.R
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {

    private var isRunning = false
    private lateinit var lapTimesText: TextView
    private var lapNumber = 1

    /** Eliminar esta variable si el scan se hace en otro sitio**/
    // Variable para poder activar el crono una vez escaneado
    private var enable_chronno = false

    //private lateinit var chronometer: Chronometer
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var startTime: Long = 0
    private var timeElapsed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_referee_start)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)
        val chronometer = findViewById<TextView>(R.id.chronometer)
        val startStopButton = findViewById<Button>(R.id.startStopButton)
        val resetButton = findViewById<Button>(R.id.resetButton)


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
            stopChronometer()
            timeElapsed = 0
            startTime = System.currentTimeMillis()
            chronometer.text = "00:00:000" // Actualiza el texto del cronómetro a cero

            // Cambiamos el estado del boton
            isRunning = false
            startStopButton.text = getString(R.string.start)
            startStopButton.setBackgroundColor(Color.argb(255, 9, 135, 151)) // @color/chrono_play
            startStopButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_play, 0, 0, 0)

        }

        initQRScanner()
    }

    private fun toggleStartStop(chronometer: TextView, startStopButton: Button) {

        if (enable_chronno) {
            if (isRunning) {
                stopChronometer()
                startStopButton.text = getString(R.string.start)
                startStopButton.setBackgroundColor(Color.argb(255, 9, 135, 151)) // @color/chrono_play
                startStopButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_play, 0, 0, 0)
            } else {
                startTime = System.currentTimeMillis() - timeElapsed
                startChronometer(chronometer)
                startStopButton.text = getString(R.string.stop)
                startStopButton.setBackgroundColor(Color.argb(255, 246, 117, 117)) // @color/chrono_red
                startStopButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_pause, 0, 0, 0)
            }

            isRunning = !isRunning
        } else {
            Toast.makeText(this, "Escanea el QR del competidor", Toast.LENGTH_SHORT).show()
        }
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

    //Este método se le llama cada vez que vuelve de un activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result == null) {
            Toast.makeText(this, "Exception?", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
            finish() /**No ha habido ningun caso que se llegase hasta aqui**/
        }

        if (result.contents == null) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            finish()
        }

        enable_chronno = true
    }
}