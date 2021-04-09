package com.example.tvapplication.data.model

import com.google.gson.annotations.SerializedName

data class Recommendations(
        @field:SerializedName("name")
        val name: String?,
        @field:SerializedName("externalContentId")
        val externalContentId: String?,
        @field:SerializedName("externalContentId")
        val images: ArrayList<Images>
)
