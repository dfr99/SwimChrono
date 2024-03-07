package es.udc.apm.swimchrono.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    // Expose LiveData as immutable LiveData to external classes
    val text: LiveData<String> = _text

    // Function to update the text value

}
