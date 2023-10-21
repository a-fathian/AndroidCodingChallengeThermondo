package ali.fathian.presentation.model

import androidx.compose.ui.graphics.Color

data class UiModel(
    val image: String,
    val name: String,
    val date: String,
    val time: String,
    val statusText: String,
    val statusColor: Color
)


