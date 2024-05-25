package es.udc.apm.swimchrono.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import es.udc.apm.swimchrono.R

class ProfileInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)



        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)



        val nameUser = findViewById<TextView>(R.id.tvName)
        val surnameUser = findViewById<TextView>(R.id.tvSurname)
        val telephoneNumber = findViewById<TextView>(R.id.tvNumber)
        val rolUser = findViewById<TextView>(R.id.tvRol)
        val nameClub = findViewById<TextView>(R.id.tvClub)



        nameUser.setText("1")
        surnameUser.setText("2")
        telephoneNumber.setText("3")
        rolUser.setText("4")
        nameClub.setText("5")










        buttonExit.setOnClickListener {
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}