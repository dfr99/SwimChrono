package es.udc.apm.swimchrono.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.databinding.FragmentTournamentInfoBinding
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.ui.dashboard.RecyclerTournamentAdapter

class TournamentInfoFragment : Fragment() {

    private lateinit var apiService: ApiService

    companion object {
        private const val ARG_TOURNAMENT_ID = "tournamentId"

        fun newInstance(tournamentId: Int): TournamentInfoFragment {
            val fragment = TournamentInfoFragment()
            val args = Bundle().apply {
                putInt(ARG_TOURNAMENT_ID, tournamentId)
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

        apiService = ApiService()
        apiService.onCreate()

        viewModel.getTournaments()

        val infoRecyclerView: RecyclerView = binding.tournamentInfoRecyclerView
        infoRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.tournaments.observe(viewLifecycleOwner) { tournaments ->

            val tournamentId = arguments?.getInt(ARG_TOURNAMENT_ID)
            val filteredTournaments =
                tournaments.filter { tournament -> tournament.id == tournamentId }

            val todayTournamentAdapter = RecyclerTournamentAdapter(
                filteredTournaments, null
            )
            infoRecyclerView.adapter = todayTournamentAdapter
            val raceAdapter = RecyclerRaceAdapter(filteredTournaments[0].races)

            val raceRecyclerView: RecyclerView = binding.tournamentRacesRecyclerView
            raceRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            raceRecyclerView.adapter = raceAdapter
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTournamentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}