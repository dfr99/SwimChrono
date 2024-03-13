package es.udc.apm.swimchrono.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.udc.apm.swimchrono.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    // Declaración de la variable de enlace para acceder a las vistas del diseño del fragmento
    private var _binding: FragmentProfileBinding? = null

    // Utilizando el patrón de propiedades para acceder al binding
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Crear una instancia del ViewModel asociado a este fragmento
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Inflar el diseño del fragmento y asignar la vista raíz a la variable root
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Acceder al TextView del diseño del fragmento
        val textView: TextView = binding.textProfile

        // Observar los cambios en el LiveData dentro del ViewModel y actualizar el TextView
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        // Devolver la vista raíz
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPerosnalInfo.setOnClickListener {
            Toast.makeText(requireContext(), "INFO", Toast.LENGTH_SHORT).show()
            val intentPersonalInfo = Intent(requireContext(), ProfileInfoActivity::class.java)
            startActivity(intentPersonalInfo)
        }
        binding.buttonMyQr.setOnClickListener {
            Toast.makeText(requireContext(), "My QR", Toast.LENGTH_SHORT).show()
            val intentMyQR = Intent(requireContext(), ProfileMyQRActivity::class.java)
            startActivity(intentMyQR)
        }
        binding.buttonNotifications.setOnClickListener {
            Toast.makeText(requireContext(), "Notifications", Toast.LENGTH_SHORT).show()
            val intentNotifications = Intent(requireContext(), ProfileNotificationsActivity::class.java)
            startActivity(intentNotifications)
        }
        binding.buttonProfileSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Profile Settings", Toast.LENGTH_SHORT).show()
            val intentSettings = Intent(requireContext(), ProfileSettingsActivity::class.java)
            startActivity(intentSettings)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Limpiar la referencia al enlace para evitar fugas de memoria
        _binding = null
    }


}