package com.example.balikitchenclub.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColumnWraper(
    modifier: Modifier = Modifier,
    content: @Composable() (ColumnScope.() -> Unit)
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ){
        content()
    }
}