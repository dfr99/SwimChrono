package es.udc.apm.swimchrono.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // Declaración de la variable de enlace para acceder a las vistas del diseño del fragmento
    private var _binding: FragmentLoginBinding? = null

    // Utilizando el patrón de propiedades para acceder al binding
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //FIXME: Change this DefaultLogin behaviour
        val isLogged = true;
        if (isLogged) {
            Toast.makeText(requireContext(), "Successfully logged", Toast.LENGTH_SHORT).show()
            navigateToProfile()
        }

        // Agregar un OnClickListener al botón de inicio de sesión
        binding.loginButton.setOnClickListener {
            navigateToProfile()
        }
        return root
    }

    private fun navigateToProfile() {
        // Navegar al fragmento de perfil
        val navController = findNavController()
        navController.navigate(R.id.action_profile_to_profile_fragment)
    }


}