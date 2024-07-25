package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "artworks")
data class ArtworksEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val data: List<Data>,
    val pagination: Pagination
)
@Entity(tableName = "data")
data class Data(
    val artist_id: Int?,
    val artist_title: String?,
    val description: String?,
    @PrimaryKey val id: Int,
    val thumbnail: Thumbnail?
)

@Entity(tableName = "pagination")
data class Pagination(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val total_pages: Int
)


@Entity(tableName = "thumbnail")
data class Thumbnail(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lqip: String
)