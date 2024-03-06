package es.udc.apm.swimchrono.ui.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClubViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is club Fragment"
    }
    val text: LiveData<String> = _text
}