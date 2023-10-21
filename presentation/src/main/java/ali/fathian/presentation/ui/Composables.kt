package ali.fathian.presentation.ui

import ali.fathian.presentation.R
import ali.fathian.presentation.model.Launches
import ali.fathian.presentation.model.UiModel
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun LaunchList(launches: Launches) {
    val tabItems = listOf("All", "Upcoming", "Past")
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, text ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                ) {
                    Text(text = text, modifier = Modifier.padding(8.dp))
                }
            }
        }
        when (selectedTabIndex) {
            0 -> LaunchesUi(launches = launches.allLaunches)
            1 -> LaunchesUi(launches = launches.upcomingLaunches)
            2 -> LaunchesUi(launches = launches.pastLaunches)
        }

    }
}

@Composable
fun LaunchItem(
    uiModel: UiModel,
    onItemClick: (UiModel) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(64.dp)
                .width(64.dp)
                .padding(start = 10.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiModel.image)
                    .fallback(R.drawable.spacex_logo)
                    .error(R.drawable.spacex_logo)
                    .placeholder(R.drawable.spacex_logo)
                    .build(),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = uiModel.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1
            )
            Text(
                text = uiModel.date,
                fontSize = 16.sp
            )
            Text(
                text = uiModel.time,
                fontSize = 16.sp
            )
            Text(
                text = uiModel.statusText,
                color = uiModel.statusColor,
                fontSize = 16.sp
            )
        }
        Box(
            modifier = Modifier
                .height(64.dp)
                .width(32.dp)
                .clickable {
                    onItemClick(uiModel)
                },
            contentAlignment = Alignment.CenterStart,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go to launch's details"
            )
        }
    }
}

@Composable
fun LaunchesUi(launches: List<UiModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(launches) { uiModel ->
            LaunchItem(uiModel = uiModel) {
                // todo navigate to launch detail screen
            }
        }
    }
}