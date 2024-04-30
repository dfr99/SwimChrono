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

        Logger.debug(tag, "Reached observer")
        // Observa el LiveData en el hilo principal
        loginViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            // Se ejecutará cuando el LiveData tenga un valor
            // Aquí puedes realizar las acciones que necesites con el usuario
            Logger.debug(tag, "Usuario actualizado: $user")
            // Por ejemplo, puedes navegar a otra pantalla, mostrar un diálogo, etc.
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

                            navigateToProfile()
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
        navController.navigate(R.id.action_profile_to_profile_fragment)

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