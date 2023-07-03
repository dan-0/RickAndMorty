package com.example.rickmorty.ui.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BasicCharacterLabel(label: String, value: String?) {
  if (value.isNullOrEmpty()) return
  Column {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium
    )
  }
}