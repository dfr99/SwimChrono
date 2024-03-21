package es.udc.apm.swimchrono.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    // LiveData privado para almacenar el texto que se mostrara en el fragmento
    private val _text = MutableLiveData<String>().apply {
        value = "Profile Fragment" // Valor inicial del texto
    }

    // LiveData publico para que el fragmento observe los cambios en el texto
    val text: LiveData<String> = _text
}