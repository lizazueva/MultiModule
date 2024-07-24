package com.example.multimodule.db

import androidx.room.TypeConverter
import com.example.domain.models.Data
import com.example.domain.models.Pagination
import com.example.domain.models.Thumbnail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail?): String? {
        return gson.toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnail: String?): Thumbnail? {
        val type = object : TypeToken<Thumbnail>() {}.type
        return gson.fromJson(thumbnail, type)
    }

    @TypeConverter
    fun fromDataList(value: List<Data>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDataList(value: String?): List<Data>? {
        val listType = object : TypeToken<List<Data>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromPagination(value: Pagination?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPagination(value: String?): Pagination? {
        return gson.fromJson(value, Pagination::class.java)
    }

    @TypeConverter
    fun fromData(value: Data?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toData(value: String?): Data? {
        val type = object : TypeToken<Data>() {}.type
        return gson.fromJson(value, type)
    }


}