package es.udc.apm.swimchrono.ui.dashboard

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
import es.udc.apm.swimchrono.ui.tournaments.TournamentInfoFragment
import es.udc.apm.swimchrono.util.Logger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardFragment : Fragment(), OnTournamentItemClickListener {

    private var tag = this.javaClass.name

    private val viewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!


    override fun onItemClick(tournamentName: String) {
        Logger.debug(
            tag,
            "Performed click with args: [tournamentName = \"$tournamentName\"]"
        )

        // Crear una instancia del fragmento de información del torneo
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

        viewModel.tournaments.observe(viewLifecycleOwner, Observer { tournaments ->

            // Obtener la fecha actual en el formato "dd MMM yyyy"
            val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date())

            // Filtrar los torneos para el día de hoy y los torneos futuros
            val todayTournaments = tournaments.filter { it.date == currentDate }
            val incomingTournaments = tournaments.filter { it.date > currentDate }

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