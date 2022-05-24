package com.example.locationapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.imasha.locationserver.database.AppDatabase
import com.imasha.locationserver.models.LocationData
import com.imasha.locationserver.repository.LocationDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val database by lazy { AppDatabase.getDatabase(application.applicationContext) }
    private val repository by lazy { LocationDataRepository(database.locationDao()) }

    val allLocations: LiveData<List<LocationData>> = repository.allLocations.asLiveData()

    fun addNewLocation(location: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inset(location)
        }
    }

    fun deleteLocation(location: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(location)
        }
    }

    fun updateLocation(location: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(location)
        }
    }

}