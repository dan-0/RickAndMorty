package com.example.rickmorty.ui.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickmorty.R

@Composable
fun CharacterImage(
  imageUrl: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center
  ) {
    var colorFilter by remember {
      mutableStateOf<ColorFilter?>(null)
    }
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .error(R.drawable.baseline_error_outline_24)
        .placeholder(R.drawable.baseline_refresh_24)
        .build(),
      contentDescription = "Character image",
      colorFilter = colorFilter,
      onLoading = { colorFilter = ColorFilter.tint(Color.DarkGray) },
      onError = { colorFilter = ColorFilter.tint(Color.Red) },
      onSuccess = { colorFilter = null },
      modifier = Modifier.height(150.dp)
    )
  }
}