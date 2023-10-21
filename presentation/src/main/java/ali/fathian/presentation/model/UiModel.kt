package ali.fathian.presentation.model

import ali.fathian.domain.DomainLaunchModel

data class UiModel(
    val name: String
)

fun DomainLaunchModel.toUiModel(): UiModel {
    return UiModel(
        name = name ?: ""
    )
}
