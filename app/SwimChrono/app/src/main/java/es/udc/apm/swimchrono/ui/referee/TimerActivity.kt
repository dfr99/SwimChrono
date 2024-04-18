package es.udc.apm.swimchrono.ui.referee

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import es.udc.apm.swimchrono.R

class TimerActivity : AppCompatActivity() {

    private var isRunning = false
    private lateinit var lapTimesText: TextView
    private var lapNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_referee_start)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)
        val chronometer = findViewById<Chronometer>(R.id.chronometer)
        val startStopButton = findViewById<Button>(R.id.startStopButton)

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
    }

    private fun toggleStartStop(chronometer: Chronometer, startStopButton: Button) {
        if (isRunning) {
            stopChronometer(chronometer)
            startStopButton.text = getString(R.string.start)
            startStopButton.setBackgroundColor(Color.argb(255,9,135,151))
        } else {
            startChronometer(chronometer)
            startStopButton.text = getString(R.string.stop)
            startStopButton.setBackgroundColor(Color.argb(255,255,0,0))
        }

        isRunning = !isRunning
    }

    private fun startChronometer(chronometer: Chronometer) {
        if (!isRunning) {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
        }
    }

    private fun stopChronometer(chronometer: Chronometer) {
        if (isRunning) {
            chronometer.stop()
        }
    }
}