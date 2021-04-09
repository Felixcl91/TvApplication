package com.example.tvapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(

    @field:SerializedName("prLevel")
    val prLevel: Int?,
    @field:SerializedName("keywords")
    val keywords: String?,
    @field:SerializedName("year")
    val year: Int?,
    @field:SerializedName("assetExternalId")
    val assetExternalId: String?,
    @field:SerializedName("id")
    val id: Int?,
    @field:SerializedName("prName")
    val prName: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("shortName")
    val shortName: String?,
    @field:SerializedName("status")
    val status: Int?,
    @field:SerializedName("attachments")
    val attachments: ArrayList<Attachment?>?,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("duration")
    val duration: Int?,
    @field:SerializedName("genreEntityList")
    val genreEntityList: ArrayList<Genre?>?
): Parcelable
