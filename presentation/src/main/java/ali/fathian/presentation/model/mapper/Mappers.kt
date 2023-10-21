package ali.fathian.presentation.model.mapper

import ali.fathian.domain.model.DomainLaunchModel
import ali.fathian.presentation.model.UiModel

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