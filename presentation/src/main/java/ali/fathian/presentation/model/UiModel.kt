package ali.fathian.presentation.model

import ali.fathian.domain.model.DomainLaunchModel

data class UiModel(
    val image: String,
    val name: String,
    val date: String,
    val time: String,
    val upcoming: Boolean,
    val success: Boolean
)

fun DomainLaunchModel.toUiModel(): UiModel {
    return UiModel(
        image = image ?: "",
        name = name ?: "",
        date = date ?: "",
        time = time ?: "",
        upcoming = upcoming,
        success = success
    )
}
