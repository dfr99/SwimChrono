package es.udc.apm.swimchrono.ui.tournaments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TournamentInfoViewModel : ViewModel() {
    
    private val _text = MutableLiveData<String>()

    val text: LiveData<String> = _text
}