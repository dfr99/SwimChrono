package es.udc.apm.swimchrono.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import es.udc.apm.swimchrono.databinding.FragmentClubBinding
import es.udc.apm.swimchrono.ui.dashboard.RecyclerTournamentAdapter
import es.udc.apm.swimchrono.R

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

        val clubInfo = mapOf<String, String>(
            "name" to "Club Fluvial Lugo",
            "address" to "Rúa Fermín Rivera, s/n, 27004 Lugo",
            "phone" to "+34 673 45 34 54",
            "members" to "100",
            "webpage" to "https://www.clubfluviallugo.com"

        )

        val clubTrainer = "Jaime López Rego"

        val clubMembers = arrayOf<String>(
            "Si",
            "No",
            "Eso espero",
            "Non sei quen participa aquí"
        )

        val MemberList = RecyclerTournamentAdapter(clubMembers)

        val ClubMemberRecyclerView: RecyclerView =
            root.findViewById(R.id.club_member_list)

        val textView: TextView = binding.textClubInfo
        clubViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}