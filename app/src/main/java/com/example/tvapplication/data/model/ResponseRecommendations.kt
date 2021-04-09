package com.example.tvapplication.data.model

data class ResponseRecommendations(
	val response: ArrayList<ResponseItem?>? = null
)

data class CategoriesItem(
	val categoryName: String? = null,
	val categoryId: String? = null
)

data class ImagesItem(
	val name: String? = null,
	val value: String? = null
)

data class ResponseItem(
	val ratersCount: Int? = null,
	val images: List<ImagesItem?>? = null,
	val prLevel: Int? = null,
	val availabilities: List<AvailabilitiesItem?>? = null,
	val prName: String? = null,
	val rating: Double? = null,
	val type: String? = null,
	val contentProperties: List<Any?>? = null,
	val recommendationReasons: List<Any?>? = null,
	val genres: List<GenresItem?>? = null,
	val name: String? = null,
	val externalContentId: String? = null,
	val id: Int? = null,
	val contentType: String? = null,
	val sourceChannelId: String? = null
)

data class AvailabilitiesItem(
	val images: List<ImagesItem?>? = null,
	val availabilityProperties: List<Any?>? = null,
	val videoId: String? = null,
	val startTime: Long? = null,
	val endTime: Long? = null,
	val categories: List<CategoriesItem?>? = null,
	val serviceId: String? = null
)

data class GenresItem(
	val name: String? = null,
	val externalId: String? = null,
	val id: String? = null
)


