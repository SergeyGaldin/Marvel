package com.gateway.marvel.ui_kit.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AlertDialogExceptionInfo(
    dialogErrorText: String?,
    dialogExceptionText: String?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.error,
        textContentColor = MaterialTheme.colorScheme.onError,
        titleContentColor = MaterialTheme.colorScheme.onError,
        iconContentColor = MaterialTheme.colorScheme.onError,
        icon = {
            Icon(Icons.Filled.Error, modifier = Modifier.size(56.dp), contentDescription = null)
        },
        title = {
            Text(
                text = "Ошибка",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        text = {
            Text(
                text = "$dialogErrorText\n\n$dialogExceptionText",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {}
    )
}