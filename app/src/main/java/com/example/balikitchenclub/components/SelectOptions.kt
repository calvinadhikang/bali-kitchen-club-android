package com.example.balikitchenclub.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SelectOptions(
    names: List<String>,
    values: List<Int>,
    defaultIndex: Int,
    onOptionSelected: (Int) -> Unit,
    labelText: String = "Select an option:",
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(defaultIndex) }
    var selectedOption by remember { mutableStateOf(names[defaultIndex]) }

    Column {
        Text(labelText)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
                .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ){
            Text(text = selectedOption, Modifier.weight(1F))
            Icon(imageVector = Icons.Filled.ArrowDropDownCircle, contentDescription = "")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            names.forEachIndexed { index, option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option
                        selectedIndex = index
                        expanded = false
                        onOptionSelected(values[selectedIndex])
                    },
                    modifier = if (index == selectedIndex) {
                        Modifier.background(Color.LightGray)
                    } else {
                        Modifier
                    },
                    text = { Text(text = option) }
                )
            }
        }
    }
}