package es.udc.apm.swimchrono.ui.login

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        apiService = ApiService()
        apiService.onCreate()

        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //FIXME: Es necesario recordar si el usuario se ha logueado correctamente.

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                val loginResult = apiService.login(email, password)
                withContext(Dispatchers.Main) {
                    when (loginResult) {
                        is ApiService.LoginResult.Success -> {
                            val userId = loginResult.userId
                            Logger.debug(tag, "userID: $userId")
                            Toast.makeText(
                                requireContext(),
                                "Successfully logged",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateToProfile()
                        }

                        ApiService.LoginResult.InvalidEmail -> {
                            showError("Invalid email format")
                        }

                        ApiService.LoginResult.InvalidPassword -> {
                            showError("Invalid password")
                        }

                        ApiService.LoginResult.NetworkError -> {
                            showError("Network error occurred")
                        }

                        ApiService.LoginResult.UnknownError -> {
                            showError("Please insert a valid email")
                        }
                    }
                }
            }
        }
        return root
    }

    private fun navigateToProfile() {
        // Navegar al fragmento de perfil
        val navController = findNavController()
        navController.navigate(R.id.action_profile_to_profile_fragment)
    }

    private fun showError(errorMessage: String) {
        binding.errorTextView.text = errorMessage
        binding.errorTextView.visibility = View.VISIBLE
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

}