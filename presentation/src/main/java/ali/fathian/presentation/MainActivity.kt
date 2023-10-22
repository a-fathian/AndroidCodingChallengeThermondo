package ali.fathian.presentation

import ali.fathian.presentation.launch.LaunchesViewModel
import ali.fathian.presentation.ui.LaunchList
import ali.fathian.presentation.ui.theme.AndroidCodingChallengeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LaunchesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCodingChallengeTheme {
                val bookmarks = viewModel.bookmarks.collectAsState().value
                LaunchList(
                    viewModel.uiState.collectAsState().value,
                    bookmarks,
                    onRetryClick = viewModel::fetchLaunches,
                    onItemClick = viewModel::onItemClick,
                    onBookmarkClicked = viewModel::onBookmarkClicked
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchLaunches()
    }
}
