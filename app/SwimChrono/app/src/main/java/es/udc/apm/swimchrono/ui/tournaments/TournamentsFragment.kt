package es.udc.apm.swimchrono.ui.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.udc.apm.swimchrono.databinding.FragmentTournamentsBinding

class TournamentsFragment : Fragment() {

    private var _binding: FragmentTournamentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tournamentsViewModel =
            ViewModelProvider(this).get(TournamentsViewModel::class.java)

        _binding = FragmentTournamentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTournaments
        tournamentsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}