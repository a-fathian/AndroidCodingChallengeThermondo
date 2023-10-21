package ali.fathian.data.remote.dto.mapper

import ali.fathian.data.remote.dto.Launch
import ali.fathian.domain.model.DomainLaunchModel

fun Launch.toDomainLaunchModel(): DomainLaunchModel {
    val (date, time) = (dateUtc ?: "T").split("T")
    return DomainLaunchModel(
        image = links?.patch?.small,
        name = name,
        date = date,
        time = time,
        upcoming = upcoming ?: false,
        success = success ?: false,
        id = id
    )
}
