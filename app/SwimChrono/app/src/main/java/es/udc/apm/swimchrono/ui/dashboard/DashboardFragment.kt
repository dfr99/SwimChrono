package es.udc.apm.swimchrono.ui.dashboard

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
import es.udc.apm.swimchrono.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val todayTournaments = arrayOf(
            arrayOf("Local Tournament", "Tournament 1", "5 May 2024", "100", "Lugo"),
            arrayOf("Regional Tournament", "Tournament 2", "5 May 2024", "150", "Coruña"),
            arrayOf("National Tournament", "Tournament 3", "5 May 2024", "200", "Madrid"),
            arrayOf("National Tournament", "Tournament 4", "5 May 2024", "200", "Madrid")
        )


        val todayTournamentAdapter = TodayTournamentAdapter(todayTournaments)

        val todayRecyclerView: RecyclerView =
            root.findViewById(R.id.tournaments_today_recycler_list)
        todayRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        todayRecyclerView.adapter = todayTournamentAdapter

        val incomingTournaments = arrayOf(
            arrayOf("Local Tournament", "Tournament A", "7 May 2024", "100", "Lugo"),
            arrayOf("Regional Tournament", "Tournament B", "10 May 2024", "150", "Coruña"),
            arrayOf("National Tournament", "Tournament C", "15 May 2024", "200", "Madrid"),
            arrayOf("Local Tournament", "Tournament D", "20 May 2024", "100", "Lugo"),
            arrayOf("Regional Tournament", "Tournament E", "20 May 2024", "150", "Coruña"),
            arrayOf("National Tournament", "Tournament F", "25 May 2024", "200", "Madrid")
        )

        val incomingTournamentAdapter = IncomingTournamentAdapter(incomingTournaments)

        val incomingRecyclerView: RecyclerView =
            root.findViewById(R.id.tournaments_incoming_recycler_list)
        incomingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        incomingRecyclerView.adapter = incomingTournamentAdapter

        val textView: TextView = binding.dashboardTournamentsToday
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}