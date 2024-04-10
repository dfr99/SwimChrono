package es.udc.apm.swimchrono.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val viewModel: TournamentInfoViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTournaments()

        val infoRecyclerView: RecyclerView = binding.tournamentInfoRecyclerView
        infoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        viewModel.tournaments.observe(viewLifecycleOwner, Observer { tournaments ->

            val tournamentName = arguments?.getString(ARG_TOURNAMENT_NAME)

            val todayTournamentAdapter = RecyclerTournamentAdapter(
                tournaments.filter { tournament -> tournament.name == tournamentName },
                null
            )
            infoRecyclerView.adapter = todayTournamentAdapter

        })

        // Retrieve tournament name from arguments

        val races = arrayOf(
            arrayOf("1", "17:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("2", "18:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("3", "19:00 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
            arrayOf("4", "19:30 (aprox.)", "Freestyle", "4x25 (200 mts.)"),
        )

        val raceAdapter = RecyclerRaceAdapter(races)

        val raceRecyclerView: RecyclerView = binding.tournamentRacesRecyclerView
        raceRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        raceRecyclerView.adapter = raceAdapter
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTournamentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}