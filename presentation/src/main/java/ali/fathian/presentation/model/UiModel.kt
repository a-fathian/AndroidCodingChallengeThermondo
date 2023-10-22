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
    var expanded: Boolean = false,
    val bookmarked: Boolean = false,
    val id: String = ""
)

class Launches(
    val allLaunches: List<UiModel> = emptyList(),
    val upcomingLaunches: List<UiModel> = emptyList(),
    val pastLaunches: List<UiModel> = emptyList(),
    val errorMessage: String = "",
    val loading: Boolean = false
) {
    fun copy(
        allLaunches: List<UiModel> = this.allLaunches,
        upcomingLaunches: List<UiModel> = this.upcomingLaunches,
        pastLaunches: List<UiModel> = this.pastLaunches,
        errorMessage: String = this.errorMessage,
        loading: Boolean = this.loading
    ): Launches {
        return Launches(
            allLaunches, upcomingLaunches, pastLaunches, errorMessage, loading
        )
    }
}

sealed class Origin {
    object AllLaunches: Origin()
    object UpcomingLaunches: Origin()
    object PastLaunches: Origin()
    object BookmarkLaunches: Origin()
}



