package com.example.tvapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attachment(
    @SerializedName("responseElementType")
    val responseElementType: String,
    @SerializedName("assetId")
    val assetId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("assetName")
    val assetName: String,
    @SerializedName("value")
    val value: String
): Parcelable
