package com.gateway.marvel.feature_settings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.gateway.marvel.ui_kit.theme.MarvelTheme

@Composable
fun ChangeValueTextField(
    title: String,
    supportingText: String,
    defaultValue: Int,
    onSaveValue: (Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var valueState by remember { mutableStateOf(defaultValue.toString()) }
    var errorState by remember { mutableStateOf(false) }

    val onSave = {
        keyboardController?.hide()
        valueState.ifEmpty { valueState = "1" }
        onSaveValue(valueState.toInt())
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueState,
        onValueChange = {
            if (it.isEmpty()) valueState = it
            else if (it.toInt() < 100) {
                valueState = it
                if (errorState) errorState = false
            } else {
                errorState = true
            }
        },
        label = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth()
            )
        },
        supportingText = {
            if (errorState) Text(
                text = supportingText,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.error
            ) else Text(
                text = supportingText,
                modifier = Modifier.fillMaxWidth()
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onSave() },
                enabled = valueState != defaultValue.toString()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.NumberPassword
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSave()
            }
        )
    )
}

@Composable
fun ChangeValueTextField(
    title: String,
    defaultValue: Int,
    onSaveValue: (Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var valueState by remember { mutableStateOf(defaultValue.toString()) }

    val onSave = {
        keyboardController?.hide()
        valueState.ifEmpty { valueState = "0" }
        onSaveValue(valueState.toInt())
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueState,
        onValueChange = {
            if (it.isEmpty()) valueState = it
            else if (it.length < 5) {
                valueState = it
            }
        },
        label = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth()
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onSave() },
                enabled = valueState != defaultValue.toString()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.NumberPassword
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSave()
            }
        )
    )
}

@Preview
@Composable
private fun ChangeValueLayoutPreview() {
    MarvelTheme {
        ChangeValueTextField(
            title = "Кол-во загружаемых персонажей",
            supportingText = "Не более 99 персонажей",
            defaultValue = 20,
            onSaveValue = {}
        )
    }
}