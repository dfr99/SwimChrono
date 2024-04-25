package es.udc.apm.swimchrono.ui.referee

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import es.udc.apm.swimchrono.R

class TimerActivity : AppCompatActivity() {

    private var isRunning = false
    private lateinit var lapTimesText: TextView
    private var lapNumber = 1

    /** Eliminar esta variable si el scan se hace en otro sitio**/
    // Variable para poder activar el crono una vez escaneado
    private var enable_chronno = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_referee_start)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)
        val chronometer = findViewById<Chronometer>(R.id.chronometer)
        val startStopButton = findViewById<Button>(R.id.startStopButton)

        /** Eliminar cuando se arregle el Scan en el fragment**/
        val auxScanButton = findViewById<Button>(R.id.botonScanQRaux)

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

        /** Eliminar cuando se arregle el Scan en el fragment o se escanee en otro sitio**/
        auxScanButton.setOnClickListener {
            initQRScanner()
        }


    }

    private fun toggleStartStop(chronometer: Chronometer, startStopButton: Button) {

        if (enable_chronno) {
            /** Eliminar este if si el scan se hace en otro sitio**/
            if (isRunning) {
                stopChronometer(chronometer)
                startStopButton.text = getString(R.string.start)
                startStopButton.setBackgroundColor(Color.argb(255, 9, 135, 151))
            } else {
                startChronometer(chronometer)
                startStopButton.text = getString(R.string.stop)
                startStopButton.setBackgroundColor(Color.argb(255, 255, 0, 0))
            }

            isRunning = !isRunning
        } else {
            Toast.makeText(this, "Escanea el QR del competidor", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startChronometer(chronometer: Chronometer) {
        if (enable_chronno) { /** Eliminar este if si el scan se hace en otro sitio**/
            if (!isRunning) {
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.start()
            }
        }
    }

    private fun stopChronometer(chronometer: Chronometer) {
        if (isRunning) {
            chronometer.stop()
        }
    }



    /** Eliminar cuando se arregle el Scan en el fragment**/
    // Scan QR functions
    private fun initQRScanner() {
        //IntentIntegrator(this).initiateScan() //DEPRECATED

        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Establecer el tipo de codigo de barras
        integrator.setPrompt("Acerque la cámara al código QR")
        integrator.setTorchEnabled(false) // Encender flash
        integrator.setBeepEnabled(true) // Sonido de confirmacion de codigo escaneado
        integrator.initiateScan()
    }

    //Este método se le llama cada vez que vuelve de un activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents == null){
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }else{
                //Toast.makeText(this, "El valor escaneado es: ${result.contents}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Puede iniciar el crono", Toast.LENGTH_SHORT).show()
                enable_chronno = true
            }
        }else{
            Toast.makeText(this, "Exception?", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}