package es.udc.apm.swimchrono.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentLoginBinding
import es.udc.apm.swimchrono.model.LoginResult
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var tag = this.javaClass.name

    private var _binding: FragmentLoginBinding? = null


    private val binding get() = _binding!!
    private lateinit var apiService: ApiService

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        apiService = ApiService()
        apiService.onCreate()
        sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Observa el LiveData en el hilo principal
        loginViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            Logger.debug(tag, "Usuario actualizado: $user")
            navigateToProfile()
        }

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userId = sharedPreferences.getString("userId", null)
        if (userId != null && userId != "") {
            Toast.makeText(
                requireContext(),
                "Successfully logged with cache",
                Toast.LENGTH_SHORT
            ).show()
            navigateToProfile()
        }



        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                val loginResult = apiService.login(email, password)
                withContext(Dispatchers.Main) {
                    when (loginResult) {
                        is LoginResult.Success -> {
                            val obtainedUserId: String = loginResult.userId
                            loginViewModel.getUserData(obtainedUserId)
                            Logger.debug(tag, "Successfully logged. UID: $obtainedUserId")
                            Toast.makeText(
                                requireContext(),
                                "Successfully logged",
                                Toast.LENGTH_SHORT
                            ).show()


                            saveUserIdToSharedPreferences(obtainedUserId)

                        }

                        LoginResult.InvalidEmail -> {
                            showError("Invalid email format")
                        }

                        LoginResult.InvalidPassword -> {
                            showError("Invalid password")
                        }

                        LoginResult.NetworkError -> {
                            showError("Network error occurred")
                        }

                        LoginResult.UnknownError -> {
                            showError("Please insert a valid email")
                        }
                    }
                }
            }
        }


        return root
    }

    private fun navigateToProfile() {
        val navController = findNavController()
        updateBottomNavigationBar()
        navController.navigate(R.id.action_profile_to_profile_fragment)

    }

    private fun updateBottomNavigationBar() {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userRole = sharedPreferences.getString("rol", null)

        val clubBottomNavigationView = requireActivity().findViewById<View>(R.id.navigation_club)
        val refereeBottomNavigationView =
            requireActivity().findViewById<View>(R.id.navigation_referee)

        // Mostrar u ocultar elementos de la barra de navegación según el rol del usuario
        when (userRole) {
            "swimmer" -> {
                clubBottomNavigationView.visibility = View.VISIBLE
                refereeBottomNavigationView.visibility = View.GONE
            }

            "referee" -> {
                clubBottomNavigationView.visibility = View.GONE
                refereeBottomNavigationView.visibility = View.VISIBLE
            }

            else -> {
                clubBottomNavigationView.visibility = View.GONE
                refereeBottomNavigationView.visibility = View.GONE
            }
        }
        clubBottomNavigationView.invalidate()
        refereeBottomNavigationView.invalidate()
    }

    private fun showError(errorMessage: String) {
        binding.errorTextView.text = errorMessage
        binding.errorTextView.visibility = View.VISIBLE
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun saveUserIdToSharedPreferences(userId: String) {
        Logger.debug(tag, "saveUserIdToShared")


        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.apply()

        Logger.info(tag, "Updated shared preferences: ${sharedPreferences.all}")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.setFragmentContext(requireContext())

    }
}