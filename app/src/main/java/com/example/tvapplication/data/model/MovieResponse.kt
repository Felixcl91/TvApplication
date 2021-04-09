package com.example.tvapplication.data.model

data class MovieResponse(
	val metadata: Metadata? = null,
	val response: Response? = null
)

data class TvShowReference(
	val any: Any? = null
)

data class Response(
	val metadata: List<MetadataItem?>? = null,
	val prLevel: Int? = null,
	val keywords: String? = null,
	val year: Int? = null,
	val seriesNumberOfEpisodes: String? = null,
	val tvShowReference: TvShowReference? = null,
	val episodeId: String? = null,
	val type: String? = null,
	val assetExternalId: String? = null,
	val contentProvider: String? = null,
	val reviews: List<Any?>? = null,
	val id: Int? = null,
	val rentalPeriodUnit: String? = null,
	val isSecured: Boolean? = null,
	val windowStart: Long? = null,
	val adsInfo: String? = null,
	val seriesName: String? = null,
	val prName: String? = null,
	val rentalPeriod: String? = null,
	val name: String? = null,
	val broadcastTime: Int? = null,
	val shortName: String? = null,
	val discountId: String? = null,
	val status: Int? = null,
	val advisories: String? = null,
	val template: String? = null,
	val attachments: List<AttachmentsItem?>? = null,
	val chapters: List<Any?>? = null,
	val externalChannelId: String? = null,
	val reviewerRating: String? = null,
	val flags: Int? = null,
	val description: String? = null,
	val seriesSeason: String? = null,
	val allowedTerminalCategories: List<AllowedTerminalCategoriesItem?>? = null,
	val duration: Int? = null,
	val genreEntityList: List<GenreEntityListItem?>? = null,
	val responseElementType: String? = null,
	val plannedPublishDate: Long? = null,
	val simultaneousViewsLimit: String? = null,
	val assetId: Long? = null,
	val genres: List<Int?>? = null,
	val pricingMatrixId: Int? = null,
	val definition: String? = null,
	val windowEnd: Long? = null,
	val encodings: List<EncodingsItem?>? = null,
	val externalId: String? = null,
	val removalDate: Long? = null,
	val awards: List<Any?>? = null,
	val extrafields: List<ExtrafieldsItem?>? = null,
	val securityGroups: List<Any?>? = null,
	val contentProviderExternalId: String? = null,
	val categoryId: Int? = null
)

data class MetadataItem(
	val responseElementType: String? = null,
	val name: String? = null,
	val value: String? = null
)

data class Metadata(
	val request: String? = null,
	val timestamp: Long? = null
)

data class AllowedTerminalCategoriesItem(
	val responseElementType: String? = null,
	val maxTerminalsOfNonOperator: Int? = null,
	val maxTerminals: Int? = null,
	val name: String? = null,
	val externalId: String? = null
)

data class EncodingsItem(
	val responseElementType: String? = null,
	val name: String? = null
)

data class GenreEntityListItem(
	val responseElementType: String? = null,
	val parentName: String? = null,
	val name: String? = null,
	val externalId: String? = null,
	val id: Int? = null
)

data class ExtrafieldsItem(
	val responseElementType: String? = null,
	val name: String? = null,
	val value: String? = null
)

data class AttachmentsItem(
	val responseElementType: String? = null,
	val assetId: String? = null,
	val name: String? = null,
	val assetName: String? = null,
	val value: String? = null
)

