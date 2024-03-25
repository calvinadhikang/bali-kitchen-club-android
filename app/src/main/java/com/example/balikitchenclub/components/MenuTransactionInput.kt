package com.example.balikitchenclub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.balikitchenclub.ui.theme.LightRed
import com.example.balikitchenclub.ui.theme.TonalBrown
import com.example.balikitchenclub.utils.getContentPadding

@Composable
fun MenuTransactionInput(
    name: String,
    stock: String,
    price: String,
    qty: String,
    addQty: () -> Unit,
    subsQty: () -> Unit
) {
    Column(
        modifier = Modifier.background(if (stock.toInt() <= 0) LightRed else TonalBrown, RoundedCornerShape(8.dp)).padding(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp,vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1F)
            ) {
                Text("$name", fontWeight = FontWeight.Bold)
                Text("Rp $price")
                if (stock.toInt() > 0){
                    Text("Tersedia : $stock Pcs")
                }else{
                    Text(text = "Tidak Tersedia / Stock Habis", color = Color.Red)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { subsQty() }, enabled = qty.toInt() > 0) {
                    Icon(imageVector = Icons.Filled.RemoveCircleOutline, contentDescription = "")
                }
                Text(qty)
                IconButton(onClick = { addQty() }, enabled = stock.toInt() != 0 && (qty.toInt() <= 0 || qty.toInt() != stock.toInt())) {
                    Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = "")
                }
            }
        }
    }
}