package es.udc.apm.swimchrono

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.udc.apm.swimchrono.databinding.ActivityMainBinding
import es.udc.apm.swimchrono.util.LocaleUtil
import es.udc.apm.swimchrono.util.Logger
import es.udc.apm.swimchrono.util.Storage

class MainActivity : BaseActivity() {
    private var tag: String = this.javaClass.name

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    public override val storage: Storage by lazy {
        Storage(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleUtil.getLocalizedContext(
                newBase,
                Storage(newBase).getPreferredLocale()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, _ ->
            updateBottomNavigationVisibility()
        }

    }


    private fun updateBottomNavigationVisibility() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getString("userId", null) != ""
        val userRole = sharedPreferences.getString("rol", null)

        val clubBottomNavigationView = findViewById<View>(R.id.navigation_club)
        val refereeBottomNavigationView = findViewById<View>(R.id.navigation_referee)
        val myTournamentsBottomNavigationView = findViewById<View>(R.id.navigation_tournaments)


        Logger.debug(tag, "Updated visibility")
        if (isLoggedIn) {
            when (userRole) {
                "admin" -> {
                    clubBottomNavigationView.visibility = View.VISIBLE
                    myTournamentsBottomNavigationView.visibility = View.VISIBLE
                    refereeBottomNavigationView.visibility = View.VISIBLE
                }

                "swimmer", "trainer" -> {
                    clubBottomNavigationView.visibility = View.VISIBLE
                    myTournamentsBottomNavigationView.visibility = View.VISIBLE
                    refereeBottomNavigationView.visibility = View.GONE
                }

                "referee" -> {
                    clubBottomNavigationView.visibility = View.GONE
                    refereeBottomNavigationView.visibility = View.VISIBLE
                    myTournamentsBottomNavigationView.visibility = View.GONE
                }

                else -> {
                    clubBottomNavigationView.visibility = View.GONE
                    refereeBottomNavigationView.visibility = View.GONE
                    myTournamentsBottomNavigationView.visibility = View.GONE
                }
            }
        } else {
            clubBottomNavigationView.visibility = View.GONE
            refereeBottomNavigationView.visibility = View.GONE
            myTournamentsBottomNavigationView.visibility = View.GONE
        }

        clubBottomNavigationView.requestLayout()
        refereeBottomNavigationView.requestLayout()
    }
}
