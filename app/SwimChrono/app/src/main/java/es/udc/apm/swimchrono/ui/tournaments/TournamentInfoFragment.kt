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
import es.udc.apm.swimchrono.databinding.FragmentTournamentInfoBinding
import es.udc.apm.swimchrono.ui.dashboard.RecyclerTournamentAdapter

class TournamentInfoFragment : Fragment() {

    companion object {
        private const val ARG_TOURNAMENT_NAME = "tournamentName"

        fun newInstance(tournamentName: String): TournamentInfoFragment {
            val fragment = TournamentInfoFragment()
            val args = Bundle().apply {
                putString(ARG_TOURNAMENT_NAME, tournamentName)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentTournamentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tournamentInfoViewModel =
            ViewModelProvider(this).get(TournamentInfoViewModel::class.java)

        _binding = FragmentTournamentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Retrieve tournament name from arguments
        val tournamentName = arguments?.getString(ARG_TOURNAMENT_NAME)

        val tournamentData = arrayOf(
            arrayOf("1", "Local Tournament", "Tournament 1", "5 May 2024", "100", "Lugo"),
            arrayOf("2", "Regional Tournament", "Tournament 2", "5 May 2024", "150", "Coruña"),
            arrayOf("3", "National Tournament", "Tournament 3", "5 May 2024", "200", "Madrid"),
            arrayOf("4", "National Tournament", "Tournament 4", "5 May 2024", "200", "Madrid"),
            arrayOf("11", "Local Tournament", "Tournament A", "7 May 2024", "100", "Lugo"),
            arrayOf("22", "Regional Tournament", "Tournament B", "10 May 2024", "150", "Coruña"),
            arrayOf("33", "National Tournament", "Tournament C", "15 May 2024", "200", "Madrid"),
            arrayOf("44", "Local Tournament", "Tournament D", "20 May 2024", "100", "Lugo"),
            arrayOf("55", "Regional Tournament", "Tournament E", "20 May 2024", "150", "Coruña"),
            arrayOf("66", "National Tournament", "Tournament F", "25 May 2024", "200", "Madrid")
        )


        val races = arrayOf(
            arrayOf("1", "17:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("2", "18:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("3", "19:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("4", "19:30 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
        )

        // FIXME: This should search with the ID not the name
        // Search for the tournament with the specified name
        val tournament =
            arrayOf(tournamentData.find { it.getOrNull(2) == tournamentName } ?: emptyArray())


        val tournamentAdapter = RecyclerTournamentAdapter(tournament, null)

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
        tournamentInfoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}