package ali.fathian.presentation.ui

import ali.fathian.presentation.model.UiModel
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LaunchList(launches: List<UiModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(launches) { uiModel ->
            LaunchItem(uiModel = uiModel)
        }
    }
}

@Composable
fun LaunchItem(uiModel: UiModel) {
    Text(
        text = uiModel.name,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    )
}