package es.udc.apm.swimchrono.ui.mytournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentDashboardBinding
import es.udc.apm.swimchrono.ui.dashboard.RecyclerTournamentAdapter
import es.udc.apm.swimchrono.ui.tournaments.TournamentInfoFragment
import es.udc.apm.swimchrono.util.Logger

class MyTournamentsFragment : Fragment(),
    es.udc.apm.swimchrono.ui.dashboard.OnTournamentItemClickListener {

    private var _binding: FragmentDashboardBinding? = null

    private val viewModel: MyTournamentsViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onItemClick(tournamentName: String) {
        Logger.debug(
            this.javaClass.name,
            "Performed click with args: [tournamentName = \"$tournamentName\"]"
        )
        // Replace the fragment with tournament info fragment
        val tournamentInfoFragment = TournamentInfoFragment.newInstance(tournamentName)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, tournamentInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTournaments()

        val todayRecyclerView: RecyclerView = binding.tournamentsTodayRecyclerList
        todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val incomingRecyclerView: RecyclerView = binding.tournamentsIncomingRecyclerList
        incomingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //FIXME: Make the date work
        viewModel.tournaments.observe(viewLifecycleOwner, Observer { tournaments ->
            val todayTournaments = tournaments.filter { it.date == "05 May 2024" }
            val incomingTournaments = tournaments.filter { it.date != "05 May 2024" }

            val todayTournamentAdapter = RecyclerTournamentAdapter(todayTournaments, this)
            todayRecyclerView.adapter = todayTournamentAdapter

            val incomingTournamentAdapter = RecyclerTournamentAdapter(incomingTournaments, this)
            incomingRecyclerView.adapter = incomingTournamentAdapter
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}