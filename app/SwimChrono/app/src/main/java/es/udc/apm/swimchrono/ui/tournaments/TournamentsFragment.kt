package es.udc.apm.swimchrono.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentTournamentsBinding
import es.udc.apm.swimchrono.ui.dashboard.RecyclerTournamentAdapter

class TournamentsFragment : Fragment() {

    private var _binding: FragmentTournamentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tournamentsViewModel =
            ViewModelProvider(this).get(TournamentsViewModel::class.java)

        _binding = FragmentTournamentsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val tournament = arrayOf(
            arrayOf("Local Tournament", "Tournament 1", "5 May 2024", "100", "Lugo")
        )

        val races = arrayOf(
            arrayOf("1", "17:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("2", "18:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("3", "19:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("4", "19:30 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
        )

        val tournamentAdapter = RecyclerTournamentAdapter(tournament)

        val tournamentRecyclerView: RecyclerView =
            root.findViewById(R.id.tournament_info_recycler_view)

        tournamentRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        tournamentRecyclerView.adapter = tournamentAdapter


        val raceAdapter = RecyclerRaceAdapter(races)

        val raceRecyclerView: RecyclerView =
            root.findViewById(R.id.tournament_races_list)


        raceRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        raceRecyclerView.adapter = raceAdapter

        val textView: TextView = binding.racesTittle
        tournamentsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}