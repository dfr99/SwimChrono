package es.udc.apm.swimchrono.ui.club

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.services.ApiService
import es.udc.apm.swimchrono.databinding.FragmentClubBinding
import es.udc.apm.swimchrono.ui.login.LoginViewModel


class ClubFragment : Fragment() {
    private var tag = this.javaClass.name

    private lateinit var apiService: ApiService

    private val clubViewModel: ClubViewModel by viewModels()
    private val userViewModel: LoginViewModel by viewModels()

    private lateinit var sharedPreferences: SharedPreferences

    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        }

        val clubMemberRecyclerView: RecyclerView = binding.clubMemberList
        clubMemberRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val trainersRecyclerView : RecyclerView = binding.trainer
        trainersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        clubViewModel.club.observe(viewLifecycleOwner) { club ->

            val clubAdapter = ClubDataAdapter(club)
            clubMemberRecyclerView.adapter = clubAdapter

            val clubMembers = mutableListOf<Array<String>>()
            for (member in club.members) {
                userViewModel.getUserData(member)
                userViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
                    clubMembers.add(arrayOf(user.name, user.surname, user.email))
                }
            }

            val clubMemberAdapter = RecyclerClubMembersListAdapter(clubMembers)
            clubMemberRecyclerView.adapter = clubMemberAdapter

            val trainers = mutableListOf<Array<String>>()
            for (trainer in club.trainers) {
                userViewModel.getUserData(trainer)
                userViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
                    trainers.add(arrayOf(user.name, user.surname, user.email))
                }
            }
            val trainersAdapter = RecyclerItemTrainerAdapter(trainers)
            trainersRecyclerView.adapter = trainersAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}