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
import java.util.Date

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


        val races = arrayOf(
            Race(
                id = 1,
                swimmer = "David",
                club = "SAL",
                race = "100 medley",
                heat = 6,
                lane = 3,
                hour = Date()
            ),
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