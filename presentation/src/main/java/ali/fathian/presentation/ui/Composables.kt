package ali.fathian.presentation.ui

import ali.fathian.presentation.R
import ali.fathian.presentation.model.UiModel
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LaunchList(launches: List<UiModel>) {
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
            GlideImage(
                imageModel = uiModel.image,
                placeHolder = ImageBitmap.imageResource(id = R.drawable.spacex_logo),
                error = ImageBitmap.imageResource(id = R.drawable.spacex_logo)
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
