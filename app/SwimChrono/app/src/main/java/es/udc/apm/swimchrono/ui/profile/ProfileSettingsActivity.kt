package es.udc.apm.swimchrono.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import es.udc.apm.swimchrono.R


class ProfileSettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)

        buttonExit.setOnClickListener {
            Toast.makeText(this, "SALIR", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}