package es.udc.apm.swimchrono

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.udc.apm.swimchrono.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, _ ->
            val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getString("userId", null) != ""

            if (!isLoggedIn) {
                val mBottomNavigationView = findViewById<View>(R.id.navigation_club)

                mBottomNavigationView.visibility = View.GONE
            }
        }


    }
}