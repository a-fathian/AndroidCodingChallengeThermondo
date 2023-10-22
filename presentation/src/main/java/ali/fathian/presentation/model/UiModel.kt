package ali.fathian.presentation.model

import androidx.compose.ui.graphics.Color

data class UiModel(
    val image: String = "",
    val name: String = "",
    val date: String = "",
    val time: String = "",
    val details: String = "",
    val upcoming: Boolean = false,
    val success: Boolean = false,
    val statusText: String = "",
    val statusColor: Color = Color.Transparent,
    val expanded: Boolean = false,
    val bookmarked: Boolean = false,
    val id: String = ""
)

data class Launches(
    val allLaunches: List<UiModel> = emptyList(),
    val upcomingLaunches: List<UiModel> = emptyList(),
    val pastLaunches: List<UiModel> = emptyList(),
    val errorMessage: String = "",
    val loading: Boolean = false
)

sealed class Origin {
    object AllLaunches: Origin()
    object UpcomingLaunches: Origin()
    object PastLaunches: Origin()
    object BookmarkLaunches: Origin()
}



