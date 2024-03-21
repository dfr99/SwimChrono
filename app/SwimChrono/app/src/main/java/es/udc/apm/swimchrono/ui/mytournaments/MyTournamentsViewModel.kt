package es.udc.apm.swimchrono.ui.mytournaments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyTournamentsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    // Expose LiveData as immutable LiveData to external classes
    val text: LiveData<String> = _text

    // Function to update the text value

}
