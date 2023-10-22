package ali.fathian.presentation.model.mapper

import ali.fathian.domain.model.DomainLaunchModel
import ali.fathian.presentation.model.UiModel
import androidx.compose.ui.graphics.Color

fun DomainLaunchModel.toUiModel(): UiModel {
    return UiModel(
        image = image ?: "",
        name = name ?: "",
        date = date ?: "",
        time = time ?: "",
        details = details ?: "No Details",
        bookmarked = bookmarked,
        upcoming = upcoming,
        success = success,
        statusText = if (upcoming) "Upcoming" else if (success) "Success" else "Failure",
        statusColor = if (upcoming) Color.Blue else if (success) Color.Green else Color.Red,
        id = id
    )
}

fun UiModel.toDomainModel(): DomainLaunchModel {
    return DomainLaunchModel(
        image = image,
        name = name,
        date = date,
        time = time,
        details = details,
        id = id,
        upcoming = upcoming,
        success = success,
        bookmarked = bookmarked
    )
}