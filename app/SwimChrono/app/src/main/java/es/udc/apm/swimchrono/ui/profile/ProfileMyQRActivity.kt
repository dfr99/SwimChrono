package es.udc.apm.swimchrono.ui.profile

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.udc.apm.swimchrono.R

class ProfileMyQRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_myqractivity)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)

        buttonExit.setOnClickListener {
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}