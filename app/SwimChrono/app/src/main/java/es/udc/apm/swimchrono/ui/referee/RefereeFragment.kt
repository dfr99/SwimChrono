package es.udc.apm.swimchrono.ui.referee

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.databinding.FragmentRefereeBinding
import es.udc.apm.swimchrono.model.Race
import java.util.Date

class RefereeFragment : Fragment() {

    private var _binding: FragmentRefereeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_referee, container, false)

        val beginChrono: Button = view.findViewById(R.id.beginButton)
        beginChrono.setOnClickListener {
            val intent = Intent(activity, TimerActivity::class.java)
            startActivity(intent)

            Log.d("BeginChronoClicked", "Go to chrono")
        }


        // Button Scan QR

        val btnScanQR: Button = view.findViewById(R.id.botonScanQR)
        btnScanQR.setOnClickListener {


            initQRScanner()


        }





        val races = arrayOf (
            Race(id = 1, swimmer = "David", club = "SAL", race = "100 medley", heat = 6, lane = 3, hour = Date()),
        )

        val racesList = RecyclerRacesListAdapter(races)
        val clubMemberRecyclerView: RecyclerView = view.findViewById(R.id.races)

        clubMemberRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        clubMemberRecyclerView.adapter = racesList

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    // Scan QR functions

    /**NO FUNCIONA**/
    private fun initQRScanner() {
        //IntentIntegrator(this).initiateScan() //DEPRECATED
        val funciona = false
        if (funciona) {
            val integrator = IntentIntegrator(requireActivity())
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Establecer el tipo de codigo de barras
            integrator.setPrompt("Acerque la cámara al código QR")
            integrator.setTorchEnabled(false) // Encender flash
            integrator.setBeepEnabled(true) // Sonido de confirmacion de codigo escaneado
            integrator.initiateScan()
        } else {
            Toast.makeText(requireContext(), "BOTÓN EN REPARACIÓN: Para escanear accede a START", Toast.LENGTH_LONG).show()
        }
    }

    //Este método se le llama cada vez que vuelve de un activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents == null){
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "El valor escaneado es: ${result.contents}", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "Exception?", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
        }

        Toast.makeText(requireContext(), "YA FUNCIONA", Toast.LENGTH_SHORT).show()

    }




}