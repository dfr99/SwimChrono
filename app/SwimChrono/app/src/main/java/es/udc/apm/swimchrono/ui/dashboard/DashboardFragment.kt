package es.udc.apm.swimchrono.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentDashboardBinding
import es.udc.apm.swimchrono.notifications.AlarmNotificationManager
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.ui.tournaments.TournamentInfoFragment
import es.udc.apm.swimchrono.util.Logger
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DashboardFragment : Fragment(), OnTournamentItemClickListener {

    private var tag = this.javaClass.name

    private val viewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null

    private lateinit var apiService: ApiService

    private val binding get() = _binding!!


    override fun onItemClick(tournamentId: Int) {
        Logger.debug(
            tag,
            "Performed click with args: [tournamentId = \"$tournamentId\"]"
        )

        // Crear una instancia del fragmento de información del torneo
        val tournamentInfoFragment = TournamentInfoFragment.newInstance(tournamentId)

        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, tournamentInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = ApiService()
        apiService.onCreate()

        viewModel.getTournaments()

        val todayRecyclerView: RecyclerView = binding.tournamentsTodayRecyclerList
        todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val incomingRecyclerView: RecyclerView = binding.tournamentsIncomingRecyclerList
        incomingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.tournaments.observe(viewLifecycleOwner) { tournaments ->


            val currentDate = Date()
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)


            // Filtrar los torneos para el día de hoy y los torneos futuros
            val todayTournaments =
                tournaments.filter { dateFormat.format(it.date!!) == dateFormat.format(currentDate) }
            val incomingTournaments =
                tournaments.filter { it.date!!.after(currentDate) }


            val todayTournamentAdapter = RecyclerTournamentAdapter(todayTournaments, this)
            todayRecyclerView.adapter = todayTournamentAdapter

            val incomingTournamentAdapter = RecyclerTournamentAdapter(incomingTournaments, this)
            incomingRecyclerView.adapter = incomingTournamentAdapter

            tournaments.forEach { tournament ->
                val dateComponents = viewModel.extractDateTimeComponents(tournament.date!!)
                createNotification(
                    requireContext(),
                    dateComponents[0],
                    dateComponents[1],
                    dateComponents[2],
                    dateComponents[3],
                    dateComponents[4],
                    dateComponents[5]
                )
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createNotification(
        context: Context,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int,
    ) {
        val alarmNotificationManager = AlarmNotificationManager(context)

        // Crear Calendar para la hora original
        val calendar = Calendar.getInstance().apply {
            set(year, month - 1, day, hour, minute, second) // Mes se basa en 0
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)

        // Crear notificación en la hora exacta recibida
        alarmNotificationManager.createNotification(year, month, day, hour, minute, second)
        Logger.debug(this.javaClass.name, "Notification created for: $formattedDate")

        // Crear Calendar para una hora antes
        val oneHourBefore = (calendar.clone() as Calendar).apply {
            add(Calendar.HOUR_OF_DAY, -1)
        }
        val formattedDateOneHourBefore = dateFormat.format(oneHourBefore.time)
        alarmNotificationManager.createNotification(
            oneHourBefore.get(Calendar.YEAR),
            oneHourBefore.get(Calendar.MONTH) + 1,
            oneHourBefore.get(Calendar.DAY_OF_MONTH),
            oneHourBefore.get(Calendar.HOUR_OF_DAY),
            oneHourBefore.get(Calendar.MINUTE),
            oneHourBefore.get(Calendar.SECOND)
        )
        Logger.debug(this.javaClass.name, "Notification created for: $formattedDateOneHourBefore")

        // Crear Calendar para un día antes
        val oneDayBefore = (calendar.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }
        val formattedDateOneDayBefore = dateFormat.format(oneDayBefore.time)
        alarmNotificationManager.createNotification(
            oneDayBefore.get(Calendar.YEAR),
            oneDayBefore.get(Calendar.MONTH) + 1,
            oneDayBefore.get(Calendar.DAY_OF_MONTH),
            oneDayBefore.get(Calendar.HOUR_OF_DAY),
            oneDayBefore.get(Calendar.MINUTE),
            oneDayBefore.get(Calendar.SECOND)
        )
        Logger.debug(this.javaClass.name, "Notification created for: $formattedDateOneDayBefore")

    }
}