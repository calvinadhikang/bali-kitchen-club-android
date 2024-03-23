package com.example.balikitchenclub.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.balikitchenclub.utils.getContentPadding

@Composable
fun TransactionListCard(
    customer: String,
    totalPrice: String,
    onClick : () -> Unit
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Column(Modifier.padding(10.dp)){
            Text("$customer", fontWeight = FontWeight.Bold)
            Text("Rp $totalPrice")
        }
    }
}