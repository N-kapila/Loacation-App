package com.example.locationapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_data")
data class LocationData(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?
)
