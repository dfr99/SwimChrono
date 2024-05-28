package es.udc.apm.swimchrono.ui.profile

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import es.udc.apm.swimchrono.BaseActivity
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.services.ApiServiceCallback

class ProfileInfoActivity : BaseActivity() {

    private var apiService = ApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)


        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)


        val nameUser = findViewById<TextView>(R.id.tvName)
        val surnameUser = findViewById<TextView>(R.id.tvSurname)
        val emailUser = findViewById<TextView>(R.id.tvEmail)
        val dniUser = findViewById<TextView>(R.id.tvDNI)
        val birthdayUser = findViewById<TextView>(R.id.tvBirthday)
        val telephoneNumberUser = findViewById<TextView>(R.id.tvNumber)
        val rolUser = findViewById<TextView>(R.id.tvRol)


        apiService = ApiService()
        apiService.onCreate()
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("userId", null)


        // Obtener los datos del usuario y actualizar la fila de la tabla
        apiService.getUserData(uid!!, object : ApiServiceCallback {
            override fun onDataReceived(userData: Any?) {
                (userData as? Map<String, Any>)?.let {
                    val name = it["nombre"] as? String ?: "Unknown"
                    val surname = it["apellido"] as? String ?: "Unknown"
                    val dni = it["DNI"] as? String ?: "Unknown"
                    val email = it["email"] as? String ?: "Unknown"
                    val birthday = it["fecha_nacimiento"] as? String ?: "Unknown"
                    val telephoneNumber = it["numero_telefono"] as? String ?: "Unknown"
                    val rol = it["rol"] as? String ?: "Unknown"
                    // Actualizar la fila de la tabla con los datos del usuario

                    nameUser.text = name
                    surnameUser.text = surname
                    emailUser.text = email
                    dniUser.text = dni
                    birthdayUser.text = birthday
                    telephoneNumberUser.text = telephoneNumber
                    rolUser.text = rol
                }
            }
        })


        buttonExit.setOnClickListener {
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}