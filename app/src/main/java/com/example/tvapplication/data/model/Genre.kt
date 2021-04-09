package com.example.tvapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val responseElementType: String,
    val parentName: String,
    val name: String,
    val externalId: String,
    //val id: Int
): Parcelable
