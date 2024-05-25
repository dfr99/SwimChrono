package es.udc.apm.swimchrono.ui.referee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentRefereeBinding
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.ui.club.RefereeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RefereeFragment : Fragment() {

    private val viewModel: RefereeViewModel by viewModels()

    private var selectedRaceId: Int? = null

    private lateinit var apiService: ApiService

    private var _binding: FragmentRefereeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_referee, container, false)

        apiService = ApiService()
        apiService.onCreate()

        viewModel.getTournaments()


        val expandableListView = view.findViewById<ExpandableListView>(R.id.expandableListView)

        viewModel.tournaments.observe(viewLifecycleOwner) { tournaments ->
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)


            // Filtrar los torneos para el día de hoy y los torneos futuros
            val todayTournaments =
                tournaments.filter { dateFormat.format(it.date!!) == dateFormat.format(currentDate) }

            val adapter = context?.let {
                TournamentExpandableListAdapter(it, todayTournaments) { raceId ->
                    selectedRaceId = raceId // Store the selected race ID
                }
            }
            expandableListView.setAdapter(adapter)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}