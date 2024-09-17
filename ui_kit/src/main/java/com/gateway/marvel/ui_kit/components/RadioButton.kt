package com.gateway.marvel.ui_kit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gateway.marvel.ui_kit.theme.MarvelTheme

@Composable
fun <T> RadioButtonGroup(
    list: List<T>,
    textItem: (T) -> String,
    selectedItem: T,
    onSelect: (T) -> Unit
) {
    val selectedOption = remember { mutableStateOf(selectedItem) }

    list.forEach { item ->
        RadioButtonOption(
            item = item,
            textItem = textItem(item),
            selectedOption = selectedOption.value,
            onSelect = { selectItem ->
                selectedOption.value = selectItem
                onSelect(selectItem)
            }
        )
    }
}

@Composable
fun <T> RadioButtonOption(
    item: T,
    textItem: String,
    selectedOption: T,
    onSelect: (T) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(item) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedOption == item,
            onClick = { onSelect(item) }
        )

        Text(
            modifier = Modifier.weight(1f),
            text = textItem
        )
    }
}

@Preview
@Composable
private fun RadioButtonOptionPreview() {
    MarvelTheme {
        RadioButtonOption(
            item = "Персонажи",
            textItem = "Персонажи",
            selectedOption = "Персонажи",
            onSelect = {}
        )
    }
}