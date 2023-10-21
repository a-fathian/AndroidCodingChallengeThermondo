package ali.fathian.domain.model

data class DomainLaunchModel(
    val image: String? = null,
    val name: String? = null,
    val date: String? = null,
    val time: String? = null,
    val upcoming: Boolean,
    val success: Boolean,
    val id: String? = null
)
