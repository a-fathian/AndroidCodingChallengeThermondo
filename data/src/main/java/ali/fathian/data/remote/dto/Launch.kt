package ali.fathian.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Launch(
    @SerializedName("fairings") val fairings: Fairings?,
    @SerializedName("links") val links: Links?,
    @SerializedName("static_fire_date_utc") val staticFireDateUtc: String?,
    @SerializedName("static_fire_date_unix") val staticFireDateUnix: Long?,
    @SerializedName("tdb") val tdb: Boolean?,
    @SerializedName("net") val net: Boolean?,
    @SerializedName("window") val window: Int?,
    @SerializedName("rocket") val rocket: String?,
    @SerializedName("success") val success: Boolean?,
    @SerializedName("details") val details: String?,
    @SerializedName("crew") val crew: List<Any>?,
    @SerializedName("ships") val ships: List<Any>?,
    @SerializedName("capsules") val capsules: List<String>?,
    @SerializedName("payloads") val payloads: List<String>?,
    @SerializedName("launchpad") val launchpad: String?,
    @SerializedName("auto_update") val autoUpdate: Boolean?,
    @SerializedName("flight_number") val flightNumber: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("date_utc") val dateUtc: String?,
    @SerializedName("date_unix") val dateUnix: Long?,
    @SerializedName("date_local") val dateLocal: String?,
    @SerializedName("date_precision") val datePrecision: String?,
    @SerializedName("upcoming") val upcoming: Boolean?,
    @SerializedName("cores") val cores: List<Core>?,
    @SerializedName("id") val id: String
)

data class Core(
    @SerializedName("core") val core: String,
    @SerializedName("flight") val flight: Int,
    @SerializedName("gridfins") val gridfins: Boolean,
    @SerializedName("legs") val legs: Boolean,
    @SerializedName("reused") val reused: Boolean,
    @SerializedName("landing_attempt") val landingAttempt: Boolean,
    @SerializedName("landing_success") val landingSuccess: Boolean,
    @SerializedName("landing_type") val landingType: String,
    @SerializedName("landpad") val landpad: String
)

data class Fairings(
    @SerializedName("recovered") val recovered: Boolean,
    @SerializedName("recovery_attempt") val recoveryAttempt: Boolean,
    @SerializedName("reused") val reused: Boolean,
    @SerializedName("ships") val ships: List<String>
)

data class Flickr(
    @SerializedName("small") val small: List<String>,
    @SerializedName("original") val original: List<String>
)

data class Links(
    @SerializedName("patch") val patch: Patch,
    @SerializedName("reddit") val reddit: Reddit,
    @SerializedName("flickr") val flickr: Flickr,
    @SerializedName("presskit") val presskit: String,
    @SerializedName("webcast") val webcast: String,
    @SerializedName("youtube_id") val youtubeId: String,
    @SerializedName("article") val article: String,
    @SerializedName("wikipedia") val wikipedia: String
)

data class Patch(
    @SerializedName("small") val small: String,
    @SerializedName("large") val large: String
)

data class Reddit(
    @SerializedName("campaign") val campaign: String,
    @SerializedName("launch") val launch: String,
    @SerializedName("media") val media: String,
    @SerializedName("recovery") val recovery: Any
)