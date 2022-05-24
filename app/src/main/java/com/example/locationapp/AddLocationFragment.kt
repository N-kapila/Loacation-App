package com.example.locationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.locationapp.viewModels.LocationViewModel
import com.imasha.locationserver.databinding.FragmentAddLocationBinding
import com.imasha.locationserver.models.LocationData
import com.imasha.locationserver.viewModels.LocationViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

const val TAG = "AddLocationFragment"

class AddLocationFragment : Fragment() {

    private var _binding: FragmentAddLocationBinding? = null


    private val binding get() = _binding!!

    private lateinit var locationViewModel: LocationViewModel

    private var id: Long? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddLocationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        val args = arguments
        if (args != null) {
            id = args.getLong("id")
            binding.editTextLocationName.setText(args.getString("locationName"))
            binding.editTextLat.setText(args.getDouble("latitude").toString())
            binding.editTextLog.setText(args.getDouble("longitude").toString())
        }
        binding.buttonSave.setOnClickListener { saveLocation() }

    }

    private fun saveLocation() {
        val name = binding.editTextLocationName.text.toString()
        val lat = binding.editTextLat.text.toString()
        val long = binding.editTextLog.text.toString()

        if (name.isNullOrEmpty() || lat.isNullOrEmpty() || long.isNullOrEmpty()) {
            Toast.makeText(context, "Some details are missing!", Toast.LENGTH_SHORT).show()
            return
        }
        val location = LocationData(id, name, lat.toDouble(), long.toDouble())

        if (id == null) {
            locationViewModel.addNewLocation(location)
            Toast.makeText(context, "New location successfully added!", Toast.LENGTH_SHORT).show()
        } else {
            locationViewModel.updateLocation(location)
            Toast.makeText(context, "Your Location successfully updated!", Toast.LENGTH_SHORT)
                .show()
        }

        findNavController().navigate(R.id.action_AddLocationFragment_to_HomeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}