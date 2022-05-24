package com.example.locationapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imasha.locationserver.R
import com.imasha.locationserver.models.LocationData
import com.imasha.locationserver.viewModels.LocationViewModel

class LocationListAdapter(private val locationViewModel: LocationViewModel) :
    RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {
    private var dataset: List<LocationData> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleText)
        val listTitle: LinearLayout = view.findViewById(R.id.listTitle)
        val leadingIcon: ImageView = view.findViewById(R.id.leadingIcon)
        val endingIcon: ImageView = view.findViewById(R.id.endingIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_title, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.title.text = data.name
        val bundle = Bundle()
        bundle.putLong("id", data.id!!)
        bundle.putString("locationName", data.name)
        bundle.putDouble("latitude", data.latitude!!)
        bundle.putDouble("longitude", data.longitude!!)

        holder.listTitle.setOnClickListener {
            it.findNavController().navigate(R.id.action_HomeFragment_to_mapViewFragment, bundle)
        }

        holder.leadingIcon.setOnClickListener {
            it.findNavController().navigate(R.id.action_HomeFragment_to_AddLocationFragment, bundle)
        }

        holder.endingIcon.setOnClickListener {
            setDelete(it.context, data)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun setData(data: List<LocationData>) {
        this.dataset = data
        notifyDataSetChanged()
    }

    private fun setDelete(context: Context, locationData: LocationData) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Are you sure to Delete")
        builder.setMessage("Do you want to delete location?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which
            ->
            locationViewModel.deleteLocation(locationData)
        })

        builder.setNeutralButton("No") { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }
}