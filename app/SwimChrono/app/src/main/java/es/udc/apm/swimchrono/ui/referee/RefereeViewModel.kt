package es.udc.apm.swimchrono.ui.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefereeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    val text: LiveData<String> = _text
}