package es.udc.apm.swimchrono.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import es.udc.apm.swimchrono.R

class ProfileMyQRActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences // Declarar la variable SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_myqractivity)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)
        val ivCodigoQR: ImageView = findViewById(R.id.ivCodigoQR)

        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)


        val userId = "UID: " + sharedPreferences.getString("userId", null)

        // Generación del QR
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                userId,
                BarcodeFormat.QR_CODE, /*Creo que el tamaño no importa*/
                300,
                300
            )

            ivCodigoQR.setImageBitmap(bitmap)


        } catch (e: Exception) {
            e.printStackTrace()
        }


        //Botón de salir
        buttonExit.setOnClickListener {
            finish()
        }


    }


}