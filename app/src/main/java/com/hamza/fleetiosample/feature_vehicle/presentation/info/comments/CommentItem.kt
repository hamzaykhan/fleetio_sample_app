package com.hamza.fleetiosample.feature_vehicle.presentation.info.comments

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hamza.fleetiosample.R
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem

@Composable
fun CommentItem(
    item: CommentItem
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)) {
        Row (
            modifier = Modifier.padding(8.dp)
        ){
            CommentImage(item = item)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                CommentInfo(item = item)
            }
        }
    }
}

@Composable
fun CommentImage(item: CommentItem) {
    AsyncImage(
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        model = item.userImage,
        contentDescription = item.userImage + " user image",
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape),
        contentScale = ContentScale.FillBounds,
        error = painterResource(id = R.drawable.ic_launcher_foreground)
    )
}

@Composable
fun CommentInfo(item: CommentItem) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
            ) {
                append(item.userName)
            }
        },
        color = MaterialTheme.colorScheme.onBackground,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
    Spacer(modifier = Modifier.width(2.dp))
    Text(
        text = buildAnnotatedString {
            if (item.title.isNotBlank()) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.W900)
                ) {
                    append("${item.title} ")
                }
            }
            append(item.comment)
        },
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colorScheme.onBackground
    )
}