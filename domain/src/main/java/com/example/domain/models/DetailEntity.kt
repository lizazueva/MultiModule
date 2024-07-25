package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail")
data class DetailEntity(
    @PrimaryKey val id: Int,
    val `data`: Data,
)