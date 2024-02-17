package com.example.balikitchenclub.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MenuTransaction(
    name: String,
    stock: String,
    price: String,
    qty: String,
    addQty: () -> Unit,
    subsQty: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(
        ) {
            Column(
                Modifier.weight(1F)
            ) {
                Text("$name")
                Text("Rp $price")
                Text("Tersisa : $stock Pcs")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { addQty() }) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
                }
                Text(qty)
                IconButton(onClick = { subsQty() }) {
                    Icon(imageVector = Icons.Filled.RemoveCircle, contentDescription = "")
                }
            }
        }
        HorizontalDivider()
    }
}