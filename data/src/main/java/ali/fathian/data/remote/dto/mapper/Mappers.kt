package ali.fathian.data.remote.dto.mapper

import ali.fathian.data.local.LaunchEntity
import ali.fathian.data.remote.dto.Launch
import ali.fathian.domain.model.DomainLaunchModel

fun Launch.toDomainLaunchModel(): DomainLaunchModel {
    val (date, time) = (dateUtc ?: "T").split("T")
    return DomainLaunchModel(
        image = links?.patch?.small,
        name = name,
        date = date,
        time = time.substringBeforeLast("."),
        details = details,
        upcoming = upcoming ?: false,
        success = success ?: false,
        id = id
    )
}

fun LaunchEntity.toDomainModel(): DomainLaunchModel {
    return DomainLaunchModel(
        image = image,
        name = name,
        date = date,
        time = time,
        details = details,
        upcoming = upcoming,
        bookmarked = bookmarked,
        success = success,
        id = id
    )
}

fun DomainLaunchModel.toLaunchEntity(): LaunchEntity {
    return LaunchEntity(
        image = image,
        name = name,
        date = date,
        time = time,
        details = details,
        upcoming = upcoming,
        bookmarked = bookmarked,
        success = success,
        id = id
    )
}
