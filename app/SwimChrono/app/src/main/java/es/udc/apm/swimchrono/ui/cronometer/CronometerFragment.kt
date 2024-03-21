package es.udc.apm.swimchrono.ui.cronometer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import es.udc.apm.swimchrono.R

class CronometerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cronometer_start, container, false)

        val beginButton: Button = view.findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            Log.d("CronometerBeginButton", "Botón de inicio pulsado")
        }

        val stopButton: Button = view.findViewById(R.id.stopButton)
        stopButton.setOnClickListener {
            Log.d("CronometerStopButton", "Pulsado botón de parada")
        }

        return view
    }


}