package ali.fathian.presentation.ui

sealed class Screens(val route: String) {
    object LaunchListScreen : Screens("launch_list_screen")
    object BookmarksScreen : Screens("bookmarks_screen")
}