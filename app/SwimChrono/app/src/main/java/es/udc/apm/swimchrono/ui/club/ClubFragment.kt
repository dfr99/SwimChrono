package es.udc.apm.swimchrono.ui.club

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentClubBinding
import es.udc.apm.swimchrono.databinding.ItemClubCardBinding
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.util.Logger


class ClubFragment : Fragment() {
    private var tag = this.javaClass.name

    private lateinit var apiService: ApiService

    private val clubViewModel: ClubViewModel by viewModels()

    private lateinit var sharedPreferences: SharedPreferences

    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentClubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = ApiService()
        apiService.onCreate()
        sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString("userId", null)
        if (userId != null) {
            clubViewModel.getClub(userId)
            Logger.info(tag, "From ClubFragment: Get club data")
        }

        val clubInfo: ItemClubCardBinding = binding.itemClubCard

        val trainerTextView: TextView = binding.textTrainer
        val trainersRecyclerView: RecyclerView = binding.trainers
        trainersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val clubMemberTextView: TextView = binding.textMembers
        val membersRecyclerView: RecyclerView = binding.members
        membersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        clubViewModel.club.observe(viewLifecycleOwner) { club ->

            clubInfo.nombreClub.text = club.name
            clubInfo.direccion.text = getString(R.string.address).plus(" ").plus(club.address)
            clubInfo.paginaWeb.text = getString(R.string.url).plus(" ").plus(club.url)
            clubInfo.telefono.text = getString(R.string.phone).plus(" ").plus(club.phone)
            clubInfo.membersNumber.text =
                getString(R.string.members).plus(" ").plus(club.membersNumber.toString())

            trainerTextView.text =
                getString(R.string.trainer).plus(" (").plus(club.trainers.size).plus(")")
            clubMemberTextView.text =
                getString(R.string.members).plus(" (").plus(club.members.size).plus(")")

            clubViewModel.getUsers(club.id, "trainers")
            clubViewModel.trainers.observe(viewLifecycleOwner) { trainers ->
                val trainersAdapter = RecyclerUsersListAdapter(trainers)
                trainersRecyclerView.adapter = trainersAdapter
            }

            clubViewModel.getUsers(club.id, "members")
            clubViewModel.members.observe(viewLifecycleOwner) { members ->
                val clubMembersAdapter = RecyclerUsersListAdapter(members)
                membersRecyclerView.adapter = clubMembersAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}