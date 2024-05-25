package es.udc.apm.swimchrono.ui.referee

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentRefereeBinding
import es.udc.apm.swimchrono.model.Race
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RefereeFragment : Fragment() {

    private var _binding: FragmentRefereeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_referee, container, false)

        val beginChrono: Button = view.findViewById(R.id.beginButton)

        beginChrono.setOnClickListener {
            val intent = Intent(activity, TimerActivity::class.java)
            startActivity(intent)

            Log.d("BeginChronoClicked", "Go to chrono")
        }

        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentHourString = dateFormat.format(Date())

        val currentHour = dateFormat.parse(currentHourString)
        val races = arrayOf(
            Race(
                id = 1,
                style = "Espalda",
                category = "Masculino",
                distance = "100",
                heat = 6,
                lane = 3,
                hour = currentHour,
                times = emptyMap() // Puedes inicializar con los tiempos si los tienes disponibles
            )
        )


        val racesList = RecyclerRacesListAdapter(races)
        val clubMemberRecyclerView: RecyclerView = view.findViewById(R.id.races)

        clubMemberRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        clubMemberRecyclerView.adapter = racesList

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}