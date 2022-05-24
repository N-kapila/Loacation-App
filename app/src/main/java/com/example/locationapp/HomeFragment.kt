package com.example.locationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imasha.locationserver.adapter.LocationListAdapter
import com.imasha.locationserver.databinding.FragmentHomeBinding
import com.imasha.locationserver.viewModels.LocationViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]

        binding.addNewFab.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_AddLocationFragment)
        }

        initData()
    }

    private fun initData() {
        val locationListView: RecyclerView = binding.locationListView
        val adapter = LocationListAdapter(locationViewModel)
        locationListView.adapter = adapter

        locationViewModel.allLocations.observe(viewLifecycleOwner) {
            it.let {
                adapter.setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}