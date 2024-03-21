package es.udc.apm.swimchrono.ui.club

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
import es.udc.apm.swimchrono.databinding.FragmentClubBinding

class ClubFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val clubViewModel =
            ViewModelProvider(this).get(ClubViewModel::class.java)

        _binding = FragmentClubBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val clubMembers = arrayOf<String>(
            "Si",
            "No",
            "Eso espero",
            "Non sei quen participa aquí"
        )

        val memberList = RecyclerClubMembersListAdapter(clubMembers)
        val clubMemberRecyclerView: RecyclerView = root.findViewById(R.id.club_member_list)

        clubMemberRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        clubMemberRecyclerView.adapter = memberList

         val textView: TextView = binding.textClubInfo
         clubViewModel.text.observe(viewLifecycleOwner) {
             textView.text = it
         }

        val clubTrainer = arrayOf<String>(
            "Jaime López Rego",
            "David Fraga Rodríguez",
        )

        val trainerInfo = RecyclerItemTrainerAdapter(clubTrainer)
        val trainerInfoRecyclerView : RecyclerView = root.findViewById(R.id.trainer)

        trainerInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        trainerInfoRecyclerView.adapter = trainerInfo

        val clubData = arrayOf<String>(
             "Club Fluvial Lugo",
             "Rúa Fermín Rivera, s/n, 27004 Lugo",
             "+34 673 45 34 54",
             "100",
             "https://www.clubfluviallugo.com"
        )

        // val ClubInfo = ClubDataAdapter(clubData)
        // val ClubInfoRecyclerView :  = root.findViewById(R.id.item_club_card)

        // ClubInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // ClubInfoRecyclerView.adapter = trainerInfo

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}